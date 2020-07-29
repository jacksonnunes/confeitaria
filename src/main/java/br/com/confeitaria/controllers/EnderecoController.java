package br.com.confeitaria.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
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
		
		endereco.setUsuario(usuario);
		repositorioEndereco.save(endereco);
		return "redirect:/";
	}

}
