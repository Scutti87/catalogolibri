package com.rest.catalogolibri.service;

import java.util.List;
import com.rest.catalogolibri.dto.CategoriaDto;

public interface ICategoriaService {

	CategoriaDto inserisci(CategoriaDto c);

	boolean aggiorna(CategoriaDto c);

	boolean elimina(long id);

	CategoriaDto getCategoriaById(long id);

	List<CategoriaDto> getListaCategorie();
	
	List<CategoriaDto> getListaCategorieByAutore(long id);
	
}
