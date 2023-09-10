package br.com.uniriotec.sagui.utils;

import br.com.uniriotec.sagui.model.Produto;
import br.com.uniriotec.sagui.model.dto.ProdutoForm;
import br.com.uniriotec.sagui.model.dto.ProdutoRepresentationAssembler;
import br.com.uniriotec.sagui.repository.ProdutoRepositorio;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@TestComponent
public class ProdutoFixture {
    @Autowired
    private ProdutoRepositorio produtoRepositorio;
    @Autowired
    private ProdutoRepresentationAssembler produtoRepresentationAssembler;

    private final Produto produtoCN = Produto.builder()
            .nome("As crónicas de nárnia")
            .descricao("C.s.Lewis")
            .preco( BigDecimal.valueOf(45.99) )
            .ativo(true)
            .build();
    private final Produto produtoRDJF = Produto.builder()
            .nome("Relógio do juízo final")
            .descricao("C.s.Lewis")
            .preco( BigDecimal.valueOf(139.99) )
            .ativo(true)
            .build();
    private final Produto produtoDC = Produto.builder()
            .nome("A divina comédia")
            .descricao("Dante Alighieri")
            .preco( BigDecimal.valueOf(799.99) )
            .ativo(true)
            .build();
    List<Produto> produtos = new ArrayList<>();
    public ProdutoFixture(){
        produtos.add(this.produtoCN);
        produtos.add(this.produtoRDJF);
        produtos.add(this.produtoDC);
    }
    public void saveProdutoFormInProdutos(ProdutoForm produtoForm){
         produtos.add( produtoRepresentationAssembler.fromFormToModel(produtoForm) );
    }
    public void saveFixture(){
        this.produtoRepositorio.saveAll( produtos );
    }
}
