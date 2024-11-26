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
import com.rest.catalogolibri.dto.CategoriaDto;
import com.rest.catalogolibri.service.ICategoriaService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/categoria")
public class CategoriaController {

	private ICategoriaService cs;

	@Autowired
	public CategoriaController(ICategoriaService cs) {
		this.cs = cs;
	}

	@RequestMapping(value = "aggiorna/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CategoriaDto> aggiornaCategoria(@Valid @RequestBody CategoriaDto c,
			@PathVariable("id") long id) {

		c.setId(id);
		boolean modificato = cs.aggiorna(c);

		if (!modificato) {
			throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Modifica fallita!!!");
		}
		return new ResponseEntity<CategoriaDto>(c, HttpStatus.OK);
	}

	@RequestMapping("cerca/{id}")
	public CategoriaDto cercaCategoria(@PathVariable("id") long id) {

		CategoriaDto c = cs.getCategoriaById(id);

		return c;
	}

	@RequestMapping("lista")
	public Iterable<CategoriaDto> getListaCategorie() {

		List<CategoriaDto> lista = cs.getListaCategorie();

		return lista;
	}

	@PostMapping("inserisci")
	public CategoriaDto inserisciCategoria(@Valid @RequestBody CategoriaDto a) {

		CategoriaDto categoria = cs.inserisci(a);

		if (categoria == null) {
			throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Inserimento fallito!!!");
		}

		return categoria;
	}

	@DeleteMapping("elimina/{id}")
	public void eliminaCategoria(@PathVariable("id") long id) {

		boolean eliminato = cs.elimina(id);

		if (!eliminato) {
			throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Eliminazione fallita!!!");
		}
	}

	@RequestMapping("listaByAutore/{id}")
	public Iterable<CategoriaDto> getListaCategorieByAutore(@PathVariable("id") long id) {

		List<CategoriaDto> lista = cs.getListaCategorieByAutore(id);

		return lista;
	}

}
