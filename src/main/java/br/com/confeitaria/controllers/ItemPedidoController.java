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
import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioEndereco;
import br.com.confeitaria.repositories.RepositorioFormaDeEntrega;
import br.com.confeitaria.repositories.RepositorioFormaPagamento;
import br.com.confeitaria.repositories.RepositorioItemPedido;
import br.com.confeitaria.repositories.RepositorioPedido;
import br.com.confeitaria.repositories.RepositorioProduto;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Controller
@RequestMapping("/item-pedido")
public class ItemPedidoController {
	
	@Autowired
	private RepositorioEndereco repositorioEndereco;
	@Autowired
	private RepositorioItemPedido repositorioItemPedido;
	@Autowired
	private RepositorioPedido repositorioPedido;
	@Autowired
	private RepositorioProduto repositorioProduto;
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	@Autowired
	private RepositorioFormaPagamento repositorioFormaPagamento;
	@Autowired
	private RepositorioFormaDeEntrega repositorioFormaDeEntrega;
	
	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView resultado = new ModelAndView("item-pedido/listar");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		Pedido pedido = repositorioPedido.findByUsuarioAndStatus(usuario, "pendente");
		if(pedido != null)
			resultado.addObject("pedido", pedido);
		
		resultado.addObject("endereco", repositorioEndereco.findByEnderecoDefault("default"));
		resultado.addObject("formasDePagamento", repositorioFormaPagamento.findAll());
		resultado.addObject("formasDeEntrega", repositorioFormaDeEntrega.findAll());
		return resultado;
	}
	
	@PostMapping("/adicionarItemCompra/{id}")
	public String adicionarCompra(@PathVariable Long id, ItemPedido itemPedido) {
		//Resgatar usuário logado para salvar o endereço
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		itemPedido.setUsuario(usuario);
		itemPedido.setStatus("pendente");
		itemPedido.setProduto(repositorioProduto.getOne(id));
		repositorioItemPedido.save(itemPedido);
		
		Pedido pedido = repositorioPedido.findByUsuarioAndStatus(usuario, "pendente");
			if(pedido != null) {
				pedido.setItem(repositorioItemPedido.findByUsuarioAndStatus(usuario, "pendente"));
				repositorioPedido.save(pedido);
			} else {
				Pedido novoPedido = new Pedido();
				novoPedido.setUsuario(usuario);
				novoPedido.setStatus("pendente");
				novoPedido.setItem(repositorioItemPedido.findByUsuarioAndStatus(usuario, "pendente"));
				repositorioPedido.save(novoPedido);
			}
		return "redirect:/produtos/lista";
	}

}
