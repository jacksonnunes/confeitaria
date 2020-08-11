package br.com.confeitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.Categoria;

public interface RepositorioCategoria extends JpaRepository<Categoria, Long> {

}
