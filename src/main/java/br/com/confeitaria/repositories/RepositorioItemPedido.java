package br.com.confeitaria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.ItemPedido;
import br.com.confeitaria.domains.Usuario;

public interface RepositorioItemPedido extends JpaRepository<ItemPedido, Long> {
	
	List<ItemPedido> findByUsuario(Usuario usuario);
	
	List<ItemPedido> findByUsuarioAndStatus(Usuario usuario, String status);

}
