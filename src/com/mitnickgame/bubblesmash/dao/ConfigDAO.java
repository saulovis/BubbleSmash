package com.mitnickgame.bubblesmash.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.mitnickgame.bubblesmash.classes.Config;

public class ConfigDAO extends ObjetoDAO<Config> {

	public ConfigDAO(Context context){
		super(context);
	}
	
	@Override
	public List<Config> listar(boolean carregarDependencias) {
		return buscar(null, null, null, null, null,carregarDependencias);
	}
	
	@Override
	public List<Config> buscar(String whereClause, String[] whereArgs, String groupBy, String having, String orderBy,boolean carregarDependencias){
		Cursor cursor = getDb().query(Config.TABELA, Config.COLUNAS, whereClause, whereArgs, groupBy, having, orderBy);
		List<Config> rotas = new ArrayList<Config>();
		while(cursor.moveToNext()){
			Config config = criar(cursor,carregarDependencias);
			rotas.add(config);
		}
		cursor.close();
		return rotas;
	}

	@Override
	public Config buscarPorId(Long id,boolean carregarDependencias) {
		Cursor cursor = getDb().query(Config.TABELA, Config.COLUNAS, 
				Config._ID + " = ?",new String[]{ id.toString() }, null, null, null);
		if(cursor.moveToNext()){
			Config config = criar(cursor,carregarDependencias);
			cursor.close();
			return config;
		}
		return null;
	}

	@Override
	public long gravar(Config config, String whereClause, String[] whereArgs) {
		ContentValues values = new ContentValues();
		values.put(Config.PLAY_MUSIC, config.getPlayMusic());
		values.put(Config.PLAY_EFFECTS, config.getPlayEffects());
		values.put(Config.DATA_ULTIMO_ACESSO, config.getDataUltimoAcesso().getTime());
		if(config.getId() == null){
			long id = getDb().insert(Config.TABELA, null, values);
			config.setId(id);
			return id;
		}else
			return getDb().update(Config.TABELA, values, whereClause, whereArgs);
	}

	@Override
	public boolean remover(Long id) {
		//removendo a selecao
		String whereClause = Config._ID + " = ?";
		String[] whereArgs = new String[]{id.toString()};
		int removidos = getDb().delete(Config.TABELA,whereClause, whereArgs);
		return removidos > 0;
	}
	
	@Override
	protected Config criar(Cursor cursor,boolean carregarDependencias) {
		Config c = new Config();
		c.setId(cursor.getLong(cursor.getColumnIndex(Config._ID)));
		c.setPlayMusic((Boolean)super.get(cursor,Config.PLAY_MUSIC, Boolean.class));
		c.setPlayEffects((Boolean)super.get(cursor,Config.PLAY_EFFECTS, Boolean.class));
		c.setDataUltimoAcesso(new Date(cursor.getLong(cursor.getColumnIndex(Config.DATA_ULTIMO_ACESSO))));
		return c;
	}
	
	@Override
	public boolean removerTodos() {
		int removidos = getDb().delete(Config.TABELA,null, null);
		return removidos > 0;
	}
}