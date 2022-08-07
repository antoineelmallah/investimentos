package br.com.mallah.investimentos.sheet.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import br.com.mallah.investimentos.sheet.annotation.Column;
import br.com.mallah.investimentos.sheet.annotation.Sheet;

@Sheet(name = "Movimentação", fileNamePattern = "^movimentacao-\\d{4}(-\\d{2}){5}\\.xlsx$")
public class MovimentacaoSheet implements SheetModel {

	@Column(name = "Entrada/Saída")
	private String entradaSaida;
	
	@Column(name = "Data")
	private LocalDateTime data;
	
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

	public LocalDateTime getData() {
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
	
}
