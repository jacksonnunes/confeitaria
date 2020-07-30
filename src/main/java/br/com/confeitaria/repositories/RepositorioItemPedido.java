package br.com.confeitaria.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.confeitaria.domains.ItemPedido;

public interface RepositorioItemPedido extends JpaRepository<ItemPedido, Long> {

}
