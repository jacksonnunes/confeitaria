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
@Table(name = "sta_status_pedidos")
public class StatusPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "sta_status", length = 20, nullable = false)
	@Length(max = 20, message = "O status deve conter, no máximo, 20 caracteres.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	@NotNull(message = "Preenchimento obrigatório.")
	private String status;
	
	@OneToMany(mappedBy = "status")
	private List<Pedido> pedidos;

	public Long getId() {
		return id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

}
