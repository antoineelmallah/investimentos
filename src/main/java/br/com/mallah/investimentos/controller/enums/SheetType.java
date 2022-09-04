package br.com.mallah.investimentos.controller.enums;

import br.com.mallah.investimentos.mapper.MapSheetToEntity;
import br.com.mallah.investimentos.sheet.model.MovimentacaoSheet;

public enum SheetType {

	MOVIMENTACAO(MovimentacaoSheet.class);
	
	private Class<? extends MapSheetToEntity> sheetModelClass;
	
	SheetType(Class<? extends MapSheetToEntity> sheetModelClass) {
		this.sheetModelClass = sheetModelClass;
	}

	public Class<? extends MapSheetToEntity> getSheetModelClass() {
		return sheetModelClass;
	}

}
