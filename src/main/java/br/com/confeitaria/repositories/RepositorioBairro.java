package br.com.confeitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.Bairro;

public interface RepositorioBairro extends JpaRepository<Bairro, Long> {
	
	Bairro findByNome(String nome);

}
