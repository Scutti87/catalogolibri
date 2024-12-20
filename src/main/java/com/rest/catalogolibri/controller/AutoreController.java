package com.rest.catalogolibri.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.rest.catalogolibri.dto.AutoreDto;
import com.rest.catalogolibri.service.IAutoreService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/autore")
public class AutoreController {

	private IAutoreService as;

	@Autowired
	public AutoreController(IAutoreService as) {
		this.as = as;
	}

	@RequestMapping(value = "/aggiorna/{id}", method = RequestMethod.PUT)
	public ResponseEntity<AutoreDto> aggiornaAutore(@Valid @RequestBody AutoreDto a, @PathVariable("id") long id) {

		a.setId(id);
		boolean modificato = as.aggiorna(a);

		if (!modificato) {
			throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Modifica fallita!!!");
		}
		return new ResponseEntity<AutoreDto>(a, HttpStatus.OK);
	}

	@RequestMapping("/cerca/{id}")
	public AutoreDto cercaAutore(@PathVariable("id") long id) {

		AutoreDto a = as.getAutoreById(id);

		return a;
	}

	@RequestMapping("lista")
	public Iterable<AutoreDto> getListaAutori() {

		List<AutoreDto> lista = as.getListaAutori();

		return lista;
	}

	@PostMapping("inserisci")
	public AutoreDto inserisciAutore(@Valid @RequestBody AutoreDto a) {

		AutoreDto autore = as.inserisci(a);

		if (autore == null) {
			throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Inserimento fallito!!!");
		}

		return autore;
	}

	@DeleteMapping("elimina/{id}")
	public void eliminaAutore(@PathVariable("id") long id) {

		boolean eliminato = as.elimina(id);

		if (!eliminato) {
			throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Eliminazione fallita!!!");
		}
	}
	
	@RequestMapping("listaByCategoria/{id}")
	public Iterable<AutoreDto> getListaAutoriByCategoria(@PathVariable("id") long id) {

		List<AutoreDto> lista = as.getListaAutoriByCategoria(id);

		return lista;
	}

}
