package br.com.mallah.investimentos.mapper;

import org.apache.logging.log4j.util.Strings;

import br.com.mallah.investimentos.persistence.entity.FluxoMovimento;
import br.com.mallah.investimentos.persistence.entity.InstituicaoEntity;
import br.com.mallah.investimentos.persistence.entity.MovimentacaoEntity;
import br.com.mallah.investimentos.persistence.entity.ProdutoEntity;
import br.com.mallah.investimentos.persistence.entity.TipoMovimentacaoEntity;
import br.com.mallah.investimentos.sheet.model.MovimentacaoSheet;

public class MovimentacaoMapper implements SheetToEntityMapper<MovimentacaoSheet, MovimentacaoEntity> {

	@Override
	public MovimentacaoEntity map(MovimentacaoSheet sheet) {
		
		return MovimentacaoEntity.newInstance(
				FluxoMovimento.getBySheetValue(sheet.getEntradaSaida()).orElse(null), 
				sheet.getData(), 
				sheet.getQuantidade() == null ? null : sheet.getQuantidade().intValue(), 
				sheet.getPrecoUnitario(), 
				Strings.isBlank(sheet.getMovimentacao()) ? null : TipoMovimentacaoEntity.newInstance(sheet.getMovimentacao()), 
				ProdutoEntity.newInstance(sheet.getProduto()), 
				InstituicaoEntity.newInstance(sheet.getInstituicao()));
	}

}
