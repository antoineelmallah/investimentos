package br.com.mallah.investimentos.mapper;

import br.com.mallah.investimentos.persistence.entity.BaseEntity;
import br.com.mallah.investimentos.sheet.model.SheetModel;

public interface SheetToEntityMapper <S extends SheetModel, E extends BaseEntity<?>> {
	
	E map(S sheet);

}
