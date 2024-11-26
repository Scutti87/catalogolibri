package com.rest.catalogolibri.service;

import java.util.ArrayList;
import java.util.List;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.rest.catalogolibri.dto.AutoreDto;
import com.rest.catalogolibri.exception.DataConflictException;
import com.rest.catalogolibri.model.Autore;
import com.rest.catalogolibri.repository.IAutoreRepository;
import com.rest.catalogolibri.repository.ICategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class AutoreServiceImpl implements IAutoreService {

	private ModelMapper mm;
	private IAutoreRepository ar;
	private ICategoriaRepository cr;

	@Autowired
	public AutoreServiceImpl(ModelMapper mm, IAutoreRepository ar, ICategoriaRepository cr) {
		this.mm = mm;
		this.ar = ar;
		this.cr = cr;
	}

	@Override
	public AutoreDto inserisci(AutoreDto a) {

		if (ar.findAll().stream().anyMatch(aut -> aut.getNome().equalsIgnoreCase(a.getNome().trim())
				&& aut.getCognome().equalsIgnoreCase(a.getCognome().trim()))) {
			throw new DataConflictException(
					String.format("Autore con nome %s e cognome %s già presente in db", a.getNome(), a.getCognome()));
		}

		long id = ar.recuperaUltimoId() + 1;
		ar.setAutoincrement(id);
		a.setId(id);
		ar.save(mm.map(a, Autore.class));

		return mm.map(ar.findById(id).get(), AutoreDto.class);
	}

	@Override
	public boolean aggiorna(AutoreDto a) {

		ar.findById(a.getId()).orElseThrow(
				() -> new EntityNotFoundException(String.format("Autore con id %d non presente in db", a.getId())));

		List<Autore> lista = ar.findAll();
		lista.remove(ar.findById(a.getId()).get());

		if (lista.stream().anyMatch(aut -> aut.getNome().equalsIgnoreCase(a.getNome().trim())
				&& aut.getCognome().equalsIgnoreCase(a.getCognome().trim()))) {
			throw new DataConflictException(
					String.format("Autore con nome %s e cognome %s già presente in db", a.getNome(), a.getCognome()));
		}

		ar.save(mm.map(a, Autore.class));
		return true;
	}

	@Override
	public boolean elimina(long id) {

		ar.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format("Autore con id %d non presente in db", id)));

		ar.deleteById(id);

		return ar.findById(id).isEmpty();
	}

	@Override
	public AutoreDto getAutoreById(long id) {

		Autore a = ar.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format("Autore con id %d non presente in db", id)));

		return mm.map(a, AutoreDto.class);
	}

	@Override
	public List<AutoreDto> getListaAutori() {

		List<AutoreDto> listaDto = new ArrayList<AutoreDto>();
		List<Autore> lista = ar.findAll();

		if (lista.isEmpty()) {
			throw new EntityNotFoundException("Nessun autore presente in db");
		}

		lista.forEach(a -> listaDto.add(mm.map(a, AutoreDto.class)));
		return listaDto;
	}

	@Override
	public List<AutoreDto> getListaAutoriByCategoria(long id) {

		cr.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format("Categoria con id %d non presente in db", id)));
		
		List<AutoreDto> listaDto = new ArrayList<AutoreDto>();
		List<Autore> lista = ar.getListaAutoriByCategoria(id);

		if (lista.isEmpty()) {
			throw new EntityNotFoundException("Nessun autore presente in db relativo alla categoria con id " + id);
		}

		lista.forEach(a -> listaDto.add(mm.map(a, AutoreDto.class)));
		return listaDto;
	}
}
