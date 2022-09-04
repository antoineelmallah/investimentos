package br.com.mallah.investimentos.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.mallah.investimentos.controller.enums.SheetType;
import br.com.mallah.investimentos.service.ProcessImportDataService;
import br.com.mallah.investimentos.sheet.service.SheetReaderService;

@RestController
@RequestMapping("process-sheet")
public class ProcessSheetController {

	private Map<Class<?>, ProcessImportDataService<?,?>> processImportDataServiceMap;
	
	@Autowired
	public ProcessSheetController(List<ProcessImportDataService<?, ?>> processImportDataServices, SheetReaderService sheetReaderService) {
		processImportDataServiceMap = processImportDataServices.stream()
			.collect(Collectors.toMap(ProcessImportDataService::sheetAnnotatedClass, Function.identity()));
	}
	
	@PostMapping()
	public String process(@RequestParam("file") MultipartFile file, SheetType sheetType) {
		
		System.out.println("Processando planilha: " + file.getName());
		
		try (InputStream inputStream = new BufferedInputStream(file.getInputStream())) {
			
			processImportDataServiceMap.get(sheetType.getSheetModelClass()).process(inputStream, sheetType);
			
			return "Planilha processada";
		
		} catch (IOException e) {
			e.printStackTrace();
		}

		return "Erro processando planilha";
	
	}
	
}
