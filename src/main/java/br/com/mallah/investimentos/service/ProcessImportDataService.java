package br.com.mallah.investimentos.service;

import java.io.InputStream;
import java.util.List;

import br.com.mallah.investimentos.controller.enums.SheetType;
import br.com.mallah.investimentos.persistence.entity.BaseEntity;

public interface ProcessImportDataService <T, E extends BaseEntity<?>> {

	Class<T> sheetAnnotatedClass();
	
	List<E> process(InputStream inputStream, SheetType sheetType);
	
}
