package br.com.confeitaria.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.confeitaria.domains.Bairro;
import br.com.confeitaria.repositories.RepositorioBairro;

@Controller
@RequestMapping("/bairros")
public class BairroController {
	
	@Autowired
	private RepositorioBairro repositorioBairro;
	
	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView resultado = new ModelAndView("bairros/listar");
		resultado.addObject("bairros", repositorioBairro.findAll());
		return resultado;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView resultado = new ModelAndView("bairros/cadastrar");
		resultado.addObject("bairro", new Bairro());
		return resultado;
	}
	
	@PostMapping("/cadastrar")
	public String cadastrar(@Valid Bairro bairro, BindingResult result) {
		if (result.hasErrors())
			return "bairros/cadastrar";
		repositorioBairro.save(bairro);
		return "redirect:/bairros/listar";
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable Long id) {
		ModelAndView resultado = new ModelAndView("bairros/alterar");
		resultado.addObject("bairro", repositorioBairro.getOne(id));
		return resultado;
	}
	
	@PostMapping("/alterar")
	public String alterar(@Valid Bairro bairro, BindingResult result) {
		if (result.hasErrors())
			return "bairros/alterar";
		repositorioBairro.save(bairro);
		return "redirect:/bairros/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String deletar(@PathVariable Long id) {
		repositorioBairro.deleteById(id);
		return "redirect:/bairros/listar";
	}

}
