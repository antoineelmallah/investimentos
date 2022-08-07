package br.com.mallah.investimentos.persistence.entity;

import java.util.Optional;
import java.util.stream.Stream;

public enum FluxoMovimento {

	CREDITO("Credito"), 
	DEBITO("Debito");
	
	private String sheetValue;
	
	private FluxoMovimento(String sheetValue) {
		this.sheetValue = sheetValue;
	}
	
	public static Optional<FluxoMovimento> getBySheetValue(String sheetValue) {
		return Stream.of(values())
			.filter(f -> f.sheetValue.equals(sheetValue))
			.findFirst();
	}
	
}
