package com.algaworks.algafood.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;

/*
 Por que usar o service? Porque no futuro pode ter uma regra de negócio e seria aqui que colocaríamos.
 Por exemplo no caso de salvar, não permitir ter o mesmo nome.
 
 Classe serviço é negócio, ela não pode ter acesso as classes de implementação. Ela sequer tem ocnhecimento do protocolo http
 */

@Service
public class CadastroCidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;

	@Autowired
	private EstadoRepository estadoRepository;
	
	public Cidade salvar(Cidade cidade) {
		 Long estadoId = cidade.getEstado().getId();
		 
         Estado estado = estadoRepository.findById(estadoId).orElseThrow(() -> new EntidadeNaoEncontradaException(
 				String.format("Não existe cadastro de estado com código %d", estadoId)));
 		// esse orelsethrow evita
 		// de transformar
 		// o retorno cozinha em optional, mas,
 		// ele também pode jogar a exceçao caso
 		// não encontre.

 		cidade.setEstado(estado);
 		return cidadeRepository.save(cidade);

	}

	public void excluir(Long cidadeId) {

		try {
			cidadeRepository.deleteById(cidadeId);

		} catch (EmptyResultDataAccessException e) {
			throw new EntidadeNaoEncontradaException(
					String.format("Não existe cadastro de cidade com o código %d", cidadeId));

		} catch (DataIntegrityViolationException e) {
			throw new EntidadeEmUsoException(
					String.format("Cidade de código %d não pode ser removida, pois está em uso", cidadeId));
		}

	}

}
