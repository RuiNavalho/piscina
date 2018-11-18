package pt.piscina.ds.entities;
// Generated 17/nov/2018 23:41:51 by Hibernate Tools 5.2.3.Final

import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * Epoca generated by hbm2java
 */
@Entity
@Table(name = "epoca", catalog = "gestgym", uniqueConstraints = @UniqueConstraint(columnNames = "descricao"))
public class Epoca implements java.io.Serializable {

	private Long id;
	private String descricao;
	private Date inicio;
	private Date fim;
	private Set<AtividadeEpoca> atividadeEpocas = new HashSet<AtividadeEpoca>(0);

	public Epoca() {
	}

	public Epoca(String descricao, Date inicio, Date fim) {
		this.descricao = descricao;
		this.inicio = inicio;
		this.fim = fim;
	}

	public Epoca(String descricao, Date inicio, Date fim, Set<AtividadeEpoca> atividadeEpocas) {
		this.descricao = descricao;
		this.inicio = inicio;
		this.fim = fim;
		this.atividadeEpocas = atividadeEpocas;
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

	@Column(name = "descricao", unique = true, nullable = false, length = 45)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "inicio", nullable = false, length = 10)
	public Date getInicio() {
		return this.inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "fim", nullable = false, length = 10)
	public Date getFim() {
		return this.fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "epoca")
	public Set<AtividadeEpoca> getAtividadeEpocas() {
		return this.atividadeEpocas;
	}

	public void setAtividadeEpocas(Set<AtividadeEpoca> atividadeEpocas) {
		this.atividadeEpocas = atividadeEpocas;
	}

}
