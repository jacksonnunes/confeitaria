package br.com.confeitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.Endereco;

public interface RepositorioEndereco extends JpaRepository<Endereco, Long> {

}
