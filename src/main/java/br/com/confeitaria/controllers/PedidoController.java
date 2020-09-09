package br.com.confeitaria.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.confeitaria.domains.Bairro;
import br.com.confeitaria.domains.Endereco;
import br.com.confeitaria.domains.ItemPedido;
import br.com.confeitaria.domains.Pedido;
import br.com.confeitaria.domains.Produto;
import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioBairro;
import br.com.confeitaria.repositories.RepositorioEndereco;
import br.com.confeitaria.repositories.RepositorioFormaDeEntrega;
import br.com.confeitaria.repositories.RepositorioFormaPagamento;
import br.com.confeitaria.repositories.RepositorioPedido;
import br.com.confeitaria.repositories.RepositorioProduto;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Controller
@RequestMapping("/pedidos")
public class PedidoController {

	@Autowired
	private RepositorioEndereco repositorioEndereco;
	@Autowired
	private RepositorioBairro repositorioBairro;
	@Autowired
	private RepositorioFormaDeEntrega repositorioFormaDeEntrega;
	@Autowired
	private RepositorioFormaPagamento repositorioFormaPagamento;
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

		// Se o usuário não estiver logado, levará para a tela de login
		if (usuario == null) {
			resultado = new ModelAndView("home/login");
			return resultado;
		}

		Pedido pedido = repositorioPedido.findByUsuarioAndStatus(usuario, "pendente");
		Endereco enderecoDefault = repositorioEndereco.findByUsuarioAndEnderecoDefault(usuario, "default");
		pedido.setEndereco(enderecoDefault);

		// Somando os valores dos itens e adicionando ao valor total do pedido
		double valorTotal = 0.0;
		for (ItemPedido item : pedido.getItens()) {
			double valorItem = item.getProduto().getValor() * item.getQuantidade();
			valorTotal += valorItem;
		}
		pedido.setValorTotal(valorTotal);

		// Adicionando o valor total com a taxa de entrega
		String nome = pedido.getEndereco().getBairro();
		Bairro bairro = repositorioBairro.findByNome(nome);
		double valorComTaxa = valorTotal + bairro.getTaxaEntrega();
		resultado.addObject("valorcomtaxa", valorComTaxa);
		resultado.addObject("taxaentrega", bairro.getTaxaEntrega());

		resultado.addObject("pedido", pedido);
		resultado.addObject("itens", pedido.getItens());
		resultado.addObject("formaentrega", repositorioFormaDeEntrega.findAll());
		resultado.addObject("formapagamento", repositorioFormaPagamento.findAll());
		return resultado;
	}

	@PostMapping("/finalizar-pedido")
	public String finalizarPedido(Pedido pedido) {
		for (ItemPedido item : pedido.getItens()) {
			Produto produto = item.getProduto();
			int quantidadeAtual = produto.getQuantidadeDisponivel() - item.getQuantidade();
			produto.setQuantidadeDisponivel(quantidadeAtual);
			repositorioProduto.save(produto);
		}

		pedido.setStatus("aguardando confirmação");
		pedido.setDataPedido(new Date());

		repositorioPedido.save(pedido);
		return "redirect:/produtos/lista";
	}

	@GetMapping("/meus-pedidos")
	public ModelAndView meusPedidos() {
		ModelAndView resultado = new ModelAndView("pedidos/meus-pedidos");

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);

		// Se o usuário não estiver logado, levará para a tela de login
		if (usuario == null) {
			resultado = new ModelAndView("home/login");
			return resultado;
		}

		List<Pedido> pedidos = repositorioPedido.findByUsuarioAndStatusNot(usuario, "pendente");

		// Separando os pedidos pendentes
		List<Pedido> pedidosPendentes = new LinkedList<Pedido>();
		for (Pedido p : pedidos) {
			if (p.getStatus() != "entregue") {
				pedidosPendentes.add(p);
			}
		}

		resultado.addObject("pedidospendentes", pedidosPendentes);
		resultado.addObject("pedidos", pedidos);
		return resultado;
	}

	@GetMapping("/adm/pedidos-pendentes")
	public ModelAndView pedidosPendentes() {
		ModelAndView resultado = new ModelAndView("pedidos/pedidos-pendentes");
		List<String> statusNotIn = new LinkedList<String>();
		statusNotIn.add("entregue");
		statusNotIn.add("pendente");
		resultado.addObject("pedidos", repositorioPedido.findByStatusNotIn(statusNotIn));
		return resultado;
	}

	@GetMapping("/adm/alterar-status/{id}")
	public ModelAndView alterarStatus(@PathVariable Long id) {
		ModelAndView resultado = new ModelAndView("pedidos/alterar-status");
		resultado.addObject("pedido", repositorioPedido.getOne(id));
		return resultado;
	}

	@PostMapping("/adm/alterar-status")
	public String alterarStatus(Pedido pedido) {
		if (pedido.getStatus().equals("entregue"))
			pedido.setDataEntrega(new Date());
		repositorioPedido.save(pedido);
		return "redirect:/pedidos/adm/pedidos-pendentes";
	}

	@GetMapping("/adm/vendas")
	public ModelAndView vendas() {
		ModelAndView resultado = new ModelAndView("pedidos/vendas");
		Calendar c = Calendar.getInstance();
		Date dataInicial = new Date();
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		dataInicial = c.getTime();
		// Selecionando os pedidos entregues no mês corrente
		List<Pedido> pedidos = repositorioPedido.findByStatusAndDataEntregaBetween("entregue", dataInicial, new Date());

		int totalDeVendas = 0;
		double valorArrecadado = 0.0;
		for (Pedido pedido : pedidos) {
			totalDeVendas++;
			valorArrecadado += pedido.getValorTotal();
		}

		resultado.addObject("totaldevendas", totalDeVendas);
		resultado.addObject("valorarrecadado", valorArrecadado);
		resultado.addObject("datainicial", dataInicial);
		resultado.addObject("datafinal", new Date());

		return resultado;
	}

}
