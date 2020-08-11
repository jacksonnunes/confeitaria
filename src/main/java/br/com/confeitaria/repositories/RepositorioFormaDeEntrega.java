package br.com.confeitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.FormaDeEntrega;

public interface RepositorioFormaDeEntrega extends JpaRepository<FormaDeEntrega, Long> {

}
