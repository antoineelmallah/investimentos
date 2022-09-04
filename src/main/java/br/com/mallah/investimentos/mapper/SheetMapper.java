package br.com.mallah.investimentos.mapper;

import org.apache.logging.log4j.util.Strings;

import br.com.mallah.investimentos.persistence.entity.BaseEntity;
import br.com.mallah.investimentos.persistence.entity.FluxoMovimento;
import br.com.mallah.investimentos.persistence.entity.InstituicaoEntity;
import br.com.mallah.investimentos.persistence.entity.MovimentacaoEntity;
import br.com.mallah.investimentos.persistence.entity.ProdutoEntity;
import br.com.mallah.investimentos.persistence.entity.TipoMovimentacaoEntity;
import br.com.mallah.investimentos.sheet.model.MovimentacaoSheet;
import br.com.mallah.investimentos.sheet.model.SheetModel;

public interface SheetMapper {

	public static MovimentacaoEntity map(MovimentacaoSheet sheet) {
		return map(sheet, s -> {
			return MovimentacaoEntity.newInstance(
					FluxoMovimento.getBySheetValue(s.getEntradaSaida()).orElse(null), 
					s.getData(), 
					s.getQuantidade() == null ? null : s.getQuantidade().intValue(), 
					s.getPrecoUnitario(), 
					Strings.isBlank(s.getMovimentacao()) ? null : TipoMovimentacaoEntity.newInstance(s.getMovimentacao()), 
					ProdutoEntity.newInstance(s.getProduto()), 
					InstituicaoEntity.newInstance(s.getInstituicao()));
		});
	}
	
	private static <S extends SheetModel, E extends BaseEntity<?>> E map(S sheet, SheetToEntityMapper<S, E> mapper) {
		return mapper.map(sheet);
	}
}
