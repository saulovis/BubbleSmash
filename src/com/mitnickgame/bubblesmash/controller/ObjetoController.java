package com.mitnickgame.bubblesmash.controller;

import java.util.List;

import com.mitnickgame.bubblesmash.dao.ObjetoDAO;

public abstract class ObjetoController<T> {

	private ObjetoDAO<T> objetoDAO;
	
	public ObjetoController(ObjetoDAO<T> mercurioDAO){
		if(mercurioDAO == null)
			throw new IllegalArgumentException("A instancia do DAO não pode ser nula.");
		
		this.objetoDAO = mercurioDAO;
	}
	
	protected ObjetoDAO<T> getMercurioDAO(){
		return objetoDAO;
	}
	
	public void close(){
		objetoDAO.close();
	}
	
	public abstract String cadastrar(T t);
	public abstract String alterar(T t, String whereClause, String[] whereArgs);
	public abstract String remover(T t);
	public abstract String removerTodos();
	public abstract T buscarPorId(long id,boolean carregarDependencias);
	public abstract List<T> listarTodos(boolean carregarDependencias);
	public abstract List<T> buscar(String whereClause, String[] whereArgs, String groupBy, String having, String orderBy,boolean carregarDependencias);
	
	protected abstract String validar(T t);
}