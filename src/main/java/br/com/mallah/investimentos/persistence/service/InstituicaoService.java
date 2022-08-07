package br.com.mallah.investimentos.persistence.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mallah.investimentos.persistence.entity.InstituicaoEntity;
import br.com.mallah.investimentos.persistence.repository.InstituicaoRepository;

@Service
public class InstituicaoService {

	@Autowired
	private InstituicaoRepository instituicaoRepository;
	
	public List<InstituicaoEntity> getPersistedEntities(List<InstituicaoEntity> instituicoes) {
		Set<String> descTipo = instituicoes.stream()
			.map(InstituicaoEntity::getDescricao)
			.collect(Collectors.toSet());
		List<InstituicaoEntity> tiposPersistidos = instituicaoRepository.findAllByDescricao(descTipo);
		List<InstituicaoEntity> tiposToPersist = instituicoes.stream()
			.filter(t -> !tiposPersistidos.contains(t))
			.collect(Collectors.toList());
		if (!tiposToPersist.isEmpty()) {
			tiposPersistidos.addAll(instituicaoRepository.saveAll(tiposToPersist));
		}
		return tiposPersistidos;
	}
	
}
