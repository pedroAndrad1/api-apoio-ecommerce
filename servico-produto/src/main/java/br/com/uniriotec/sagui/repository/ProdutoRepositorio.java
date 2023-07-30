package br.com.uniriotec.sagui.repository;

import br.com.uniriotec.sagui.model.Produto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Repositorio de produto, classe responsavel por expor a interacao com o banco
 * 
 * @author C3PO
 */
public interface ProdutoRepositorio extends MongoRepository<Produto, String>{
	Page<Produto> findByAtivo(boolean ativo, Pageable pageable);
}
