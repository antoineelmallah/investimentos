package br.com.mallah.investimentos.sheet.converter;

import org.apache.poi.ss.usermodel.Cell;

public class DoubleConverter implements Convertable<Double> {

	@Override
	public Double convert(Cell cell) {
		try {
			return cell.getNumericCellValue();
		} catch (Exception e) {
			String cellValue = cell.getStringCellValue();
			if (cellValue.matches("^\\d+(\\.\\d+)?$")) {
				return Double.parseDouble(cellValue);
			}
		}
		return null;
	}

}
