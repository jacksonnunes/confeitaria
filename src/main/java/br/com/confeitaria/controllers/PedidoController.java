package br.com.confeitaria.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.confeitaria.domains.Endereco;
import br.com.confeitaria.domains.ItemPedido;
import br.com.confeitaria.domains.Pedido;
import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioEndereco;
import br.com.confeitaria.repositories.RepositorioFormaDeEntrega;
import br.com.confeitaria.repositories.RepositorioFormaPagamento;
import br.com.confeitaria.repositories.RepositorioPedido;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {
	
	@Autowired
	private RepositorioEndereco repositorioEndereco;
	@Autowired
	private RepositorioFormaDeEntrega repositorioFormaDeEntrega;
	@Autowired
	private RepositorioFormaPagamento repositorioFormaPagamento;
	@Autowired
	private RepositorioPedido repositorioPedido;
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView resultado = new ModelAndView("pedidos/listar");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		Pedido pedido = repositorioPedido.findByUsuarioAndStatus(usuario, "pendente");
		Endereco enderecoDefault = repositorioEndereco.findByEnderecoDefault("default");
		pedido.setEndereco(enderecoDefault);
		
		//Somando os valores dos itens e adicionando ao valor total do pedido
		double valorTotal = 0.0;
		for(ItemPedido item: pedido.getItem()) {
			double valorItem = item.getProduto().getValor() * item.getQuantidade();
			valorTotal += valorItem;
		}
		pedido.setValorTotal(valorTotal);
		
		//Adicionando o valor total com a taxa de entrega
		double valorComTaxa = pedido.getValorTotal() + pedido.getEndereco().getBairro().getTaxaEntrega();
		resultado.addObject("valorcomtaxa", valorComTaxa);
		
		resultado.addObject("pedido", pedido);
		resultado.addObject("formaentrega", repositorioFormaDeEntrega.findAll());
		resultado.addObject("formapagamento", repositorioFormaPagamento.findAll());
		return resultado;
	}

}
