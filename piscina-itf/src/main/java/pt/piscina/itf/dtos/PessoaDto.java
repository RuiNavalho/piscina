package pt.piscina.itf.dtos;

import java.util.Date;

public class PessoaDto {
	
	private Long id;
	private String nome;
	private String genero;
	private String nacionalidade;
	private String nif;
	private String rua;
	private String ruaComplemento;
	private String codPostal;
	private String localidade;
	private Date dataNascimento;
	private Byte seguro;
	private String nomePai;
	private String nomeMae;
	
	public PessoaDto() {	
	}

	public PessoaDto(Long id, String nome, String genero, String nacionalidade, String nif, String rua,
			String ruaComplemento, String codPostal, String localidade, Date dataNascimento, Byte seguro,
			String nomePai, String nomeMae) {
		super();
		this.id = id;
		this.nome = nome;
		this.genero = genero;
		this.nacionalidade = nacionalidade;
		this.nif = nif;
		this.rua = rua;
		this.ruaComplemento = ruaComplemento;
		this.codPostal = codPostal;
		this.localidade = localidade;
		this.dataNascimento = dataNascimento;
		this.seguro = seguro;
		this.nomePai = nomePai;
		this.nomeMae = nomeMae;
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

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNif() {
		return nif;
	}

	public void setNif(String nif) {
		this.nif = nif;
	}

	public String getRua() {
		return rua;
	}

	public void setRua(String rua) {
		this.rua = rua;
	}

	public String getRuaComplemento() {
		return ruaComplemento;
	}

	public void setRuaComplemento(String ruaComplemento) {
		this.ruaComplemento = ruaComplemento;
	}

	public String getCodPostal() {
		return codPostal;
	}

	public void setCodPostal(String codPostal) {
		this.codPostal = codPostal;
	}

	public String getLocalidade() {
		return localidade;
	}

	public void setLocalidade(String localidade) {
		this.localidade = localidade;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Byte getSeguro() {
		return seguro;
	}

	public void setSeguro(Byte seguro) {
		this.seguro = seguro;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}


}
