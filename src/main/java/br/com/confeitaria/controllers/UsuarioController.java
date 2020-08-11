package br.com.confeitaria.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioRole;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	@Autowired
	private RepositorioUsuario repositorioUsuario;
	@Autowired
	private RepositorioRole repositorioRole;
	
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

}
