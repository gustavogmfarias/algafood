package com.algaworks.algafood.domain.repository;

/*
 *
- repository faz a abstração de acesso a dados (dao)
- em repository não se pensa em implementação , se pensa em negócio, imitar uma coleção.
- o que um repositorory de cidade tem que ter? TEm que listar a cidade? Buscar? Adicionar
- no repository não diz nada sobre o mecanismo usado para persister os dados
 *
 */

import java.util.List;

import com.algaworks.algafood.domain.model.Cidade;

public interface CidadeRepository {

	List<Cidade> listar();
	Cidade buscar(Long id);
	Cidade salvar(Cidade cidade);
	void remover(Cidade cidade);

}
