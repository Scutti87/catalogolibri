package com.rest.catalogolibri.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "libri")
public class Libro implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String titolo;
	private int annoPubblicazione;
	private double prezzo;
	@ManyToOne
	private Autore autore;
	@ManyToOne
	private Categoria categoria;

	public Libro() {
	}

	public Libro(String titolo, int annoPubblicazione, double prezzo, Autore autore, Categoria categoria) {
		this.titolo = titolo;
		this.annoPubblicazione = annoPubblicazione;
		this.autore = autore;
		this.categoria = categoria;
		this.prezzo = prezzo;
	}

	public Libro(long id, String titolo, int annoPubblicazione, double prezzo, Autore autore, Categoria categoria) {
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

	public Autore getAutore() {
		return autore;
	}

	public void setAutore(Autore autore) {
		this.autore = autore;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public double getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(double prezzo) {
		this.prezzo = prezzo;
	}

}
