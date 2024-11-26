package com.rest.catalogolibri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rest.catalogolibri.model.Libro;
import java.util.List;
import jakarta.transaction.Transactional;

@Repository
public interface ILibroRepository extends JpaRepository<Libro, Long> {

	@Query(value = "SELECT l.id FROM libri l ORDER BY l.id DESC LIMIT 1", nativeQuery = true)
	long recuperaUltimoId();
	
	@Transactional
	@Modifying
	@Query(value = "ALTER TABLE libri AUTO_INCREMENT = :id", nativeQuery = true)
	void setAutoincrement(@Param("id") long id);
	
	@Query(value = "SELECT * FROM libri l WHERE l.autore_id = :id", nativeQuery = true)
	List<Libro> getListaLibriByAutore(@Param("id") long id);
	
	@Query(value = "SELECT * FROM libri l WHERE l.categoria_id = :id", nativeQuery = true)
	List<Libro> getListaLibriByCategoria(@Param("id") long id);
}
