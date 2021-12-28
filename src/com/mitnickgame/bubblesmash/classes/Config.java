package com.mitnickgame.bubblesmash.classes;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by dantas on 17/07/13.
 */
public class Config implements Serializable{
	/* CONTANTES DO BANCO DE DADOS*/
	public static final String TABELA = "config";
	public static final String _ID = "_id";
	public static final String PLAY_MUSIC = "play_music";
	public static final String PLAY_EFFECTS = "play_effects";
	public static final String DATA_ULTIMO_ACESSO = "data_ultimo_acesso";
	public static final String[] COLUNAS = new String[]{
		_ID, PLAY_MUSIC, PLAY_EFFECTS, DATA_ULTIMO_ACESSO};
	
	/*ATRIBUTOS DA CLASSE*/
	private static final long serialVersionUID = 4993324600410871007L;
    private Long id;
    private Boolean playMusic, playEffects;
    private Date dataUltimoAcesso;
    
	public Config() {
		super();
	}
	
	public Config(Long id, Boolean playMusic, Boolean playEffects, Date dataUltimoAcesso) {
		super();
		this.id = id;
		this.playMusic = playMusic;
		this.playEffects = playEffects;
		this.dataUltimoAcesso = dataUltimoAcesso;
		
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
	
	public Boolean getPlayMusic() {
		return playMusic;
	}
	
	public void setPlayMusic(Boolean playMusic) {
		this.playMusic = playMusic;
	}
	
	public Boolean getPlayEffects() {
		return playEffects;
	}
	
	public void setPlayEffects(Boolean playEffects) {
		this.playEffects = playEffects;
	}
	
	public Date getDataUltimoAcesso() {
		return dataUltimoAcesso;
	}

	public void setDataUltimoAcesso(Date dataUltimoAcesso) {
		this.dataUltimoAcesso = dataUltimoAcesso;
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
		return "Config: playMusic="+playMusic.toString()+" playEffects="+playEffects.toString();
	}
	
	public String validarNaoNulos() {
		//String mensagem = "";
		//if(playMusic == null || playEffects == null){
		//	mensagem += "O nome não pode estar nulo ou vazio.\n";
		//}
		//if(mensagem.equals(""))
			return null;
		//else{
		//	return mensagem.substring(0, mensagem.length()-1); //para tirar ultimo \n
		//}
	}
	
	public String validarRegrasNegocio() {
		return null;
	}
}
