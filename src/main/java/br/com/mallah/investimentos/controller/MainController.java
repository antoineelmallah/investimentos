package br.com.mallah.investimentos.controller;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import br.com.mallah.investimentos.mapper.MovimentacaoMapper;
import br.com.mallah.investimentos.persistence.entity.MovimentacaoEntity;
import br.com.mallah.investimentos.persistence.service.InsertMovimentacaoService;
import br.com.mallah.investimentos.sheet.model.MovimentacaoSheet;
import br.com.mallah.investimentos.sheet.service.SheetReaderService;

@RestController
@RequestMapping("main")
public class MainController {

	@Autowired
	private InsertMovimentacaoService insertMovimentacaoService;
	
	@Autowired
	private SheetReaderService sheetReaderService;
	
	@PostMapping()
	public String uploadFile(@RequestParam("file") MultipartFile file) {
		
		System.out.println("Processando planilha: " + file.getName());
		
		try (InputStream inputStream = new BufferedInputStream(file.getInputStream())) {
			
			List<MovimentacaoSheet> sheets = sheetReaderService.processSheet(new BufferedInputStream(file.getInputStream()), MovimentacaoSheet.class);
			
			List<MovimentacaoEntity> entities = sheets.stream()
					.map(s -> new MovimentacaoMapper().map(s))
					.collect(Collectors.toList());
			
			insertMovimentacaoService.process(entities);
			
			return "Planilha processada";
		
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return "Erro processando planilha";
	
	}
	
}
