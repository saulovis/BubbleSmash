package com.mitnickgame.bubblesmash.classes;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dantas on 17/07/13.
 */
public class Pontuacao implements Serializable{
	/* CONTANTES DO BANCO DE DADOS*/
	public static final String TABELA = "pontuacao";
	public static final String _ID = "_id";
	public static final String NOME = "nome";
	public static final String DATA = "data";
	public static final String PONTOS = "pontos";
	public static final String TIPO_JOGO = "tipo_jogo";
	public static final String[] COLUNAS = new String[]{
		_ID, NOME, DATA, PONTOS, TIPO_JOGO};
	
	/*ATRIBUTOS DA CLASSE*/
	private static final long serialVersionUID = 4993324600410871007L;
    private Long id;
    private String nome;
    private Date data;
    private int pontos, tipoJogo;
    
	public Pontuacao() {
		super();
	}
	
	public Pontuacao(Long id, String nome, Date data, int pontos, int tipoJogo) {
		super();
		this.id = id;
		this.nome = nome;
		this.data = data;
		this.pontos = pontos;
		this.setTipoJogo(tipoJogo);
		
		String mensagem = validarNaoNulos();
		if(mensagem != null){
			throw new IllegalArgumentException(mensagem);
		}
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public int getPontos() {
		return pontos;
	}

	public void setPontos(int pontos) {
		this.pontos = pontos;
	}
	
	public int getTipoJogo() {
		return tipoJogo;
	}

	public void setTipoJogo(int tipoJogo) {
		this.tipoJogo = tipoJogo;
	}

	@Override
	public int hashCode() {
		return 1;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		return true;
	}    
    
	@Override
	public String toString() {
		return "Pontuação: nome="+nome+" pontos="+pontos;
	}
	
	public String validarNaoNulos() {
		String mensagem = "";
		if(data == null){
			mensagem += "A data não pode estar nula.\n";
		}
		if(pontos == 0){
			mensagem += "A pontuação não pode estar zerada.\n";
		}
		if(mensagem.equals(""))
			return null;
		else{
			return mensagem.substring(0, mensagem.length()-1); //para tirar ultimo \n
		}
	}
	
	public String validarRegrasNegocio() {
		return null;
	}
}
