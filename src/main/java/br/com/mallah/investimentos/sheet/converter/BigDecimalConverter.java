package br.com.mallah.investimentos.sheet.converter;

import java.math.BigDecimal;

import org.apache.poi.ss.usermodel.Cell;

public class BigDecimalConverter implements Convertable<BigDecimal> {

	@Override
	public BigDecimal convert(Cell cell) {
		try {
			return BigDecimal.valueOf(cell.getNumericCellValue());
		} catch (Exception e) {
			String cellValue = cell.getStringCellValue();
			if (cellValue.matches("^\\d+(\\.\\d+)?$")) {
				return new BigDecimal(cellValue);
			}
		}
		return null;
	}

}
