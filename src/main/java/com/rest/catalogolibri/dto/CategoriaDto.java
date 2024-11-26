package com.rest.catalogolibri.dto;

import java.util.List;
import jakarta.validation.constraints.NotBlank;

public class CategoriaDto {

	private long id;
	@NotBlank(message = "Campo nome obbligatorio")
	private String nome;
//	private List<LibroDto> listaLibri;

	public CategoriaDto() {
	}

	public CategoriaDto(String nome, List<LibroDto> listaLibri) {
		this.nome = nome;
//		this.listaLibri = listaLibri;
	}

	public CategoriaDto(long id, String nome, List<LibroDto> listaLibri) {
		this.id = id;
		this.nome = nome;
//		this.listaLibri = listaLibri;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

//	public List<LibroDto> getListaLibri() {
//		return listaLibri;
//	}
//
//	public void setListaLibri(List<LibroDto> listaLibri) {
//		this.listaLibri = listaLibri;
//	}
}
