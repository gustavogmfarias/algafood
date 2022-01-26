package com.algaworks.algafood.domain.repository;

/*
 *
- repository faz a abstração de acesso a dados (dao)
- em repository não se pensa em implementação , se pensa em negócio, imitar uma coleção.
- o que um repositorory de cozinha tem que ter? TEm que listar a cozinha? Buscar? Adicionar
- no repository não diz nada sobre o mecanismo usado para persister os dados
 *
 */

import java.util.List;

import com.algaworks.algafood.domain.model.Cozinha;

public interface CozinhaRepository {

	List<Cozinha> listar();
	Cozinha buscar(Long id);
	Cozinha salvar(Cozinha cozinha);
	void remover(Long id);

}
