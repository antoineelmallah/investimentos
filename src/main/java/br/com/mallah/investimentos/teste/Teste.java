package br.com.mallah.investimentos.teste;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import br.com.mallah.investimentos.sheet.converter.Convertable;
import br.com.mallah.investimentos.utils.ReflectionUtils;

public class Teste {

	public static void main(String[] args) {

		Teste teste = new Teste();
		
		teste.readSheet();
		
	}
	
	private void readSheet() {
		Path path = Paths.get("/", "home", "mallah", "Documents", "investimentos", "movimentacao-2019.xlsx");
		
		try (InputStream arquivo = Files.newInputStream(path, StandardOpenOption.READ)) {
			convertSheetIntoEntities(arquivo);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void convertSheetIntoEntities(InputStream arquivo) throws IOException {
		Workbook workbook = WorkbookFactory.create(arquivo);
		
		for (int sheetNumb = 0; sheetNumb < workbook.getNumberOfSheets(); sheetNumb++) {
			
			Sheet sheet = workbook.getSheetAt(sheetNumb);
			System.out.println("NAME: "+sheet.getSheetName());
			
			Optional<Class<?>> annotatedClassOptional = ReflectionUtils.getSheetAnnotatedClass(sheet.getSheetName());
			
			if (annotatedClassOptional.isEmpty()) {
				System.out.println("Mapeamento para '"+sheet.getSheetName()+"' não encontrado.");
				continue;
			}
			
			Class<?> sheetClass = annotatedClassOptional.get();
			Optional<?> entitySheetOptional = ReflectionUtils.getNewInstance(sheetClass);
			
			if (entitySheetOptional.isEmpty()) {
				System.out.println("Não foi possível instanciar a classe '"+sheetClass.getName()+"'");
				continue;
			}
			
			System.out.println("TOP ROW: "+sheet.getTopRow());
			Row header = sheet.getRow(sheet.getFirstRowNum());
			System.out.println("HEADER:");
			
			List<Field> mappedFields = extractFieldsFromHeader(sheetClass, header);
			
			for (Row row: sheet) {

				if (row.getRowNum() == sheet.getTopRow()) continue;
				
				//System.out.println("ROW:"+row.getRowNum());
				Object entitySheet = entitySheetOptional.get();
				Iterator<Field> fieldIterator = mappedFields.iterator();
				for (Cell cell: row) {
					//System.out.println(cell.getStringCellValue());
					if (!fieldIterator.hasNext()) {
						throw new IllegalStateException("Número de valores maior que colunas mapeadas na classe '"+sheetClass.getName()+"'");
					}
					Field field = fieldIterator.next();
					Optional<Convertable> converterOptional = Convertable.getConverter(field.getType());
					if (converterOptional.isEmpty()) 
						throw new IllegalStateException("Converter não encontrado.");
					ReflectionUtils.setValue(field, entitySheet, converterOptional.get().convert(cell));
				}
				System.out.println(entitySheet);
			}
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
	
	/*
	private Optional<Convertable> getConverter(Field field, Cell cell) {
		Optional<Class<? extends Convertable>> convertableClassOptional = ReflectionUtils.getSubclasses(Convertable.class).stream()
			.filter(c -> {
				try {
					return c.getMethod("convert", Cell.class).getReturnType().equals(field.getType());
				} catch (NoSuchMethodException | SecurityException e) {
					return false;
				}
			})
			.findFirst();
		return (Optional<Convertable>) convertableClassOptional
				.map(c -> ReflectionUtils.getNewInstance(c).orElse(null))
				.filter(c -> c != null);
	}
	*/
	
}
