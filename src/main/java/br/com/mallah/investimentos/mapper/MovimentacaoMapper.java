package br.com.mallah.investimentos.mapper;

import br.com.mallah.investimentos.persistence.entity.FluxoMovimento;
import br.com.mallah.investimentos.persistence.entity.MovimentacaoEntity;
import br.com.mallah.investimentos.sheet.model.MovimentacaoSheet;

public class MovimentacaoMapper implements SheetToEntityMapper<MovimentacaoSheet, MovimentacaoEntity> {

	@Override
	public MovimentacaoEntity map(MovimentacaoSheet sheet) {
		
		return MovimentacaoEntity.newInstance(
				FluxoMovimento.getBySheetValue(sheet.getEntradaSaida()).orElse(null), 
				null, 
				null, 
				null, 
				null, 
				null, 
				null);
	}

}
