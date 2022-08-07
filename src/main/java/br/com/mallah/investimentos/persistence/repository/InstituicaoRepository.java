package br.com.mallah.investimentos.persistence.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.mallah.investimentos.persistence.entity.InstituicaoEntity;

public interface InstituicaoRepository extends JpaRepository<InstituicaoEntity, Long> {

	@Query("select c from InstituicaoEntity c where c.descricao in :descricoes")
	List<InstituicaoEntity> findAllByDescricao(@Param("descricoes") Set<String> descTipo);

}
