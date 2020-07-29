package br.com.confeitaria.domains;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "ent_enderecos_entrega")
public class EnderecoEntrega {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ent_logradouro", length = 30, nullable = false)
	@Length(max = 30, message = "O logradouro deve conter, no máximo, 30 caracteres")
	@NotEmpty(message = "Preenchimento obrigatório.")
	@NotNull(message = "Preenchimento obrigatório.")
	private String logradouro;
	
	@Column(name = "ent_numero", nullable = false)
	@Min(value = 0, message = "O número não pode ser negativo.")
	@NotNull(message = "Preenchimento obrigatório.")
	private int numero;
	
	@Column(name = "ent_complemento", length = 10)
	@Length(max = 10, message = "O complemento deve conter, no máximo, 10 caracteres.")
	private String complemento;
	
	@Column(name = "ent_bairro", length = 30, nullable = false)
	@Length(max = 30, message = "O bairro deve conter, no máximo, 30 caracteres.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	@NotNull(message = "Preenchimento obrigatório.")
	private String bairro;
	
	@Column(name = "ent_cidade", length = 30, nullable = false)
	@Length(max = 30, message = "A cidade deve conter, no máximo, 30 caracteres.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	@NotNull(message = "Preenchimento obrigatório.")
	private String cidade;
	
	@Column(name = "ent_cep", nullable = false)
	@NotNull(message = "Preenchimento obrigatório.")
	private int cep;
	
	@Column(name = "ent_referencia", length = 60)
	@Length(max = 60, message = "A referência deve conter, no máximo, 60 caracteres.")
	private String referencia;
	
	@OneToMany
	@JoinColumn(name = "endereco")
	private List<Pedido> pedidos;

	public Long getId() {
		return id;
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

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

}
