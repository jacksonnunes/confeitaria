package br.com.confeitaria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.confeitaria.domains.ItemPedido;
import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioItemPedido;
import br.com.confeitaria.repositories.RepositorioProduto;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Controller
@RequestMapping("/item-pedido")
public class ItemPedidoController {
	
	@Autowired
	private RepositorioItemPedido repositorioItemPedido;
	@Autowired
	private RepositorioProduto repositorioProduto;
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@GetMapping("/adicionarCompra/{id}")
	public String adicionarCompra(@PathVariable Long id) {
		//Resgatar usuário logado para salvar o endereço
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setUsuario(usuario);
		itemPedido.setProduto(repositorioProduto.getOne(id));
		repositorioItemPedido.save(itemPedido);
		return "redirect:/produtos/lista";
	}

}
