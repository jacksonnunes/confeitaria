package br.com.confeitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.Produto;

public interface RepositorioProduto extends JpaRepository<Produto, Long> {

}
