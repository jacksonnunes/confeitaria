package br.com.confeitaria.domains;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "usr_usuarios")
public class Usuario {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "usr_nome", length = 20, nullable = false)
	@Length(max = 20, message = "O nome deve conter, no máximo, 20 caracteres.")
	@NotNull(message = "Preenchimento obrigatório.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	private String nome;
	
	@Column(name = "usr_sobrenome", length = 30, nullable = false)
	@Length(max = 30, message = "O sobrenome deve conter, no máximo, 30 caracteres.")
	@NotNull(message = "Preenchimento obrigatório.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	private String sobrenome;
	
	@Column(name = "usr_apelido", length = 20)
	@Length(max = 20, message = "Este campo deve conter, no máximo, 20 caracteres.")
	private String apelido;
	
	@Column(name = "usr_email", length = 60, nullable = false)
	@Email(message = "Digite um email válido.")
	@NotNull(message = "Preenchimento obrigatório.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	private String email;
	
	@Column(name = "usr_senha", length = 150, nullable = false)
	@NotNull(message = "Escolha uma senha.")
	@NotEmpty(message = "Escolha uma senha.")
	private String senha;
	
	@OneToMany(mappedBy = "usuario")
	private List<Endereco> enderecos;
	
	@Column(name = "usr_telefone", length = 14, nullable = false)
	@Length(max = 14, message = "O número de telefone deve conter, no máximo, 14 caracteres.")
	@NotNull(message = "Digite um número de telefone.")
	@NotEmpty(message = "Digite um número de telefone.")
	private String telefone;
	
	@ManyToOne
	@JoinColumn(name = "rol_id")
	private Role role;
	
	@OneToMany(mappedBy = "usuario")
	private List<Pedido> pedidos;
	
	@OneToMany(mappedBy = "usuario")
	private List<ItemPedido> itemPedidos;
	
	public Usuario() {
		this.role = new Role();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSobrenome() {
		return sobrenome;
	}

	public void setSobrenome(String sobrenome) {
		this.sobrenome = sobrenome;
	}

	public String getApelido() {
		return apelido;
	}

	public void setApelido(String apelido) {
		this.apelido = apelido;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public List<ItemPedido> getItemPedidos() {
		return itemPedidos;
	}

	public void setItemPedidos(List<ItemPedido> itemPedidos) {
		this.itemPedidos = itemPedidos;
	}

}
