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

import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;

@Component //forma de injetar com componente
public class EstadoRepositoryImpl implements EstadoRepository {

	@PersistenceContext //forma de injectar uma persistencia
	private EntityManager manager;

	@Override
	public List<Estado> listar() {
		TypedQuery<Estado>	query = manager.createQuery("from Estado", Estado.class);
		return query.getResultList();

	}

	@Override
	public Estado buscar(Long id) {
		return manager.find(Estado.class, id);
	}

	@Transactional //annotation para iniciar e fechar uma transação no banco
	@Override
	public Estado salvar(Estado estado) {
		return manager.merge(estado);
	}

	@Transactional
	@Override
	public void remover(Estado estado) {
		estado = buscar(estado.getId());
		manager.remove(estado);
	}


}
