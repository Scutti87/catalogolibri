package com.rest.catalogolibri.service;

import java.util.List;

import com.rest.catalogolibri.dto.LibroDto;

public interface ILibroService {
	
	LibroDto inserisci(LibroDto l);
	
	boolean aggiorna(LibroDto l);
	
	boolean elimina(long id);
	
	LibroDto getLibroById(long id);
	
	List<LibroDto> getListaLibri();
	
	List<LibroDto> getListaLibriByAutore(long id);
	
	List<LibroDto> getListaLibriByCategoria(long id);

}
