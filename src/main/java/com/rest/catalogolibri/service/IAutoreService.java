package com.rest.catalogolibri.service;

import java.util.List;

import com.rest.catalogolibri.dto.AutoreDto;

public interface IAutoreService {

	AutoreDto inserisci(AutoreDto a);

	boolean aggiorna(AutoreDto a);

	boolean elimina(long id);

	AutoreDto getAutoreById(long id);

	List<AutoreDto> getListaAutori();
	
	List<AutoreDto> getListaAutoriByCategoria(long id);
	
}
