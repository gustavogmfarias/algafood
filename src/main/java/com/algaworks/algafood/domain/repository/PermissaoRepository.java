package com.algaworks.algafood.domain.repository;

/*
 *
- repository faz a abstração de acesso a dados (dao)
- em repository não se pensa em implementação , se pensa em negócio, imitar uma coleção.
- o que um repositorory de Permissao tem que ter? TEm que listar a Permissao? Buscar? Adicionar
- no repository não diz nada sobre o mecanismo usado para persister os dados
 *
 */

import java.util.List;

import com.algaworks.algafood.domain.model.Permissao;

public interface PermissaoRepository {

	List<Permissao> listar();
	Permissao buscar(Long id);
	Permissao salvar(Permissao permissao);
	void remover(Permissao permissao);

}
