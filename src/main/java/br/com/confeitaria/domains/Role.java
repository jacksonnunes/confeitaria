package br.com.confeitaria.domains;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "rol_permissoes")
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "rol_role", length = 15, nullable = false)
	@Length(max = 15, message = "A permissão deve ter, no máximo, 15 caracteres.")
	@NotNull(message = "Preenchimento obrigatório.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	private String role;
	
	@OneToMany(mappedBy = "role")
	private List<Usuario> usuario;

	public Long getId() {
		return id;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public List<Usuario> getUsuario() {
		return usuario;
	}

	public void setUsuario(List<Usuario> usuario) {
		this.usuario = usuario;
	}

}
