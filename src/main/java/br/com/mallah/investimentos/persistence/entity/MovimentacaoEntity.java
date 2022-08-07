package br.com.mallah.investimentos.persistence.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movimentacao")
public class MovimentacaoEntity implements BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private FluxoMovimento fluxo;
	
	@Column(nullable = false)
	private LocalDate data;
	
	@Column(nullable = false)
	private Integer quantidade;
	
	@Column(nullable = false)
	private BigDecimal precoUnitario;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private TipoMovimentacaoEntity tipo;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private ProdutoEntity produto;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private InstituicaoEntity instituicao;
	
	protected MovimentacaoEntity() {}

	@Override
	public Long getId() {
		return id;
	}

	public FluxoMovimento getFluxo() {
		return fluxo;
	}

	public LocalDate getData() {
		return data;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public BigDecimal getPrecoUnitario() {
		return precoUnitario;
	}

	public BigDecimal getValorTransacao() {
		return BigDecimal.valueOf(quantidade).multiply(precoUnitario);
	}

	public TipoMovimentacaoEntity getTipo() {
		return tipo;
	}

	public void setTipo(TipoMovimentacaoEntity tipo) {
		this.tipo = tipo;
	}

	public ProdutoEntity getProduto() {
		return produto;
	}

	public void setProduto(ProdutoEntity produto) {
		this.produto = produto;
	}

	public InstituicaoEntity getInstituicao() {
		return instituicao;
	}

	public void setInstituicao(InstituicaoEntity instituicao) {
		this.instituicao = instituicao;
	}

	public static MovimentacaoEntity newInstance(FluxoMovimento fluxo, LocalDate data, Integer quantidade, BigDecimal precoUnitario, TipoMovimentacaoEntity tipo, ProdutoEntity produto, InstituicaoEntity instituicao) {
		MovimentacaoEntity entity = new MovimentacaoEntity();
		entity.fluxo = fluxo;
		entity.data = data;
		entity.quantidade = quantidade;
		entity.precoUnitario = precoUnitario;
		entity.tipo = tipo;
		entity.produto = produto;
		entity.instituicao = instituicao;
		return entity;
	}

}
