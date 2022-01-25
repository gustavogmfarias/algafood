package com.algaworks.algafood.domain.repository;

/*
 *
- repository faz a abstração de acesso a dados (dao)
- em repository não se pensa em implementação , se pensa em negócio, imitar uma coleção.
- o que um repositorory de estado tem que ter? TEm que listar a estado? Buscar? Adicionar
- no repository não diz nada sobre o mecanismo usado para persister os dados
 *
 */

import java.util.List;

import com.algaworks.algafood.domain.model.Estado;

public interface EstadoRepository {

	List<Estado> listar();
	Estado buscar(Long id);
	Estado salvar(Estado estado);
	void remover(Estado estado);

}
