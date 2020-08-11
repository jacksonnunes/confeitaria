package br.com.confeitaria.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.confeitaria.domains.Endereco;
import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioEndereco;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Controller
@RequestMapping("/endereco")
public class EnderecoController {
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	@Autowired
	private RepositorioEndereco repositorioEndereco;
	
	@GetMapping("/listar")
	public ModelAndView listar() {
		ModelAndView resultado = new ModelAndView("enderecos/listar");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		resultado.addObject("enderecos", repositorioEndereco.findByUsuario(usuario));
		return resultado;
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView cadastrar() {
		ModelAndView resultado = new ModelAndView("enderecos/cadastrar");
		Endereco endereco = new Endereco();
		endereco.setCidade("Quixadá-CE");
		resultado.addObject("endereco", endereco);
		return resultado;
	}
	
	@PostMapping("/cadastrar")
	public String cadastrarEndereco(@Valid Endereco endereco, BindingResult result) {
		if(result.hasErrors())
			return "enderecos/cadastrar";
		
		//Resgatar usuário logado para salvar o endereço
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		//Verificando se o usuário já possui endeços cadastrados, caso contrário, tornará o endereço cadastrado como default
		List<Endereco> enderecos = repositorioEndereco.findByUsuario(usuario);
		if(enderecos.isEmpty())
			endereco.setEnderecoDefault("default");
		else
			endereco.setEnderecoDefault("secundário");
		
		endereco.setUsuario(usuario);
		repositorioEndereco.save(endereco);
		return "redirect:/";
	}
	
	@GetMapping("/alterar/{id}")
	public ModelAndView alterar(@PathVariable Long id) {
		ModelAndView resultado = new ModelAndView("enderecos/alterar");
		resultado.addObject("endereco", repositorioEndereco.getOne(id));
		return resultado;
	}
	
	@PostMapping("/alterar")
	public String alterar(@Valid Endereco endereco, BindingResult result) {
		if(result.hasErrors())
			return "enderecos/alterar";
		repositorioEndereco.save(endereco);
		return "redirect:/endereco/listar";
	}
	
	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable Long id) {
		repositorioEndereco.deleteById(id);
		return "redirect:/endereco/listar";
	}
	
	@PostMapping("/escolherDefault/{id}")
	public String escolherDefault(@PathVariable Long id) {
		Endereco antigoEnderecoDefault = repositorioEndereco.findByEnderecoDefault("default");
		antigoEnderecoDefault.setEnderecoDefault("secundário");
		Endereco novoEnderecoDefault = repositorioEndereco.getOne(id);
		novoEnderecoDefault.setEnderecoDefault("default");
		repositorioEndereco.save(antigoEnderecoDefault);
		repositorioEndereco.save(novoEnderecoDefault);
		return "redirect:/endereco/listar";
	}

}
