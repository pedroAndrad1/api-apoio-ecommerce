package br.com.uniriotec.sagui.services;

import java.util.Locale;
import java.util.Optional;

import br.com.uniriotec.sagui.model.dto.ProdutoData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import br.com.uniriotec.sagui.model.Produto;
import br.com.uniriotec.sagui.model.dto.ProdutoForm;
import br.com.uniriotec.sagui.model.dto.ProdutoRepresentationAssembler;
import br.com.uniriotec.sagui.repository.ProdutoRepositorio;

@Service
public class SpringDataMongoProdutoServico implements ProdutoServico {

	private ProdutoRepositorio produtoRepositorio;
	private ProdutoRepresentationAssembler produtoAssembler;
	private MessageSource messageSource;
	@Autowired
	public SpringDataMongoProdutoServico(ProdutoRepositorio produtoRepositorio, ProdutoRepresentationAssembler produtoAssembler, MessageSource messageSource) {
		this.produtoRepositorio = produtoRepositorio;
		this.produtoAssembler = produtoAssembler;
		this.messageSource = messageSource;
	}

	@Override
	public ProdutoData buscarPorId(String id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		if( produto.isPresent() ) {
			return produtoAssembler.toModel( produto.get() );
		}else {
			throw new ProdutoNaoEncontradoException( messageSource.getMessage("api.erro.produto.nao.encontrado", null, Locale.getDefault())  );//
		}	
	}
	@PreAuthorize("hasRole('Produto_Admin_Read')")
	@Override
	public CollectionModel<ProdutoData> buscarTodos() {
		return produtoAssembler.toCollectionModel(produtoRepositorio.findAll());
	}
	
	@Override
	public ProdutoRetornoPaginacao buscarTodosAtivosPaginavel(int page, int size){
		Pageable paging = PageRequest.of(page, size);
		
		Page<Produto> pageProduto;
		
		pageProduto = produtoRepositorio.findByAtivo(true, paging);
		
		return ProdutoRetornoPaginacao.builder()
				.produtos( produtoAssembler.toCollectionModel( pageProduto.getContent() ) )	
				.paginaAtual( pageProduto.getNumber() )
				.totalItems( pageProduto.getNumberOfElements() )
				.totalPaginas( pageProduto.getTotalPages() )
				.build();
	}

	@PreAuthorize("hasRole('Produto_Admin_Write')")
	@Override
	public ProdutoData salvar(ProdutoForm produto) {
		Produto persistir = Produto.builder()
				.nome( produto.getNome() )
				.descricao( produto.getDescricao() )
				.preco( produto.getPreco() )
				.ativo( produto.getAtivo() )
				.build();
			try {
				persistir.setId( produtoRepositorio.save(persistir).getId() );
				return produtoAssembler.toModel(persistir);
			}catch(DuplicateKeyException dke) {
				throw new BancoNaoModificadoException( messageSource.getMessage("api.erro.mongodb.chave.duplicada", null, Locale.getDefault()) );
			}		
	}
	@PreAuthorize("hasRole('Produto_Admin_Write')")
	@Override
	public ProdutoData inativar(String id) {
		Optional<Produto> produto = produtoRepositorio.findById(id);
		if( produto.isPresent() ) {
			produto.get().setAtivo(false); 
			return produtoAssembler.toModel( produtoRepositorio.save( produto.get() ) );
		}else {
			throw new ProdutoNaoEncontradoException( messageSource.getMessage("api.erro.produto.nao.encontrado", null, Locale.getDefault()) );//
		}
	}
	@PreAuthorize("hasRole('Produto_Admin_Write')")
	@Override
	public ProdutoData alterar(ProdutoForm produto) {
		if(produtoRepositorio.existsById(produto.getId())) {
			Produto persistido = produtoRepositorio.findById( produto.getId() ).get();
			persistido.setDescricao( produto.getDescricao() );
			persistido.setPreco(produto.getPreco());
			return produtoAssembler.toModel( produtoRepositorio.save(persistido) );
		}else {
			throw new ProdutoNaoEncontradoException( messageSource.getMessage("api.erro.produto.nao.encontrado", null, Locale.getDefault()) );
		}
	}
}
