package br.com.mallah.investimentos.persistence.entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "instituicao")
public class InstituicaoEntity implements BaseEntity<Long> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(unique = true, nullable = false)
	private String descricao;

	protected InstituicaoEntity() {}
	
	@Override
	public Long getId() {
		return id;
	}

	public String getDescricao() {
		return descricao;
	}

	public static InstituicaoEntity newInstance(String descricao) {
		InstituicaoEntity entity = new InstituicaoEntity();
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
		InstituicaoEntity other = (InstituicaoEntity) obj;
		return Objects.equals(descricao, other.descricao);
	}

}
