package br.com.confeitaria.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.Pedido;
import br.com.confeitaria.domains.Usuario;

public interface RepositorioPedido extends JpaRepository<Pedido, Long> {
	
	List<Pedido> findByUsuario(Usuario usuario);
	
	Pedido findByUsuarioAndStatus(Usuario usuario, String status);
	
	List<Pedido> findByStatusNot(String status);

}
