package br.com.mallah.investimentos.sheet.service;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import br.com.mallah.investimentos.sheet.converter.Convertable;
import br.com.mallah.investimentos.utils.CollectionUtils;
import br.com.mallah.investimentos.utils.ReflectionUtils;

@Service
public class SheetReaderService {
	
	private static final Logger logger = LoggerFactory.getLogger(SheetReaderService.class);

	public <T> List<T> processSheet(InputStream arquivo, Class<T> sheetAnnotatedClass) {
		
		Sheet sheet = getSheets(arquivo)
				.filter(s -> s.getSheetName().equals(getSheetAnnotationNameAttributeValue(sheetAnnotatedClass)))
				.findFirst()
				.orElseThrow(() -> new IllegalArgumentException("Sheet not found or not mapped."));

		logger.debug("Processing sheet named '"+sheet.getSheetName()+"'");

		List<T> result = new ArrayList<>();
		
		Row header = sheet.getRow(sheet.getFirstRowNum());
		
		List<Field> mappedFields = extractFieldsFromHeader(sheetAnnotatedClass, header);
				
		for (Row row: sheet) {

			if (row.getRowNum() == sheet.getTopRow()) continue;

			T entitySheet = ReflectionUtils.getNewInstance(sheetAnnotatedClass)
					.orElseThrow(() -> new IllegalStateException("Error trying to instantiate class '"+sheetAnnotatedClass.getName()+"'"));
			
			Iterator<Field> fieldIterator = mappedFields.iterator();

			for (Cell cell: row) {
				
				if (!fieldIterator.hasNext()) {
					throw new IllegalStateException("Number of values bigger than number of sheet columns mapped on class '"+sheetAnnotatedClass.getName()+"'");
				}
				Field field = fieldIterator.next();
				Convertable converter = Convertable.getConverter(field.getType())
						.orElseThrow(() -> new IllegalStateException("Converter not found for type '"+field.getType()+"'."));
				
				ReflectionUtils.setValue(field, entitySheet, converter.convert(cell));
			}

			result.add(entitySheet);
			
		}
		
		logger.debug("Sheet '"+sheet.getSheetName()+"' processed!");
		
		return result;
	}

	private <T> String getSheetAnnotationNameAttributeValue(Class<T> sheetClass) {
		br.com.mallah.investimentos.sheet.annotation.Sheet annotation = sheetClass.getAnnotation(br.com.mallah.investimentos.sheet.annotation.Sheet.class);
		return annotation.name();
	}
	
	private Stream<Sheet> getSheets(InputStream is) {
		try {
			Workbook workbook = WorkbookFactory.create(is);
			return CollectionUtils.streamFromIterator(workbook.sheetIterator());
		} catch (EncryptedDocumentException | IOException e) {
			return Stream.empty();
		}
	}

	private List<Field> extractFieldsFromHeader(Class<?> sheetClass, Row header) {
		List<Field> mappedFields = new ArrayList<>();
		for (Cell cell: header) {
			System.out.println(cell.getStringCellValue());
			Field annotatedField = ReflectionUtils.getColumnAnnotatedField(sheetClass, cell.getStringCellValue())
					.orElseThrow(() -> new IllegalStateException("Campo '"+cell.getStringCellValue()+"' não mapeado na classe '"+sheetClass.getName()+"'"));
			mappedFields.add(annotatedField);
		}
		return mappedFields;
	}

}
