package br.com.confeitaria.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.confeitaria.domains.Produto;
import br.com.confeitaria.repositories.RepositorioProduto;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {
	
	@Autowired
	private RepositorioProduto repositorioProduto;
	
	@GetMapping("/lista")
	public ModelAndView listar() {
		ModelAndView resultado = new ModelAndView("produtos/listar");
		resultado.addObject("produtos", repositorioProduto.findAll());
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
		if(result.hasErrors())
			return "produtos/cadastrar";
		repositorioProduto.save(produto);
		return "redirect:/produtos/lista";
	}

}
