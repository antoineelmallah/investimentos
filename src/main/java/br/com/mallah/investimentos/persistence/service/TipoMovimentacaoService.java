package br.com.mallah.investimentos.persistence.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mallah.investimentos.persistence.entity.TipoMovimentacaoEntity;
import br.com.mallah.investimentos.persistence.repository.TipoMovimentacaoRepository;

@Service
public class TipoMovimentacaoService {

	@Autowired
	private TipoMovimentacaoRepository tipoMovimentacaoRepository;
	
	public List<TipoMovimentacaoEntity> getPersistedEntities(List<TipoMovimentacaoEntity> tipos) {
		Set<String> descTipo = tipos.stream()
			.map(TipoMovimentacaoEntity::getDescricao)
			.collect(Collectors.toSet());
		List<TipoMovimentacaoEntity> tiposPersistidos = tipoMovimentacaoRepository.findAllByDescricao(descTipo);
		List<TipoMovimentacaoEntity> tiposToPersist = tipos.stream()
			.filter(t -> !tiposPersistidos.contains(t))
			.collect(Collectors.toList());
		if (!tiposToPersist.isEmpty()) {
			tiposPersistidos.addAll(tipoMovimentacaoRepository.saveAll(tiposToPersist));
		}
		return tiposPersistidos;
	}
	
}
