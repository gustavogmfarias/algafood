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

import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;

@Component //forma de injetar com componente
public class CozinhaRepositoryImpl implements CozinhaRepository {

	@PersistenceContext //forma de injectar uma persistencia
	private EntityManager manager;

	@Override
	public List<Cozinha> listar() {
		TypedQuery<Cozinha>	query = manager.createQuery("from Cozinha", Cozinha.class);
		return query.getResultList();

	}

	@Override
	public Cozinha buscar(Long id) {
		return manager.find(Cozinha.class, id);
	}

	@Transactional //annotation para iniciar e fechar uma transação no banco
	@Override
	public Cozinha salvar(Cozinha cozinha) {
		return manager.merge(cozinha);
	}

	@Transactional
	@Override
	public void remover(Cozinha cozinha) {
		cozinha = buscar(cozinha.getId());
		manager.remove(cozinha);
	}


}
