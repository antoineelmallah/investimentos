package br.com.mallah.investimentos.persistence.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mallah.investimentos.persistence.entity.ProdutoEntity;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity, Long> {

	@Query("select c from ProdutoEntity c where c.descricao in :descricoes")
	List<ProdutoEntity> findAllByDescricao(@Param("descricoes") Set<String> descTipo);

}
