package pt.piscina.ds.entities;
// Generated 17/nov/2018 20:51:16 by Hibernate Tools 5.2.3.Final

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Escalao generated by hbm2java
 */
@Entity
@Table(name = "escalao", catalog = "gestgym")
public class Escalao implements java.io.Serializable {

	private Long id;
	private Atividade atividade;
	private int NOrdem;
	private String descricao;
	private int idadeMin;
	private int idadeMax;
	private Set<Nivel> nivels = new HashSet<Nivel>(0);

	public Escalao() {
	}

	public Escalao(Atividade atividade, int NOrdem, String descricao, int idadeMin, int idadeMax) {
		this.atividade = atividade;
		this.NOrdem = NOrdem;
		this.descricao = descricao;
		this.idadeMin = idadeMin;
		this.idadeMax = idadeMax;
	}

	public Escalao(Atividade atividade, int NOrdem, String descricao, int idadeMin, int idadeMax, Set<Nivel> nivels) {
		this.atividade = atividade;
		this.NOrdem = NOrdem;
		this.descricao = descricao;
		this.idadeMin = idadeMin;
		this.idadeMax = idadeMax;
		this.nivels = nivels;
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
	@JoinColumn(name = "atividade_id", nullable = false)
	public Atividade getAtividade() {
		return this.atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	@Column(name = "n_ordem", nullable = false)
	public int getNOrdem() {
		return this.NOrdem;
	}

	public void setNOrdem(int NOrdem) {
		this.NOrdem = NOrdem;
	}

	@Column(name = "descricao", nullable = false, length = 45)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "idade_min", nullable = false)
	public int getIdadeMin() {
		return this.idadeMin;
	}

	public void setIdadeMin(int idadeMin) {
		this.idadeMin = idadeMin;
	}

	@Column(name = "idade_max", nullable = false)
	public int getIdadeMax() {
		return this.idadeMax;
	}

	public void setIdadeMax(int idadeMax) {
		this.idadeMax = idadeMax;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "escalao")
	public Set<Nivel> getNivels() {
		return this.nivels;
	}

	public void setNivels(Set<Nivel> nivels) {
		this.nivels = nivels;
	}

}
