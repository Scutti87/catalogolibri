package com.rest.catalogolibri.model;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "autori")
public class Autore implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	private String cognome;
	@OneToMany(mappedBy = "autore")
	private List<Libro> libriScritti;

	public Autore() {
	}

	public Autore(String nome, String cognome, List<Libro> libriScritti) {
		this.nome = nome;
		this.cognome = cognome;
		this.libriScritti = libriScritti;
	}

	public Autore(long id, String nome, String cognome, List<Libro> libriScritti) {
		this.id = id;
		this.nome = nome;
		this.cognome = cognome;
		this.libriScritti = libriScritti;
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

	public List<Libro> getLibriScritti() {
		return libriScritti;
	}

	public void setLibriScritti(List<Libro> libriScritti) {
		this.libriScritti = libriScritti;
	}

}
