package com.rest.catalogolibri.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.rest.catalogolibri.model.Categoria;
import jakarta.transaction.Transactional;

@Repository
public interface ICategoriaRepository extends JpaRepository<Categoria, Long>{

	@Query(value = "SELECT c.id FROM categorie c ORDER BY c.id DESC LIMIT 1", nativeQuery = true)
	long recuperaUltimoId();
	
	@Transactional
	@Modifying
	@Query(value = "ALTER TABLE categorie AUTO_INCREMENT = :id", nativeQuery = true)
	void setAutoincrement(@Param("id") long id);
	
	@Query(value = "SELECT c.id, c.nome FROM categorie c JOIN libri l ON l.categoria_id = c.id JOIN autori a ON l.autore_id = a.id WHERE a.id = :id GROUP BY c.id, c.nome", nativeQuery = true)
	List<Categoria> getListaCategorieByAutore(@Param("id") long id);
}
