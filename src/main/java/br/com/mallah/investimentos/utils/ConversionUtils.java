package br.com.mallah.investimentos.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConversionUtils {

	private static final DateTimeFormatter LOCALDATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	public static LocalDate stringToLocalDate(String date) {
		return LocalDate.parse(date, LOCALDATE_FORMATTER);
	}
	
}
