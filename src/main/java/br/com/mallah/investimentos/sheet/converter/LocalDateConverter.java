package br.com.mallah.investimentos.sheet.converter;

import java.time.LocalDate;

import org.apache.poi.ss.usermodel.Cell;

import br.com.mallah.investimentos.utils.ConversionUtils;

public class LocalDateConverter implements Convertable<LocalDate> {
	
	@Override
	public LocalDate convert(Cell cell) {
		String stringDate = cell.getStringCellValue();
		return ConversionUtils.stringToLocalDate(stringDate);
	}

}
