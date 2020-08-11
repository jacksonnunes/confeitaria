package br.com.confeitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.Usuario;

public interface RepositorioUsuario extends JpaRepository<Usuario, Long> {
	
	Usuario findByEmail(String email);
	
	boolean existsByEmail(String email);

}
