package com.mitnickgame.bubblesmash.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	private static final String BANCO_DADOS = "BubbleSmash";
	private static int VERSAO = 2;
	
	public DatabaseHelper(Context context) {
		super(context, BANCO_DADOS, null, VERSAO);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("create table config (_id integer primary key, " +
				"play_music integer check(play_music = 0 or play_music = 1) default 1, " +
				"play_effects integer check(play_effects = 0 or play_effects = 1) default 1," +
				"data_ultimo_acesso integer);");
		
		db.execSQL("create table pontuacao (_id integer primary key, " +
				"nome text not null, " +
				"data integer not null, " +
				"pontos integer not null," +
				"tipo_jogo integer not null default 0);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		if (oldVersion < 2){
			db.execSQL("alter table config add data_ultimo_acesso integer default 0;");
			db.execSQL("alter table pontuacao add tipo_jogo integer default 0;");
		}
	}
}
