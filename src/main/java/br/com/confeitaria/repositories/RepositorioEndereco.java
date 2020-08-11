package br.com.confeitaria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.Endereco;
import br.com.confeitaria.domains.Usuario;

public interface RepositorioEndereco extends JpaRepository<Endereco, Long> {
	
	List<Endereco> findByUsuario(Usuario usuario);
	
	Endereco findByEnderecoDefault(String palavra);

}
