package br.com.mallah.investimentos.mapper;

import br.com.mallah.investimentos.persistence.entity.BaseEntity;
import br.com.mallah.investimentos.sheet.model.SheetModel;

public interface MapSheetToEntity <T extends BaseEntity<?>> extends SheetModel {

	T toEntity();
	
}
