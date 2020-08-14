package br.com.confeitaria.domains;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "ped_pedidos")
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "ped_possui_item",
	joinColumns = @JoinColumn(name = "pedido_id"),
	inverseJoinColumns = @JoinColumn(name = "item_id"))
	private List<ItemPedido> item;
	
	@Column(name = "ped_data_pedido")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataPedido;
	
	@Column(name = "ped_data_entrega")
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date dataEntrega;
	
	@ManyToOne
	@JoinColumn(name = "id_ent")
	private FormaDeEntrega entrega;
	
	@ManyToOne
	@JoinColumn(name = "id_pgt")
	private FormaPagamento pagamento;
	
	@ManyToOne
	@JoinColumn(name = "id_end")
	private Endereco endereco;
	
	@Column(name = "ped_valor_total", precision = 2, nullable = false)
	@Min(value = 0, message = "O valor do pedido não pode ser negativo.")
	private double valorTotal;
	
	@Column(name = "ped_status")
	private String status;
	
	@ManyToOne
	@JoinColumn(name = "id_usr")
	private Usuario usuario;
	
	public Pedido() {
		this.item = new LinkedList<ItemPedido>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public List<ItemPedido> getItem() {
		return item;
	}

	public void setItem(List<ItemPedido> item) {
		this.item = item;
	}

	public Date getDataPedido() {
		return dataPedido;
	}

	public void setDataPedido(Date dataPedido) {
		this.dataPedido = dataPedido;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public FormaDeEntrega getEntrega() {
		return entrega;
	}

	public void setEntrega(FormaDeEntrega entrega) {
		this.entrega = entrega;
	}

	public FormaPagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(FormaPagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public double getValorTotal() {
		return valorTotal;
	}

	public void setValorTotal(double valorTotal) {
		this.valorTotal = valorTotal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void addItem(ItemPedido item) {
		this.item.add(item);
	}

}
