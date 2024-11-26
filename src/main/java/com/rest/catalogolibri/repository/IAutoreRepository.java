package com.rest.catalogolibri.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rest.catalogolibri.model.Autore;
import jakarta.transaction.Transactional;

@Repository
public interface IAutoreRepository extends JpaRepository<Autore, Long> {
	
	@Query(value = "SELECT a.id FROM autori a ORDER BY a.id DESC LIMIT 1", nativeQuery = true)
	long recuperaUltimoId();
	
	@Transactional
	@Modifying
	@Query(value = "ALTER TABLE autori AUTO_INCREMENT = :id", nativeQuery = true)
	void setAutoincrement(@Param("id") long id);
	
	@Query(value = "SELECT a.id, a.nome, a.cognome FROM autori a JOIN libri l ON l.autore_id = a.id JOIN categorie c ON l.categoria_id = c.id WHERE c.id = :id GROUP BY a.id, a.nome, a.cognome", nativeQuery = true)
	List<Autore> getListaAutoriByCategoria(@Param("id") long id);
	
}
