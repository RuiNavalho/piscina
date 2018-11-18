package pt.piscina.ds.entities;
// Generated 17/nov/2018 23:41:51 by Hibernate Tools 5.2.3.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * TipoDocIdentificacao generated by hbm2java
 */
@Entity
@Table(name = "tipo_doc_identificacao", catalog = "gestgym", uniqueConstraints = @UniqueConstraint(columnNames = "descricao"))
public class TipoDocIdentificacao implements java.io.Serializable {

	private Long id;
	private int NOrdem;
	private String descricao;
	private Set<DocIdentificacao> docIdentificacaos = new HashSet<DocIdentificacao>(0);

	public TipoDocIdentificacao() {
	}

	public TipoDocIdentificacao(int NOrdem, String descricao) {
		this.NOrdem = NOrdem;
		this.descricao = descricao;
	}

	public TipoDocIdentificacao(int NOrdem, String descricao, Set<DocIdentificacao> docIdentificacaos) {
		this.NOrdem = NOrdem;
		this.descricao = descricao;
		this.docIdentificacaos = docIdentificacaos;
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

	@Column(name = "n_ordem", nullable = false)
	public int getNOrdem() {
		return this.NOrdem;
	}

	public void setNOrdem(int NOrdem) {
		this.NOrdem = NOrdem;
	}

	@Column(name = "descricao", unique = true, nullable = false, length = 100)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoDocIdentificacao")
	public Set<DocIdentificacao> getDocIdentificacaos() {
		return this.docIdentificacaos;
	}

	public void setDocIdentificacaos(Set<DocIdentificacao> docIdentificacaos) {
		this.docIdentificacaos = docIdentificacaos;
	}

}
