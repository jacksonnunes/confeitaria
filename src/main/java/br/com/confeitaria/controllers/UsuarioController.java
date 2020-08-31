package br.com.confeitaria.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioEndereco;
import br.com.confeitaria.repositories.RepositorioRole;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	@Autowired
	private RepositorioRole repositorioRole;
	@Autowired
	private RepositorioEndereco repositorioEndereco;
	
	@GetMapping("/sign-in")
	public ModelAndView cadastrar() {
		ModelAndView resultado = new ModelAndView("usuarios/cadastrar");
		resultado.addObject("usuario", new Usuario());
		return resultado;
	}
	
	@PostMapping("/sign-in")
	public String cadastrar(@Valid Usuario usuario, BindingResult result, HttpServletRequest request, HttpServletResponse response) throws Exception, IOException {
		boolean existeEmail = repositorioUsuario.existsByEmail(usuario.getEmail());
		if (existeEmail) {
			result.rejectValue("email", "error.user", "Email já cadastrado.");
		}
		if(result.hasErrors())
			return "usuarios/cadastrar";
		//Criptografando a senha
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		usuario.setSenha(encoder.encode(usuario.getSenha()));
		//inserindo uma role ao usuário
		usuario.setRole(repositorioRole.findByRole("ROLE_USER"));
		repositorioUsuario.save(usuario);		
		return "redirect:/login";
	}
	
	@GetMapping("/dados-pessoais")
	public ModelAndView dadosPessoais() {
		ModelAndView resultado = new ModelAndView("usuarios/dados-pessoais");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		resultado.addObject("usuario", usuario);
		resultado.addObject("endereco", repositorioEndereco.findByUsuarioAndEnderecoDefault(usuario, "default"));
		return resultado;
	}
	
	@GetMapping("/atualizar-dados")
	public ModelAndView atualizarDados() {
		ModelAndView resultado = new ModelAndView("usuarios/dados-pessoais-alterar");
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		Usuario usuario = repositorioUsuario.findByEmail(username);
		
		resultado.addObject("usuario", usuario);
		return resultado;
	}
	
	@PostMapping("/atualizar-dados")
	public String atualizarDados(@Valid Usuario usuario, BindingResult result) {
		if(result.hasErrors())
			return "usuarios/dados-pessoais-alterar";
		
		repositorioUsuario.save(usuario);
		return "redirect:/usuario/dados-pessoais";
	}

}
