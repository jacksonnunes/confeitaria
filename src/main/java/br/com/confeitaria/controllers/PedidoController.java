package br.com.confeitaria.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.confeitaria.domains.ItemPedido;
import br.com.confeitaria.domains.Pedido;
import br.com.confeitaria.domains.Produto;
import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioItemPedido;
import br.com.confeitaria.repositories.RepositorioPedido;
import br.com.confeitaria.repositories.RepositorioProduto;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private RepositorioItemPedido repositorioItemPedido;
	@Autowired
	private RepositorioPedido repositorioPedido;
	@Autowired
	private RepositorioProduto repositorioProduto;
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView resultado = new ModelAndView("pedidos/listar");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		resultado.addObject("pedidos", repositorioPedido.findByUsuario(usuario));
		return resultado;
	}
	
	@PostMapping("/efetuar")
	public String efetuarPedido() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		Pedido pedido = repositorioPedido.findByUsuarioAndStatus(usuario, "pendente");
		pedido.setStatus("efetuado");
		pedido.setDataPedido(new Date());
		List<ItemPedido> itensPedido = repositorioItemPedido.findByUsuarioAndStatus(usuario, "pendente");
		for(ItemPedido item: itensPedido) {
			item.setStatus("concluído");
			Produto produto = item.getProduto();
			int quantidadeDisponivel = produto.getQuantidadeDisponivel();
			produto.setQuantidadeDisponivel(quantidadeDisponivel - item.getQuantidade());
			repositorioProduto.save(produto);
			repositorioItemPedido.save(item);
		}
		repositorioPedido.save(pedido);
		
		return "redirect:/pedidos/listar";
	}

}
