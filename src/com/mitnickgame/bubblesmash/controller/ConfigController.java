package com.mitnickgame.bubblesmash.controller;

import java.util.List;
import android.content.Context;

import com.mitnickgame.bubblesmash.classes.Config;
import com.mitnickgame.bubblesmash.dao.ConfigDAO;

public class ConfigController extends ObjetoController<Config> {

	public ConfigController(Context context){
		super(new ConfigDAO(context));
	}
	
	@Override
	public String cadastrar(Config objeto) {
		if(objeto == null)
			throw new IllegalArgumentException("O objeto de "+Config.class.getSimpleName()+" n�o pode ser nulo no parametro do m�todo cadastrar.");
		
		String mensagem = validar(objeto);
		if(mensagem != null)
			return mensagem;
		
		try{
			getMercurioDAO().gravar(objeto, null, null);
			return null;
		}catch(Exception e){
			return "Erro ao gravar "+Config.class.getSimpleName()+" no banco de dados.";
		}
	}

	@Override
	public String alterar(Config objeto, String whereClause, String[] whereArgs) {
		if(objeto == null)
			throw new IllegalArgumentException("O objeto de "+Config.class.getSimpleName()+" n�o pode ser nulo no parametro do m�todo alterar.");
		
		if(objeto.getId() == null)
			return "O "+Config.class.getSimpleName()+" que voc� est� tentando alterar n�o existe no banco de dados.";
		
		String mensagem = validar(objeto);
		if(mensagem != null)
			return mensagem;
		
		try{
			getMercurioDAO().gravar(objeto, whereClause, whereArgs);
			return null;
		}catch(Exception e){
			return "Erro ao alterar "+Config.class.getSimpleName()+" no banco de dados.";
		}
	}

	@Override
	public String remover(Config objeto) {
		if(objeto == null)
			throw new IllegalArgumentException("O objeto de "+Config.class.getSimpleName()+" n�o pode ser nulo no parametro do m�todo remover.");
		
		try{
			if(!getMercurioDAO().remover(objeto.getId())){
				return "Este dado est� vinculado a outra informa��o no sistema.\n" +
						"Voc� n�o pode excluir uma rota vinculada a uma viagem.";
			}
			return null;
		}catch(Exception e){
			return "Erro ao remover "+Config.class.getSimpleName()+" de id="+objeto.getId()+" no banco de dados.\n" +
					"Verifique se este dado n�o est� vinculado a outra informa��o no sistema.\n" +
					"Por exemplo, voc� n�o pode excluir um cliente que possui pedidos.";
		}
	}

	@Override
	protected String validar(Config objeto) {
		String mensagem = null;
		String valNaoNulos = objeto.validarNaoNulos();
		String valRegrasNegocio = objeto.validarRegrasNegocio(); 
		if(valNaoNulos != null)
			mensagem = valNaoNulos;
		if(valRegrasNegocio != null)
			mensagem = (valNaoNulos == null?"":valNaoNulos+"\n") +valRegrasNegocio;
		
		return mensagem;
	}

	@Override
	public Config buscarPorId(long id, boolean carregarDependencias) {
		return getMercurioDAO().buscarPorId(id, carregarDependencias);
	}

	@Override
	public List<Config> listarTodos(boolean carregarDependencias) {
		return getMercurioDAO().listar(carregarDependencias);
	}

	@Override
	public List<Config> buscar(String whereClause, String[] whereArgs,
			String groupBy, String having, String orderBy,
			boolean carregarDependencias) {
		return getMercurioDAO().buscar(whereClause, whereArgs, groupBy, having, orderBy, carregarDependencias);
	}
	@Override
	public String removerTodos() {
		try{
			getMercurioDAO().removerTodos();
			return null;
		}catch(Exception e){
			return "Erro ao remover os registros no banco de dados.";
		}
	}
}