package com.rest.catalogolibri.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LibroDto {

	private long id;
	@NotBlank(message = "Campo titolo obbligatorio")
	private String titolo;
	@Min(value = 1500)
	private int annoPubblicazione;
	@NotNull(message = "Campo prezzo è obbligatorio.")
	private double prezzo;
	private AutoreDto autore;
	private CategoriaDto categoria;

	public LibroDto() {
	}

	public LibroDto(String titolo, int annoPubblicazione, double prezzo, AutoreDto autore, CategoriaDto categoria) {
		this.titolo = titolo;
		this.annoPubblicazione = annoPubblicazione;
		this.autore = autore;
		this.categoria = categoria;
		this.prezzo = prezzo;
	}

	public LibroDto(long id, String titolo, int annoPubblicazione, double prezzo, AutoreDto autore,
			CategoriaDto categoria) {
		this.id = id;
		this.titolo = titolo;
		this.annoPubblicazione = annoPubblicazione;
		this.autore = autore;
		this.categoria = categoria;
		this.prezzo = prezzo;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public int getAnnoPubblicazione() {
		return annoPubblicazione;
	}

	public void setAnnoPubblicazione(int annoPublicazione) {
		this.annoPubblicazione = annoPublicazione;
	}

	public AutoreDto getAutore() {
		return autore;
	}

	public void setAutore(AutoreDto autore) {
		this.autore = autore;
	}

	public CategoriaDto getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaDto categoria) {
		this.categoria = categoria;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

}
