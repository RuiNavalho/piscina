package pt.piscina.ds.entities;
// Generated 17/nov/2018 23:41:51 by Hibernate Tools 5.2.3.Final

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

/**
 * UtenteHorario generated by hbm2java
 */
@Entity
@Table(name = "utente_horario", catalog = "gestgym")
public class UtenteHorario implements java.io.Serializable {

	private Long id;
	private Horario horario;
	private Utente utente;
	private Date dataInicio;
	private Date dataFim;

	public UtenteHorario() {
	}

	public UtenteHorario(Horario horario, Utente utente, Date dataInicio, Date dataFim) {
		this.horario = horario;
		this.utente = utente;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
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
	@JoinColumn(name = "horario_id", nullable = false)
	public Horario getHorario() {
		return this.horario;
	}

	public void setHorario(Horario horario) {
		this.horario = horario;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "utente_id", nullable = false)
	public Utente getUtente() {
		return this.utente;
	}

	public void setUtente(Utente utente) {
		this.utente = utente;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_inicio", nullable = false, length = 10)
	public Date getDataInicio() {
		return this.dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "data_fim", nullable = false, length = 10)
	public Date getDataFim() {
		return this.dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

}
