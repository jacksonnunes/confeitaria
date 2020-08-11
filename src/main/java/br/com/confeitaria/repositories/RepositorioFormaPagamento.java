package br.com.confeitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.FormaPagamento;

public interface RepositorioFormaPagamento extends JpaRepository<FormaPagamento, Long> {

}
