package br.com.confeitaria.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.confeitaria.domains.ItemPedido;
import br.com.confeitaria.domains.Pedido;
import br.com.confeitaria.domains.Produto;
import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioCategoria;
import br.com.confeitaria.repositories.RepositorioPedido;
import br.com.confeitaria.repositories.RepositorioProduto;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

	@Autowired
	private RepositorioProduto repositorioProduto;
	@Autowired
	private RepositorioCategoria repositorioCategoria;
	@Autowired
	private RepositorioPedido repositorioPedido;
	@Autowired
	private RepositorioUsuario repositorioUsuario;

	@GetMapping("/lista")
	public ModelAndView listar() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);

		ModelAndView resultado = new ModelAndView("produtos/listar");
		resultado.addObject("produtos", repositorioProduto.findAll());
		resultado.addObject("categorias", repositorioCategoria.findAll(Sort.by(Sort.Direction.ASC, "nome")));
		resultado.addObject("itempedido", new ItemPedido());

		// Pegando a quantidade de itens para exibir no carrinho
		Pedido pedido = repositorioPedido.findByUsuarioAndStatus(usuario, "pendente");
		resultado.addObject("quantidadeDeItens", pedido.getItem().size());
		return resultado;
	}

	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView resultado = new ModelAndView("produtos/cadastrar");
		resultado.addObject("produto", new Produto());
		return resultado;
	}

	@PostMapping("/cadastrar")
	public String cadastrar(@Valid Produto produto, BindingResult result) {
		if (result.hasErrors())
			return "produtos/cadastrar";
		repositorioProduto.save(produto);
		return "redirect:/produtos/lista";
	}

}
