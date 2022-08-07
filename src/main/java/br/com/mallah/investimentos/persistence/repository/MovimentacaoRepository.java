package br.com.mallah.investimentos.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.mallah.investimentos.persistence.entity.MovimentacaoEntity;

public interface MovimentacaoRepository extends JpaRepository<MovimentacaoEntity, Long> {

}
