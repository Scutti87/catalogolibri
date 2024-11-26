package com.rest.catalogolibri.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rest.catalogolibri.dto.LibroDto;
import com.rest.catalogolibri.exception.DataConflictException;
import com.rest.catalogolibri.model.Libro;
import com.rest.catalogolibri.repository.ILibroRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class LibroServiceImpl implements ILibroService {

	private ModelMapper mm;
	private ILibroRepository lr;

	@Autowired
	public LibroServiceImpl(ModelMapper mm, ILibroRepository lr) {
		this.mm = mm;
		this.lr = lr;
	}

	@Override
	public LibroDto inserisci(LibroDto l) {

		List<Libro> lista = lr.findAll();
		if (lista.stream().anyMatch(lib -> lib.getTitolo().equalsIgnoreCase(l.getTitolo())
				&& lib.getAutore().getId() == l.getAutore().getId())) {
			throw new DataConflictException("Libro inserito già presente in db");
		}
		long id = lr.recuperaUltimoId() + 1;
		lr.setAutoincrement(id);
		l.setId(id);
		lr.save(mm.map(l, Libro.class));

		return mm.map(lr.findById(id), LibroDto.class);
	}

	@Override
	public boolean aggiorna(LibroDto l) {

		Libro libro = lr.findById(l.getId()).orElseThrow(
				() -> new EntityNotFoundException(String.format("Libro con id %d non presente in db", l.getId())));

		List<Libro> lista = lr.findAll();
		lista.remove(libro);

		if (lista.stream().anyMatch(lib -> lib.getTitolo().equalsIgnoreCase(l.getTitolo())
				&& lib.getAutore().getId() == l.getAutore().getId())) {
			throw new DataConflictException("Libro inserito già presente in db");
		}

		lr.save(mm.map(l, Libro.class));
		return true;
	}

	@Override
	public boolean elimina(long id) {

		lr.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Libro con id %d non presente in db", id)));

		lr.deleteById(id);

		return lr.findById(id).isEmpty();
	}

	@Override
	public LibroDto getLibroById(long id) {

		Libro l = lr.findById(id)
				.orElseThrow(() -> new EntityNotFoundException(String.format("Libro con id %d non trovato", id)));

		return mm.map(l, LibroDto.class);
	}

	@Override
	public List<LibroDto> getListaLibri() {

		List<LibroDto> listaDto = new ArrayList<LibroDto>();
		List<Libro> lista = lr.findAll();

		if (lista.isEmpty()) {
			throw new EntityNotFoundException("Nessun libro presente in db");
		}

		lista.forEach(l -> listaDto.add(mm.map(l, LibroDto.class)));
		return listaDto;

	}

	@Override
	public List<LibroDto> getListaLibriByAutore(long id) {

		List<LibroDto> listaDto = new ArrayList<LibroDto>();
		List<Libro> lista = lr.getListaLibriByAutore(id);
		
		if (lista.isEmpty()) {
			throw new EntityNotFoundException("Nessun libro presente in db con id autore " + id);
		}
		lista.forEach(l -> listaDto.add(mm.map(l, LibroDto.class)));
		
		return listaDto;
	}

	@Override
	public List<LibroDto> getListaLibriByCategoria(long id) {		
		
		List<LibroDto> listaDto = new ArrayList<LibroDto>();
		List<Libro> lista = lr.getListaLibriByCategoria(id);
		
		if (lista.isEmpty()) {
			throw new EntityNotFoundException("Nessun libro presente in db con id categoria " + id);
		}
		lista.forEach(l -> listaDto.add(mm.map(l, LibroDto.class)));
		
		return listaDto;
	}

}
