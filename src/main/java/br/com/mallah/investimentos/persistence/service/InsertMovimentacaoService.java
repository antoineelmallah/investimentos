package br.com.mallah.investimentos.persistence.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.mallah.investimentos.persistence.entity.InstituicaoEntity;
import br.com.mallah.investimentos.persistence.entity.MovimentacaoEntity;
import br.com.mallah.investimentos.persistence.entity.ProdutoEntity;
import br.com.mallah.investimentos.persistence.entity.TipoMovimentacaoEntity;
import br.com.mallah.investimentos.persistence.repository.MovimentacaoRepository;

@Service
public class InsertMovimentacaoService {
	
	@Autowired
	private TipoMovimentacaoService tipoMovimentacaoService;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private InstituicaoService instituicaoService;

	@Autowired
	private MovimentacaoRepository movimentacaoRepository;
	
	@Transactional
	public void process(List<MovimentacaoEntity> entities) {
		
		excluirMovimentacoesExistentes(entities);

		List<TipoMovimentacaoEntity> tipos = tipoMovimentacaoService.getPersistedEntities(entities.stream()
				.map(MovimentacaoEntity::getTipo)
				.distinct()
				.collect(Collectors.toList()));
		
		List<ProdutoEntity> produtos = produtoService.getPersistedEntities(entities.stream()
				.map(MovimentacaoEntity::getProduto)
				.distinct()
				.collect(Collectors.toList()));
		
		List<InstituicaoEntity> instituicoes = instituicaoService.getPersistedEntities(entities.stream()
				.map(MovimentacaoEntity::getInstituicao)
				.distinct()
				.collect(Collectors.toList()));
		
		entities.stream().forEach(m -> {
			setTipoPersistido(tipos, m);
			setProdutoPersistido(produtos, m);
			setInstituicaoPersistida(instituicoes, m);
		});

		movimentacaoRepository.saveAll(entities);
		
	}

	private void excluirMovimentacoesExistentes(List<MovimentacaoEntity> entities) {
		Optional<LocalDate> dataInicioOptional = entities.stream().map(MovimentacaoEntity::getData).min((di, df) -> di.compareTo(df));
		Optional<LocalDate> dataFimOptional = entities.stream().map(MovimentacaoEntity::getData).max((di, df) -> di.compareTo(df));
		
		if (dataInicioOptional.isEmpty() || dataFimOptional.isEmpty())
			throw new IllegalStateException("Data inicio e data fim nulas");
		
		movimentacaoRepository.deleteByDateRange(dataInicioOptional.get(), dataFimOptional.get());
	}

	private void setTipoPersistido(List<TipoMovimentacaoEntity> tipos, MovimentacaoEntity m) {
		TipoMovimentacaoEntity tipo = tipos.stream()
			.filter(t -> t.equals(m.getTipo()))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("Tipo não encontrado"));
		m.setTipo(tipo);
	}

	private void setProdutoPersistido(List<ProdutoEntity> produtos, MovimentacaoEntity m) {
		ProdutoEntity produto = produtos.stream()
			.filter(t -> t.equals(m.getProduto()))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("Produto não encontrado"));
		m.setProduto(produto);
	}

	private void setInstituicaoPersistida(List<InstituicaoEntity> instituicoes, MovimentacaoEntity m) {
		InstituicaoEntity instituicao = instituicoes.stream()
			.filter(t -> t.equals(m.getInstituicao()))
			.findFirst()
			.orElseThrow(() -> new IllegalStateException("Instituicao não encontrada"));
		m.setInstituicao(instituicao);
	}

}
