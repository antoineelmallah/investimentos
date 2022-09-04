package br.com.mallah.investimentos.sheet.model;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.apache.logging.log4j.util.Strings;

import br.com.mallah.investimentos.mapper.MapSheetToEntity;
import br.com.mallah.investimentos.persistence.entity.FluxoMovimento;
import br.com.mallah.investimentos.persistence.entity.InstituicaoEntity;
import br.com.mallah.investimentos.persistence.entity.MovimentacaoEntity;
import br.com.mallah.investimentos.persistence.entity.ProdutoEntity;
import br.com.mallah.investimentos.persistence.entity.TipoMovimentacaoEntity;
import br.com.mallah.investimentos.sheet.annotation.Column;
import br.com.mallah.investimentos.sheet.annotation.Sheet;

@Sheet(name = "Movimentação", fileNamePattern = "^movimentacao-\\d{4}(-\\d{2}){5}\\.xlsx$")
public class MovimentacaoSheet implements MapSheetToEntity<MovimentacaoEntity> {

	@Column(name = "Entrada/Saída")
	private String entradaSaida;
	
	@Column(name = "Data")
	private LocalDate data;
	
	@Column(name = "Movimentação")
	private String movimentacao;
	
	@Column(name = "Produto")
	private String produto;
	
	@Column(name = "Instituição")
	private String instituicao;
	
	@Column(name = "Quantidade")
	private BigDecimal quantidade;
	
	@Column(name = "Preço unitário")
	private BigDecimal precoUnitario;
	
	@Column(name = "Valor da Operação")
	private BigDecimal valorOperacao;

	public String getEntradaSaida() {
		return entradaSaida;
	}

	public LocalDate getData() {
		return data;
	}

	public String getMovimentacao() {
		return movimentacao;
	}

	public String getProduto() {
		return produto;
	}

	public String getInstituicao() {
		return instituicao;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public BigDecimal getValorOperacao() {
		return valorOperacao;
	}

	@Override
	public String toString() {
		return "Movimentacao [entradaSaida=" + entradaSaida + ", data=" + data + ", movimentacao=" + movimentacao
				+ ", produto=" + produto + ", instituicao=" + instituicao + ", quantidade=" + quantidade
				+ ", precoUnitario=" + precoUnitario + ", valorOperacao=" + valorOperacao + "]";
	}
	
	@Override
	public MovimentacaoEntity toEntity() {
		return MovimentacaoEntity.newInstance(
				FluxoMovimento.getBySheetValue(this.getEntradaSaida()).orElse(null), 
				this.getData(), 
				this.getQuantidade() == null ? null : this.getQuantidade().intValue(), 
				this.getPrecoUnitario(), 
				Strings.isBlank(this.getMovimentacao()) ? null : TipoMovimentacaoEntity.newInstance(this.getMovimentacao()), 
				ProdutoEntity.newInstance(this.getProduto()), 
				InstituicaoEntity.newInstance(this.getInstituicao()));
	}
	
}
