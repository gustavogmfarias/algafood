/*
 - Fica em "infrasctrure" e não no package domain, porque agora é algo que tem mais a ver de como serão persistido os dados,
 quais conexões, como acessar os dados, naõ tem nada a ver com o negócio, ou seja, com o dominio.
 */

package com.algaworks.algafood.infrastructure.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;

@Component //forma de injetar com componente
public class CidadeRepositoryImpl implements CidadeRepository {

	@PersistenceContext //forma de injectar uma persistencia
	private EntityManager manager;

	@Override
	public List<Cidade> listar() {
		TypedQuery<Cidade>	query = manager.createQuery("from Cidade", Cidade.class);
		return query.getResultList();

	}

	@Override
	public Cidade buscar(Long id) {
		return manager.find(Cidade.class, id);
	}

	@Transactional //annotation para iniciar e fechar uma transação no banco
	@Override
	public Cidade salvar(Cidade cidade) {
		return manager.merge(cidade);
	}

	@Transactional
	@Override
	public void remover(Cidade cidade) {
		cidade = buscar(cidade.getId());
		manager.remove(cidade);
	}


}
