package br.com.mallah.investimentos.persistence.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "tipo_movimentacao")
public class TipoMovimentacaoEntity implements BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String descricao;

	protected TipoMovimentacaoEntity() {}
	
	@Override
	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static TipoMovimentacaoEntity newInstance(String descricao) {
		TipoMovimentacaoEntity entity = new TipoMovimentacaoEntity();
		entity.descricao = descricao;
		return entity;
	}

	@Override
	public int hashCode() {
		return Objects.hash(descricao);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TipoMovimentacaoEntity other = (TipoMovimentacaoEntity) obj;
		return Objects.equals(descricao, other.descricao);
	}

}
