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
import com.rest.catalogolibri.dto.LibroDto;
import com.rest.catalogolibri.service.ILibroService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("api/libro")
public class LibroController {

	private ILibroService ls;

	@Autowired
	public LibroController(ILibroService ls) {
		this.ls = ls;
	}

	@RequestMapping(value = "aggiorna/{id}", method = RequestMethod.PUT)
	public ResponseEntity<LibroDto> aggiornaLibro(@Valid @RequestBody LibroDto l, @PathVariable("id") long id) {

		l.setId(id);
		boolean modificato = ls.aggiorna(l);

		if (!modificato) {
			throw new ResponseStatusException(HttpStatus.NOT_MODIFIED, "Modifica fallita!!!");
		}
		return new ResponseEntity<LibroDto>(l, HttpStatus.OK);
	}

	@RequestMapping("cerca/{id}")
	public LibroDto cercaLibro(@PathVariable("id") long id) {

		LibroDto l = ls.getLibroById(id);

		return l;
	}
	
	@RequestMapping("lista")
	public Iterable<LibroDto> getListaLibri(){
		
		List<LibroDto> lista = ls.getListaLibri();
		
		return lista;
	}
	
	@PostMapping("inserisci")
	public LibroDto inserisciLibro(@Valid @RequestBody LibroDto l) {
		
		LibroDto libro = ls.inserisci(l);
		
		if (libro == null) {
			throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Inserimento fallito!!!");
		}
		
		return libro;		
	}

	@DeleteMapping("elimina/{id}")
	public void eliminaLibro(@PathVariable("id") long id) {
		
		boolean eliminato = ls.elimina(id);
		
		if (!eliminato) {
			throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Eliminazione fallita!!!");
		}
	}
	
	@RequestMapping("listaByAutore/{id}")
	public List<LibroDto> getListaLibriByAutore(@PathVariable("id") long id){
		
		List<LibroDto> lista = ls.getListaLibriByAutore(id);
		
		return lista;
	}
	
	@RequestMapping("listaByCategoria/{id}")
	public List<LibroDto> getListaLibriByCategoria(@PathVariable("id") long id){
		
		List<LibroDto> lista = ls.getListaLibriByCategoria(id);
		
		return lista;
	}
	
}
