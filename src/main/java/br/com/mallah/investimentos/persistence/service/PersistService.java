package br.com.mallah.investimentos.persistence.service;

import java.util.List;

import br.com.mallah.investimentos.persistence.entity.BaseEntity;

public interface PersistService <T extends BaseEntity<?>> {

	List<T> process(List<T> entities);
	
	Class<T> getEntityClass();
	
}
