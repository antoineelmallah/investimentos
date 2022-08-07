package br.com.mallah.investimentos.sheet.converter;

import org.apache.poi.ss.usermodel.Cell;

public class StringConverter implements Convertable<String> {

	@Override
	public String convert(Cell cell) {
		return cell.getStringCellValue();
	}

}
