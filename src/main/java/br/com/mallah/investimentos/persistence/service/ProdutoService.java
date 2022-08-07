package br.com.mallah.investimentos.persistence.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mallah.investimentos.persistence.entity.ProdutoEntity;
import br.com.mallah.investimentos.persistence.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;
	
	public List<ProdutoEntity> getPersistedEntities(List<ProdutoEntity> produtos) {
		Set<String> descTipo = produtos.stream()
			.map(ProdutoEntity::getDescricao)
			.collect(Collectors.toSet());
		List<ProdutoEntity> tiposPersistidos = produtoRepository.findAllByDescricao(descTipo);
		List<ProdutoEntity> tiposToPersist = produtos.stream()
			.filter(t -> !tiposPersistidos.contains(t))
			.collect(Collectors.toList());
		if (!tiposToPersist.isEmpty()) {
			tiposPersistidos.addAll(produtoRepository.saveAll(tiposToPersist));
		}
		return tiposPersistidos;
	}
	
}
