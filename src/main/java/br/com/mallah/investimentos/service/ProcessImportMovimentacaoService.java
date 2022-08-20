package br.com.mallah.investimentos.service;

import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mallah.investimentos.mapper.MovimentacaoMapper;
import br.com.mallah.investimentos.persistence.entity.MovimentacaoEntity;
import br.com.mallah.investimentos.persistence.service.InsertMovimentacaoService;
import br.com.mallah.investimentos.sheet.model.MovimentacaoSheet;
import br.com.mallah.investimentos.sheet.service.SheetReaderService;

@Service
public class ProcessImportMovimentacaoService implements ProcessImportDataService<MovimentacaoSheet> {

	@Autowired
	private SheetReaderService sheetReaderService;
	
	@Autowired
	private InsertMovimentacaoService insertMovimentacaoService;
	
	@Override
	public void process(InputStream inputStream) {
		
		List<MovimentacaoEntity> entities = sheetReaderService.processSheet(inputStream, sheetAnnotatedClass()).stream()
			.map(s -> new MovimentacaoMapper().map(s))
			.collect(Collectors.toList());
		
		insertMovimentacaoService.process(entities);
	}

	@Override
	public Class<MovimentacaoSheet> sheetAnnotatedClass() {
		return MovimentacaoSheet.class;
	}
	
}
