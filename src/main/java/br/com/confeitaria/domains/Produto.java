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
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "pro_produtos")
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "pro_nome", length = 30, nullable = false)
	@Length(max = 25, message = "O nome deve ter, no máximo, 30 caracteres.")
	@NotEmpty(message = "Preenchimento obrigatório.")
	@NotNull(message = "Preenchimento obrigatório.")
	private String nome;
	
	@Column(name = "pro_descricao", length = 100)
	@Length(max = 100, message = "A descrição deve ter, no máximo, 100 caracteres.")
	private String descricao;
	
	@Column(name = "pro_qtd_disponivel", nullable = false)
	@Min(value = 0, message = "Este campo não pode ser negativo.")
	@NotNull(message = "Preenchimento obrigatório.")
	private int quantidadeDisponivel;
	
	@Column(name = "pro_valor", precision = 2, nullable = false)
	@Min(value = 0, message = "O valor não pode ser negativo")
	@NotNull(message = "Preenchimento obrigatório.")
	private double valor;
	
	@OneToMany(mappedBy = "produto")
	private List<ItemPedido> itemPedido;
	
	@ManyToOne
	@JoinColumn(name = "cat_id")
	private Categoria categoria;
	
	@Column(name = "pro_imagem")
	private String imagem;
	
	public Produto() {
		super();
		this.categoria = new Categoria();
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

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getQuantidadeDisponivel() {
		return quantidadeDisponivel;
	}

	public void setQuantidadeDisponivel(int quantidadeDisponivel) {
		this.quantidadeDisponivel = quantidadeDisponivel;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public List<ItemPedido> getItemPedido() {
		return itemPedido;
	}

	public void setItemPedido(List<ItemPedido> itemPedido) {
		this.itemPedido = itemPedido;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

}
