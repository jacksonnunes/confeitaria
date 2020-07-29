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
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "itm_itens_pedido")
public class ItemPedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "pro_id")
	private Produto produto;
	
	@Column(name = "itm_quantidade")
	@Min(value = 1, message = "Escolha pelo menos um item.")
	@NotNull(message = "A quantidade deve ser preenchida.")
	private int quantidade;
	
	@ManyToOne
	@JoinColumn(name = "id_ped")
	private Pedido pedido;
	
	public ItemPedido() {
		this.produto = new Produto();
		this.pedido = new Pedido();
	}

	public Long getId() {
		return id;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	public int getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public Pedido getPedido() {
		return pedido;
	}

	public void setPedido(Pedido pedido) {
		this.pedido = pedido;
	}

}
