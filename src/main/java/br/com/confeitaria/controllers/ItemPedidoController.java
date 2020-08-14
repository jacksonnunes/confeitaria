package br.com.confeitaria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.confeitaria.domains.ItemPedido;
import br.com.confeitaria.domains.Pedido;
import br.com.confeitaria.domains.Produto;
import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioPedido;
import br.com.confeitaria.repositories.RepositorioProduto;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Controller
@RequestMapping("/item-pedido")
public class ItemPedidoController {
	
	@Autowired
	private RepositorioPedido repositorioPedido;
	@Autowired
	private RepositorioProduto repositorioProduto;
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@GetMapping("/comprar/{id}")
	public ModelAndView comprar(@PathVariable Long id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		ModelAndView resultado = new ModelAndView("item-pedido/comprar");
		Produto produto = repositorioProduto.getOne(id);
		ItemPedido itemPedido = new ItemPedido();
		itemPedido.setProduto(produto);
		resultado.addObject("itempedido", itemPedido);
		
		//Pegando a quantidade de itens para exibir no carrinho
		Pedido pedido = repositorioPedido.findByUsuarioAndStatus(usuario, "pendente");
		resultado.addObject("quantidadeDeItens", pedido.getItem().size());
		return resultado;
	}
	
	@PostMapping("/comprar")
	public String comprar(ItemPedido item) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		item.setUsuario(usuario);
		
		//Verificando se existe pedido pendente, caso contrário, cria um novo pedido
		Pedido pedido = repositorioPedido.findByUsuarioAndStatus(usuario, "pendente");
		if (pedido == null) {
			pedido = new Pedido();
			pedido.setUsuario(usuario);
			pedido.setStatus("pendente");
		}
		
		pedido.getItem().add(item);
		repositorioPedido.save(pedido);
		
		return "redirect:/produtos/lista";
	}
	
}
