package com.rest.catalogolibri.service;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rest.catalogolibri.dto.AutoreDto;
import com.rest.catalogolibri.dto.CategoriaDto;
import com.rest.catalogolibri.exception.DataConflictException;
import com.rest.catalogolibri.model.Autore;
import com.rest.catalogolibri.model.Categoria;
import com.rest.catalogolibri.repository.IAutoreRepository;
import com.rest.catalogolibri.repository.ICategoriaRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class CategoriaServiceImpl implements ICategoriaService {

	private ModelMapper mm;
	private ICategoriaRepository cr;
	private IAutoreRepository ar;

	@Autowired
	public CategoriaServiceImpl(ModelMapper mm, ICategoriaRepository cr, IAutoreRepository ar) {
		this.mm = mm;
		this.cr = cr;
		this.ar = ar;
	}

	@Override
	public CategoriaDto inserisci(CategoriaDto c) {

		if (cr.findAll().stream().anyMatch(cat -> cat.getNome().equalsIgnoreCase(c.getNome().trim()))) {
			throw new DataConflictException("Categoria già presente in db");
		}

		long id = cr.recuperaUltimoId() + 1;
		cr.setAutoincrement(id);
		c.setId(id);
		cr.save(mm.map(c, Categoria.class));

		return mm.map(cr.findById(id).get(), CategoriaDto.class);
	}

	@Override
	public boolean aggiorna(CategoriaDto c) {

		cr.findById(c.getId()).orElseThrow(
				() -> new EntityNotFoundException(String.format("Categoria con id %d non presente in db", c.getId())));

		List<Categoria> lista = cr.findAll();
		lista.remove(cr.findById(c.getId()).get());

		if (cr.findAll().stream().anyMatch(cat -> cat.getNome().equalsIgnoreCase(c.getNome().trim()))) {
			throw new DataConflictException("Categoria già presente in db");
		}

		cr.save(mm.map(c, Categoria.class));
		return true;
	}

	@Override
	public boolean elimina(long id) {

		cr.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format("Categoria con id %d non presente in db", id)));

		cr.deleteById(id);

		return cr.findById(id).isEmpty();
	}

	@Override
	public CategoriaDto getCategoriaById(long id) {

		Categoria c = cr.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format("Categoria con id %d non presente in db", id)));

		return mm.map(c, CategoriaDto.class);
	}

	@Override
	public List<CategoriaDto> getListaCategorie() {

		List<CategoriaDto> listaDto = new ArrayList<CategoriaDto>();
		List<Categoria> lista = cr.findAll();

		if (lista.isEmpty()) {
			throw new EntityNotFoundException("Nessuna categoria presente in db");
		}

		lista.forEach(a -> listaDto.add(mm.map(a, CategoriaDto.class)));
		return listaDto;
	}

	@Override
	public List<CategoriaDto> getListaCategorieByAutore(long id) {

		ar.findById(id).orElseThrow(
				() -> new EntityNotFoundException(String.format("Autore con id %d non presente in db", id)));

		List<CategoriaDto> listaDto = new ArrayList<CategoriaDto>();
		List<Categoria> lista = cr.getListaCategorieByAutore(id);

		if (lista.isEmpty()) {
			throw new EntityNotFoundException("Nessuna categoria presente in db relativa all'autore con id " + id);
		}

		lista.forEach(a -> listaDto.add(mm.map(a, CategoriaDto.class)));
		return listaDto;
	}
}
