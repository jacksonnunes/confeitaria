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
@Table(name = "pgt_formas_pagamento")
public class FormaPagamento {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "pgt_pagamento", length = 15, nullable = false)
	@Length(max = 15, message = "A forma de pagamento deve conter, no máximo, 15 caracteres.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	@NotNull(message = "Preenchimento obrigatório.")
	private String formaPagamento;
	
	@OneToMany(mappedBy = "pagamento")
	private List<Pedido> pedidos;

	public Long getId() {
		return id;
	}

	public String getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(String formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

}
