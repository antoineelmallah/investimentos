package br.com.mallah.investimentos.persistence.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mallah.investimentos.persistence.entity.TipoMovimentacaoEntity;

public interface TipoMovimentacaoRepository extends JpaRepository<TipoMovimentacaoEntity, Long> {

	@Query("select c from TipoMovimentacaoEntity c where c.descricao in :descricoes")
	List<TipoMovimentacaoEntity> findAllByDescricao(@Param("descricoes") Set<String> descTipo);

}
