package br.com.mallah.investimentos.persistence.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.mallah.investimentos.persistence.entity.MovimentacaoEntity;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {

	@Modifying
	@Query("delete from MovimentacaoEntity c where c.data between :dataInicio and :dataFim")
	void deleteByDateRange(LocalDate dataInicio, LocalDate dataFim);
	
}
