package br.com.confeitaria.domains;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "end_enderecos")
public class Endereco {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "end_logradouro", length = 50, nullable = false)
	@Length(max = 50, message = "O logradouro deve conter, no máximo, 30 caracteres")
	@NotEmpty(message = "Preenchimento obrigatório.")
	@NotNull(message = "Preenchimento obrigatório.")
	private String logradouro;
	
	@Column(name = "end_numero", nullable = false)
	@Min(value = 0, message = "O número não pode ser negativo.")
	@NotNull(message = "Preenchimento obrigatório.")
	private int numero;
	
	@Column(name = "end_complemento", length = 10)
	@Length(max = 10, message = "O complemento deve conter, no máximo, 10 caracteres.")
	private String complemento;
	
	@Column(name = "end_bairro", length = 30, nullable = false)
	@Length(max = 30, message = "O bairro deve conter, no máximo, 30 caracteres.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	@NotNull(message = "Preenchimento obrigatório.")
	private String bairro;
	
	@Column(name = "end_cidade", length = 30, nullable = false)
	@Length(max = 30, message = "A cidade deve conter, no máximo, 30 caracteres.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	@NotNull(message = "Preenchimento obrigatório.")
	private String cidade;
	
	@Column(name = "end_cep", length = 9, nullable = false)
	@Length(max = 9, message = "O CEP deve conter 9 caracteres, no formato 00000-000.")
	@NotNull(message = "Preenchimento obrigatório.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	private String cep;
	
	@Column(name = "end_referencia", length = 60)
	@Length(max = 60, message = "A referência deve conter, no máximo, 60 caracteres.")
	private String referencia;
	
	@ManyToOne
	@JoinColumn(name = "usr_id")
	private Usuario usuario;
	
	public Endereco() {
		this.usuario = new Usuario();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
