package br.com.confeitaria.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.Pedido;
import br.com.confeitaria.domains.Usuario;

public interface RepositorioPedido extends JpaRepository<Pedido, Long> {
	
	List<Pedido> findByUsuarioAndStatusNot(Usuario usuario, String status);
	
	Pedido findByUsuarioAndStatus(Usuario usuario, String status);
	
	List<Pedido> findByStatusNotIn(List<String> status);
	
	List<Pedido> findByStatusAndDataEntregaBetween(String status, Date dataEntregaInicial, Date dataEntregaFinal);

}
