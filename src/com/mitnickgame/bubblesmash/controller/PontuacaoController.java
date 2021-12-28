package com.mitnickgame.bubblesmash.controller;

import java.util.List;
import android.content.Context;

import com.mitnickgame.bubblesmash.classes.Pontuacao;
import com.mitnickgame.bubblesmash.dao.PontuacaoDAO;

public class PontuacaoController extends ObjetoController<Pontuacao> {

	public PontuacaoController(Context context){
		super(new PontuacaoDAO(context));
	}
	
	@Override
	public String cadastrar(Pontuacao objeto) {
		if(objeto == null)
			throw new IllegalArgumentException("O objeto de "+Pontuacao.class.getSimpleName()+" não pode ser nulo no parametro do método cadastrar.");
		
		String mensagem = validar(objeto);
		if(mensagem != null)
			return mensagem;
		
		try{
			getMercurioDAO().gravar(objeto, null, null);
			return null;
		}catch(Exception e){
			return "Erro ao gravar "+Pontuacao.class.getSimpleName()+" no banco de dados.";
		}
	}

	@Override
	public String alterar(Pontuacao objeto, String whereClause, String[] whereArgs) {
		if(objeto == null)
			throw new IllegalArgumentException("O objeto de "+Pontuacao.class.getSimpleName()+" não pode ser nulo no parametro do método alterar.");
		
		if(objeto.getId() == null)
			return "O "+Pontuacao.class.getSimpleName()+" que você está tentando alterar não existe no banco de dados.";
		
		String mensagem = validar(objeto);
		if(mensagem != null)
			return mensagem;
		
		try{
			getMercurioDAO().gravar(objeto, whereClause, whereArgs);
			return null;
		}catch(Exception e){
			return "Erro ao alterar "+Pontuacao.class.getSimpleName()+" no banco de dados.";
		}
	}

	@Override
	public String remover(Pontuacao objeto) {
		if(objeto == null)
			throw new IllegalArgumentException("O objeto de "+Pontuacao.class.getSimpleName()+" não pode ser nulo no parametro do método remover.");
		
		try{
			if(!getMercurioDAO().remover(objeto.getId())){
				return "Este dado está vinculado a outra informação no sistema.\n" +
						"Você não pode excluir uma rota vinculada a uma viagem.";
			}
			return null;
		}catch(Exception e){
			return "Erro ao remover "+Pontuacao.class.getSimpleName()+" de id="+objeto.getId()+" no banco de dados.\n" +
					"Verifique se este dado não está vinculado a outra informação no sistema.\n" +
					"Por exemplo, você não pode excluir um cliente que possui pedidos.";
		}
	}

	@Override
	protected String validar(Pontuacao objeto) {
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
	public Pontuacao buscarPorId(long id, boolean carregarDependencias) {
		return getMercurioDAO().buscarPorId(id, carregarDependencias);
	}

	@Override
	public List<Pontuacao> listarTodos(boolean carregarDependencias) {
		return getMercurioDAO().listar(carregarDependencias);
	}

	@Override
	public List<Pontuacao> buscar(String whereClause, String[] whereArgs,
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