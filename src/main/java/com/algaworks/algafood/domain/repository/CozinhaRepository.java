package com.algaworks.algafood.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.algaworks.algafood.domain.model.Cozinha;

/*
*
- repository faz a abstração de acesso a dados (dao)
- em repository não se pensa em implementação , se pensa em negócio, imitar uma coleção.
- o que um repositorory de cozinha tem que ter? TEm que listar a cozinha? Buscar? Adicionar
- no repository não diz nada sobre o mecanismo usado para persister os dados
*
*/

@Repository
public interface CozinhaRepository extends CustomJpaRepository<Cozinha, Long> {

	List<Cozinha> findTodasByNomeContaining(String nome);
	
	Optional<Cozinha> findByNome(String nome);
	
	boolean existsByNome(String nome);
	
}