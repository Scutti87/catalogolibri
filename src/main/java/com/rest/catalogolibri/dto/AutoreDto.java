package com.rest.catalogolibri.dto;

import java.util.List;

import jakarta.validation.constraints.NotBlank;

public class AutoreDto {

	private long id;
	@NotBlank(message = "Campo nome obbligatorio")
	private String nome;
	@NotBlank(message = "Campo cognome obbligatorio")
	private String cognome;
//	private List<LibroDto> libriScritti;

	public AutoreDto() {
	}

	public AutoreDto(String nome, String cognome, List<LibroDto> libriScritti) {
		this.nome = nome;
		this.cognome = cognome;
//		this.libriScritti = libriScritti;
	}

	public AutoreDto(long id, String nome, String cognome, List<LibroDto> libriScritti) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
//		this.libriScritti = libriScritti;
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

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

//	public List<LibroDto> getLibriScritti() {
//		return libriScritti;
//	}
//
//	public void setLibriScritti(List<LibroDto> libriScritti) {
//		this.libriScritti = libriScritti;
//	}

}
