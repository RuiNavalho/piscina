package pt.piscina.ds.entities;
// Generated 17/nov/2018 20:51:16 by Hibernate Tools 5.2.3.Final

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * RelacaoId generated by hbm2java
 */
@Embeddable
public class RelacaoId implements java.io.Serializable {

	private long pessoaId1;
	private long pessoaId2;
	private long tipoRelacaoId;

	public RelacaoId() {
	}

	public RelacaoId(long pessoaId1, long pessoaId2, long tipoRelacaoId) {
		this.pessoaId1 = pessoaId1;
		this.pessoaId2 = pessoaId2;
		this.tipoRelacaoId = tipoRelacaoId;
	}

	@Column(name = "pessoa_id_1", nullable = false)
	public long getPessoaId1() {
		return this.pessoaId1;
	}

	public void setPessoaId1(long pessoaId1) {
		this.pessoaId1 = pessoaId1;
	}

	@Column(name = "pessoa_id_2", nullable = false)
	public long getPessoaId2() {
		return this.pessoaId2;
	}

	public void setPessoaId2(long pessoaId2) {
		this.pessoaId2 = pessoaId2;
	}

	@Column(name = "tipo_relacao_id", nullable = false)
	public long getTipoRelacaoId() {
		return this.tipoRelacaoId;
	}

	public void setTipoRelacaoId(long tipoRelacaoId) {
		this.tipoRelacaoId = tipoRelacaoId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof RelacaoId))
			return false;
		RelacaoId castOther = (RelacaoId) other;

		return (this.getPessoaId1() == castOther.getPessoaId1()) && (this.getPessoaId2() == castOther.getPessoaId2())
				&& (this.getTipoRelacaoId() == castOther.getTipoRelacaoId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getPessoaId1();
		result = 37 * result + (int) this.getPessoaId2();
		result = 37 * result + (int) this.getTipoRelacaoId();
		return result;
	}

}