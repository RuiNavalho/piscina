package pt.piscina.ds.entities;
// Generated 17/nov/2018 20:51:16 by Hibernate Tools 5.2.3.Final

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * DocIdentificacao generated by hbm2java
 */
@Entity
@Table(name = "doc_identificacao", catalog = "gestgym", uniqueConstraints = @UniqueConstraint(columnNames = "descricao"))
public class DocIdentificacao implements java.io.Serializable {

	private Long id;
	private Pessoa pessoa;
	private TipoDocIdentificacao tipoDocIdentificacao;
	private String descricao;
	private Date validade;

	public DocIdentificacao() {
	}

	public DocIdentificacao(Pessoa pessoa, TipoDocIdentificacao tipoDocIdentificacao, String descricao) {
		this.pessoa = pessoa;
		this.tipoDocIdentificacao = tipoDocIdentificacao;
		this.descricao = descricao;
	}

	public DocIdentificacao(Pessoa pessoa, TipoDocIdentificacao tipoDocIdentificacao, String descricao, Date validade) {
		this.pessoa = pessoa;
		this.tipoDocIdentificacao = tipoDocIdentificacao;
		this.descricao = descricao;
		this.validade = validade;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)

	@Column(name = "id", unique = true, nullable = false)
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pessoa_id", nullable = false)
	public Pessoa getPessoa() {
		return this.pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_doc_identificacao_id", nullable = false)
	public TipoDocIdentificacao getTipoDocIdentificacao() {
		return this.tipoDocIdentificacao;
	}

	public void setTipoDocIdentificacao(TipoDocIdentificacao tipoDocIdentificacao) {
		this.tipoDocIdentificacao = tipoDocIdentificacao;
	}

	@Column(name = "descricao", unique = true, nullable = false, length = 100)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "validade", length = 19)
	public Date getValidade() {
		return this.validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

}