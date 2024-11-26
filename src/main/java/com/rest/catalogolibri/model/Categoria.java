package com.rest.catalogolibri.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "categorie")
public class Categoria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String nome;
	@OneToMany(mappedBy = "categoria")
	private List<Libro> listaLibri;

	public Categoria() {
	}

	public Categoria(String nome, List<Libro> listaLibri) {
		this.nome = nome;
		this.listaLibri = listaLibri;
	}

	public Categoria(long id, String nome, List<Libro> listaLibri) {
		this.id = id;
		this.nome = nome;
		this.listaLibri = listaLibri;
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

	public List<Libro> getListaLibri() {
		return listaLibri;
	}

	public void setListaLibri(List<Libro> listaLibri) {
		this.listaLibri = listaLibri;
	}
}
