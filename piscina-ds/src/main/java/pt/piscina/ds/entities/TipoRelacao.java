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
 * TipoRelacao generated by hbm2java
 */
@Entity
@Table(name = "tipo_relacao", catalog = "gestgym", uniqueConstraints = @UniqueConstraint(columnNames = "descricao"))
public class TipoRelacao implements java.io.Serializable {

	private Long id;
	private int NOrdem;
	private String descricao;
	private String antonimo;
	private Set<Relacao> relacaos = new HashSet<Relacao>(0);

	public TipoRelacao() {
	}

	public TipoRelacao(int NOrdem, String descricao) {
		this.NOrdem = NOrdem;
		this.descricao = descricao;
	}

	public TipoRelacao(int NOrdem, String descricao, String antonimo, Set<Relacao> relacaos) {
		this.NOrdem = NOrdem;
		this.descricao = descricao;
		this.antonimo = antonimo;
		this.relacaos = relacaos;
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

	@Column(name = "descricao", unique = true, nullable = false, length = 45)
	public String getDescricao() {
		return this.descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Column(name = "antonimo", length = 45)
	public String getAntonimo() {
		return this.antonimo;
	}

	public void setAntonimo(String antonimo) {
		this.antonimo = antonimo;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "tipoRelacao")
	public Set<Relacao> getRelacaos() {
		return this.relacaos;
	}

	public void setRelacaos(Set<Relacao> relacaos) {
		this.relacaos = relacaos;
	}

}
