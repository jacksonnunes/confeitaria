package br.com.confeitaria.domains;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
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
	@JoinColumn(name = "id_pro")
	private Produto produto;
	
	@Column(name = "itm_quantidade")
	@Min(value = 1, message = "Escolha pelo menos um item.")
	@NotNull(message = "A quantidade deve ser preenchida.")
	private int quantidade;
	
	@ManyToMany(mappedBy = "itens")
	private List<Pedido> pedido;
	
	@ManyToOne
	@JoinColumn(name = "id_usr")
	private Usuario usuario;
	
	public ItemPedido() {
		this.produto = new Produto();
		this.quantidade = 1;
		this.pedido = new LinkedList<Pedido>();
		this.usuario = new Usuario();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Pedido> getPedido() {
		return pedido;
	}

	public void setPedido(List<Pedido> pedido) {
		this.pedido = pedido;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
