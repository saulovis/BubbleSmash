package com.mitnickgame.bubblesmash.dao;

import java.util.Date;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public abstract class ObjetoDAO<T>{

	protected Context context;
	private DatabaseHelper helper;
	private SQLiteDatabase db;

	public ObjetoDAO(Context context){
		helper = new DatabaseHelper(context);
		this.context = context;
	}

	protected SQLiteDatabase getDb(){
		if(db == null){
			db = helper.getWritableDatabase();
		}
		return db;
	}

	public void close(){
		helper.close();
	}
	
	protected Object get(Cursor cursor, String columnName, Class<?> columnType){
		try{
			if(columnType.equals(Long.class)){
				return cursor.getLong(cursor.getColumnIndex(columnName));
			}else if(columnType.equals(String.class)){
				return cursor.getString(cursor.getColumnIndex(columnName));
			}else if(columnType.equals(Double.class)){
				return cursor.getDouble(cursor.getColumnIndex(columnName));
			}else if(columnType.equals(Byte.class)){
				return cursor.getBlob(cursor.getColumnIndex(columnName));
			}else if(columnType.equals(Float.class)){
				return cursor.getFloat(cursor.getColumnIndex(columnName));
			}else if(columnType.equals(Integer.class)){
				return cursor.getInt(cursor.getColumnIndex(columnName));
			}else if(columnType.equals(Short.class)){
				return cursor.getShort(cursor.getColumnIndex(columnName));
			}else if(columnType.equals(Boolean.class)){
				int bool = cursor.getInt(cursor.getColumnIndex(columnName));
				if(bool <= 0)
					return Boolean.valueOf(false);
				else
					return Boolean.valueOf(true);
			}else if(columnType.equals(Date.class)){
				long data = cursor.getLong(cursor.getColumnIndex(columnName));
				if(data > 0)
					return new Date(data);
				else
					return null;
			}
		}catch(Exception e){
			e.printStackTrace(); //TODO: verificar qual exceção é lançada quando o valor da coluna é null no banco.
		}
		return null;
	}
	
	/**
	 * 
	 * @return Retorna um List com todos os registros da referida tabela.
	 */
	public abstract List<T> listar(boolean carregarDependencias);
	
	/**
	 * 
	 * @param id Chave primária do registro pesquisado.
	 * @return Retorna uma instancia válida do id informado, ou null caso não encontre nenhum registro correspondente
	 */
	public abstract T buscarPorId(Long id,boolean carregarDependencias);
	
	/**
	 * 
	 * @param t
	 * @param whereClause
	 * @param whereArgs
	 * @return
	 */
	public abstract long gravar(T t,String whereClause, String[] whereArgs);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public abstract boolean remover(Long id);
	
	public abstract boolean removerTodos();
	/**
	 * 
	 * @param cursor
	 * @return
	 */
	protected abstract T criar(Cursor cursor, boolean carregarDependencias);
	
	/**
	 * Este método recebe por parâmetro todos os atributos necessários para uma busca mais completa. Para as opções que não desejadas, 
	 * basta informar null. Então, se desejar fazer uma busca sem ordenar, ou fazer agrupamentos, basta passar null para os parametros 
	 * groupBy, having e orderBy
	 * 
	 * @param whereClause
	 * @param whereArgs
	 * @param groupBy
	 * @param having
	 * @param orderBy
	 * @return Retorna um List com o resultado da busca, ou vazio caso não haja correspondencia aos critérios informados.
	 */
	public abstract List<T> buscar(String whereClause, String[] whereArgs, String groupBy, String having, String orderBy, boolean carregarDependencias);
	
}