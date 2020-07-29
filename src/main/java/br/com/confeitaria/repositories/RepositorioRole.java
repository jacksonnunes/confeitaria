package br.com.confeitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.Role;

public interface RepositorioRole extends JpaRepository<Role, Long> {
	
	Role findByRole(String roleName);

}
