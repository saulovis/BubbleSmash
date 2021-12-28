package com.mitnickgame.bubblesmash.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mitnickgame.bubblesmash.classes.Pontuacao;

public class PontuacaoDAO extends ObjetoDAO<Pontuacao> {

	public PontuacaoDAO(Context context){
		super(context);
	}
	
	@Override
	public List<Pontuacao> listar(boolean carregarDependencias) {
		return buscar(null, null, null, null, null,carregarDependencias);
	}
	
	@Override
	public List<Pontuacao> buscar(String whereClause, String[] whereArgs, String groupBy, String having, String orderBy,boolean carregarDependencias){
		Cursor cursor = getDb().query(Pontuacao.TABELA, Pontuacao.COLUNAS, whereClause, whereArgs, groupBy, having, orderBy);
		List<Pontuacao> rotas = new ArrayList<Pontuacao>();
		while(cursor.moveToNext()){
			Pontuacao config = criar(cursor,carregarDependencias);
			rotas.add(config);
		}
		cursor.close();
		return rotas;
	}

	@Override
	public Pontuacao buscarPorId(Long id,boolean carregarDependencias) {
		Cursor cursor = getDb().query(Pontuacao.TABELA, Pontuacao.COLUNAS, 
				Pontuacao._ID + " = ?",new String[]{ id.toString() }, null, null, null);
		if(cursor.moveToNext()){
			Pontuacao p = criar(cursor,carregarDependencias);
			cursor.close();
			return p;
		}
		return null;
	}

	@Override
	public long gravar(Pontuacao p, String whereClause, String[] whereArgs) {
		ContentValues values = new ContentValues();
		values.put(Pontuacao.NOME, p.getNome());
		values.put(Pontuacao.DATA, p.getData().getTime());
		values.put(Pontuacao.PONTOS, p.getPontos());
		values.put(Pontuacao.TIPO_JOGO, p.getTipoJogo());
		if(p.getId() == null){
			long id = getDb().insert(Pontuacao.TABELA, null, values);
			p.setId(id);
			return id;
		}else
			return getDb().update(Pontuacao.TABELA, values, whereClause, whereArgs);
	}

	@Override
	public boolean remover(Long id) {
		//removendo a selecao
		String whereClause = Pontuacao._ID + " = ?";
		String[] whereArgs = new String[]{id.toString()};
		int removidos = getDb().delete(Pontuacao.TABELA,whereClause, whereArgs);
		return removidos > 0;
	}
	
	@Override
	protected Pontuacao criar(Cursor cursor,boolean carregarDependencias) {
		Date data = new Date(cursor.getLong(cursor.getColumnIndex(Pontuacao.DATA)));
		Pontuacao p = new Pontuacao(
			cursor.getLong(cursor.getColumnIndex(Pontuacao._ID)),
			cursor.getString(cursor.getColumnIndex(Pontuacao.NOME)),
			data,
			cursor.getInt(cursor.getColumnIndex(Pontuacao.PONTOS)),
			cursor.getInt(cursor.getColumnIndex(Pontuacao.TIPO_JOGO)));
		return p;
	}
	
	@Override
	public boolean removerTodos() {
		int removidos = getDb().delete(Pontuacao.TABELA,null, null);
		return removidos > 0;
	}
}