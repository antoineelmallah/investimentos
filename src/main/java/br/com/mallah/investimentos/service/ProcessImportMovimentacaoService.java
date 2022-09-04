package br.com.mallah.investimentos.service;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mallah.investimentos.controller.enums.SheetType;
import br.com.mallah.investimentos.mapper.MapSheetToEntity;
import br.com.mallah.investimentos.persistence.entity.MovimentacaoEntity;
import br.com.mallah.investimentos.persistence.service.PersistService;
import br.com.mallah.investimentos.sheet.model.MovimentacaoSheet;
import br.com.mallah.investimentos.sheet.service.SheetReaderService;

@Service
public class ProcessImportMovimentacaoService implements ProcessImportDataService<MovimentacaoSheet, MovimentacaoEntity> {

	@Autowired
	private SheetReaderService sheetReaderService;
	
	@Autowired
	private PersistService<MovimentacaoEntity> persistService;
	
	@Override
	public List<MovimentacaoEntity> process(InputStream inputStream, SheetType sheetType) {
		
		List<MovimentacaoSheet> sheets = sheetReaderService.processSheet(new BufferedInputStream(inputStream), MovimentacaoSheet.class);
		
		List<MovimentacaoEntity> entities = sheets.stream()
				.map(MapSheetToEntity::toEntity)
				.collect(Collectors.toList());
		
		return persistService.process(entities);
		
	}

	@Override
	public Class<MovimentacaoSheet> sheetAnnotatedClass() {
		return MovimentacaoSheet.class;
	}

}
