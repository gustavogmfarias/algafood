package com.algaworks.algafood.api.controller;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping(value = "/restaurantes")
public class RestauranteController {

	@Autowired
	private RestauranteRepository restauranteRepository;
	
	@Autowired
	private CadastroRestauranteService cadastroRestaurante;
	
	@GetMapping
	public List<Restaurante> listar() {
		
		
		return restauranteRepository.findAll();
	}
	
	@GetMapping("/{restauranteId}")
	public ResponseEntity<Restaurante> buscar(@PathVariable Long restauranteId) {
		Optional<Restaurante> restaurante = restauranteRepository.findById(restauranteId);
		
		if (restaurante.isPresent()) {
			return ResponseEntity.ok(restaurante.get());
		}
		
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping
	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
		try {
			restaurante = cadastroRestaurante.salvar(restaurante);
			
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(restaurante);
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	
	@PutMapping("/{restauranteId}")
	public ResponseEntity<?> atualizar(@PathVariable Long restauranteId,
			@RequestBody Restaurante restaurante) {
		try {
			Restaurante restauranteAtual = restauranteRepository
					.findById(restauranteId).orElse(null);
			
			if (restauranteAtual != null) {
				BeanUtils.copyProperties(restaurante, restauranteAtual, 
				        "id", "formasPagamento", "endereco", "dataCadastro", "produtos"); // essa função faz tudo que est[a dentro de
				//restaurante entrar dentor de restaurante atual, menos o que está entre aspas
				
				restauranteAtual = cadastroRestaurante.salvar(restauranteAtual);
				return ResponseEntity.ok(restauranteAtual);
			}
			
			return ResponseEntity.notFound().build();
		
		} catch (EntidadeNaoEncontradaException e) {
			return ResponseEntity.badRequest()
					.body(e.getMessage());
		}
	}
	
	@PatchMapping("/{restauranteId}")
	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
			@RequestBody Map<String, Object> campos) {
		Restaurante restauranteAtual = restauranteRepository
				.findById(restauranteId).orElse(null);
		
		if (restauranteAtual == null) {
			return ResponseEntity.notFound().build();
		}
		
		merge(campos, restauranteAtual);
		
		return atualizar(restauranteId, restauranteAtual);
	}

	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
		ObjectMapper objectMapper = new ObjectMapper();
		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
		
		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
			field.setAccessible(true);
			
			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
			
//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
			
			ReflectionUtils.setField(field, restauranteDestino, novoValor);
		});
	}
	
}


//package com.algaworks.algafood.api.controller;
//
//import java.lang.reflect.Field;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//
//import org.springframework.beans.BeanUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.util.ReflectionUtils;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PatchMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
//import com.algaworks.algafood.domain.model.Restaurante;
//import com.algaworks.algafood.domain.repository.RestauranteRepository;
//import com.algaworks.algafood.domain.service.CadastroRestauranteService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//@RestController
//@RequestMapping(value = "/restaurantes")
//public class RestauranteController {
//
//	@Autowired
//	private RestauranteRepository restauranteRepository;
//
//	@Autowired
//	private CadastroRestauranteService cadastroRestauranteService;
//
//	@GetMapping
//	public List<Restaurante> listar() {
//		return restauranteRepository.findAll();
//	}
//
//	@PostMapping
//	public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
//		// o ? do responseentity significa que pode ser qualquer coisa, uma string, um
//		// objeto..
//
//		try {
//			restaurante = cadastroRestauranteService.salvar(restaurante);
//			return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.badRequest().body(e.getMessage());
//		}
//	}
//
//	@GetMapping("/{restauranteId}")
//	public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long id) {
//		Optional<Restaurante> restaurante = restauranteRepository.findById(id); // o findby id não retona uma restaurante e sim um
//																	// optinal, que é uma clase do java para evitar o
//																	// null pointer. Com o optional, ele pode ou nõ ter
//																	// a classe buscada
//
//		if (restaurante.isPresent()) {
//			return ResponseEntity.ok(restaurante.get()); // uso o get aqui por agora ela não é uma restaurante e sim um
//														// "optional", então uso o get para pegar a restaurante de dentro do
//														// optional
//		}
//		return ResponseEntity.notFound().build();
//	}
//	
//	@DeleteMapping("/{restauranteId}")
//	public ResponseEntity<Restaurante> remover(@PathVariable Long restauranteId) {
//
//		try {
//			cadastroRestauranteService.remover(restauranteId);
//			return ResponseEntity.ok().build();
//		} catch (EntidadeNaoEncontradaException e) {
//			return ResponseEntity.notFound().build();
//		} catch (DataIntegrityViolationException e) {
//			return ResponseEntity.status(HttpStatus.CONFLICT).build();
//		}
//	}
//
//	@PutMapping("/{restauranteId}")
//	public ResponseEntity<Restaurante> atualizar(@PathVariable Long restauranteId, @RequestBody Restaurante restaurante) {
//		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
//
//		if (restauranteAtual.isPresent()) {
//			BeanUtils.copyProperties(restaurante, restauranteAtual.get(), "id");
//			Restaurante restauranteSalva = cadastroRestauranteService.salvar(restauranteAtual.get());
//			return ResponseEntity.ok(restauranteSalva);
//		}
//
//		return ResponseEntity.notFound().build();
//	}
//
//	@PatchMapping("/{restauranteId}")
//	public ResponseEntity<?> atualizarParcial(@PathVariable Long restauranteId,
//			@RequestBody Map<String, Object> campos) {
//		Optional<Restaurante> restauranteAtual = restauranteRepository.findById(restauranteId);
//
//		if (!restauranteAtual.isPresent()) {
//			return ResponseEntity.notFound().build();
//		}
//
//		merge(campos, restauranteAtual.get());
//
//		return atualizar(restauranteId, restauranteAtual.get());
//	}
//
//	private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
//		ObjectMapper objectMapper = new ObjectMapper();
//		Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);
//
//		dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
//
//			Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
//			field.setAccessible(true);
//
//			Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);
//
//			System.out.println(nomePropriedade + " = " + valorPropriedade + " = " + novoValor);
//
//			ReflectionUtils.setField(field, restauranteDestino, novoValor);
//
//		});
//	}
//	
//	
//
//}
