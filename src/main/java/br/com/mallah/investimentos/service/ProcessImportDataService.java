package br.com.mallah.investimentos.service;

import java.io.InputStream;

public interface ProcessImportDataService <T> {

	Class<T> sheetAnnotatedClass();
	
	void process(InputStream inputStream);
	
}
