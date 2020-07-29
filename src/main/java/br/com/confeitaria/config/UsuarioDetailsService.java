package br.com.confeitaria.config;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.confeitaria.domains.Usuario;
import br.com.confeitaria.repositories.RepositorioUsuario;

@Service
public class UsuarioDetailsService implements UserDetailsService {

	@Autowired
	private RepositorioUsuario repositorioUsuario;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario usuario = repositorioUsuario.findByEmail(username);
		if(usuario == null)
			throw new UsernameNotFoundException("Usuário não encontrado.");
		
		Set<GrantedAuthority> profiles = new HashSet<GrantedAuthority>();
		profiles.add(new SimpleGrantedAuthority(usuario.getRole().getRole()));
		return new User(usuario.getEmail(), usuario.getSenha(), profiles);
	}

}
