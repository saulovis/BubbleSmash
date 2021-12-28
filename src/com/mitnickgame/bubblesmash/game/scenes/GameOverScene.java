package com.mitnickgame.bubblesmash.game.scenes;

import static com.mitnickgame.bubblesmash.MainActivity.config;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.jaCriouEditText;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenHeight;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenResolution;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenWidth;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.telaInicial;

import java.util.ArrayList;
import java.util.Date;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;
import org.cocos2d.types.ccColor4B;

import android.content.Context;
import android.os.Vibrator;
import android.text.InputFilter;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
import com.mitnickgame.bubblesmash.classes.Pontuacao;
import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.config.Runner;
import com.mitnickgame.bubblesmash.controller.PontuacaoController;
import com.mitnickgame.bubblesmash.game.control.Button;
import com.mitnickgame.bubblesmash.game.control.ButtonDelegate;
import com.mitnickgame.bubblesmash.screens.ScreenBackground;
import com.mitnickgame.bubblesmash.R;

public class GameOverScene extends CCLayer implements ButtonDelegate{
	private ScreenBackground background;
	private Button playAgain,back;
	private CCColorLayer smoke;
	private CCBitmapFontAtlas textScore, textPontos;
	private int tipoJogo, score, colocacao;
	
	public static final int NORMAL = 0;
	public static final int ENEMY = 1;
	public static final int BABY = 2;
	public static final int ARCADE = 3;
	
	public CCScene scene() {
		CCScene scene = CCScene.node();
		scene.addChild(this);
		return scene;
	}
	
	public GameOverScene(int score, int tipoJogo) {
		telaInicial = false;
		this.tipoJogo = tipoJogo;
		this.score = score;
		Vibrator v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(400);
		//carregando um novo AdView
		/*CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			public void run() {
		        final AdView ad = (AdView) CCDirector.sharedDirector().getActivity().findViewById(5000);
		        AdRequest request = new AdRequest.Builder().build();
		        ad.loadAd(request);
		        ad.setVisibility(View.VISIBLE);
		    }
		});*/
		
		colocacao = verificaPosicaoRanking(score, tipoJogo);
		if(colocacao != 0){
			CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
				@SuppressWarnings("deprecation")
				public void run() {
					if(jaCriouEditText){
						final EditText et = (EditText) CCDirector.sharedDirector().getActivity().findViewById(6000);
				        et.setVisibility(View.VISIBLE);
				        et.setText("");
					}else{
						int Y = Math.round( CCDirector.sharedDirector().getActivity().getWindowManager().getDefaultDisplay().getHeight() * 0.6587f);
						LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, Y);
						
						final EditText text = new EditText(CCDirector.sharedDirector().getActivity());
				        text.setId(6000);
				        text.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.BOTTOM);
				        text.setWidth(LinearLayout.LayoutParams.WRAP_CONTENT);
				        text.setHint("your name here");
				        text.setTextSize(22f);
				        text.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
				        CCDirector.sharedDirector().getActivity().addContentView(text, params);
				        jaCriouEditText = true;
					}
			    }
			});
		}
		
		// background
		background = new ScreenBackground(Assets.BACKGROUND);
		background.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0f, screenHeight() / 2.0f)));
		addChild(background);
		smoke = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 175), screenWidth(), screenHeight());
		addChild(smoke);
		// logo
		if(colocacao == 1){
			if(config.getPlayMusic())
				//TODO quando for criado o ranking online só vai levar o maior ranking de cada celular 
				SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(),	R.raw.new_record, false);
			
			CCSprite title = CCSprite.sprite(Assets.NEW_RECORD);
			title.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() -150 )));
			addChild(title);
		}else{
			CCSprite title = CCSprite.sprite(Assets.GAMEOVER);
			title.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() -150 )));
			addChild(title);
		}
		
		// Enable Touch
		setIsTouchEnabled(true);
		playAgain = new Button(Assets.PLAYAGAIN);
		if(colocacao != 0){
			//imagem em branco para EditText
			CCSprite textBlank = CCSprite.sprite(Assets.TEXT);
			textBlank.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.62f) )));
			addChild(textBlank);
			playAgain.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.75f) )));
		}else{
			playAgain.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.65f) )));
		}
		playAgain.setDelegate(this);
		addChild(playAgain);
		//botão de saida do jogo
		back = new Button(Assets.EXIT);
		if(colocacao != 0){
			back.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.85f) )));
		}else{
			back.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.75f) )));
		}
		
		back.setDelegate(this);
		addChild(back);
		
		//texto "SCORE" e texto da pontuação
		textScore = CCBitmapFontAtlas.bitmapFontAtlas("SCORE","UniSansBold_AlphaNum_50.fnt");
		textScore.setScale((float) 50 / 50);
		textScore.setColor(ccColor3B.ccWHITE);
		textScore.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.42f) )));
		addChild(this.textScore);
		
		textPontos = CCBitmapFontAtlas.bitmapFontAtlas(String.valueOf(score),"UniSansSemiBold_Numbers_240.fnt");
		textPontos.setScale((float) 80 / 120);
		textPontos.setColor(ccColor3B.ccWHITE);
		textPontos.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.5f) )));
		addChild(this.textPontos);
	}

	@Override
	public void buttonClicked(Button sender) {
		Vibrator v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(20);
		
		if(config.getPlayEffects())
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.burst);
		Runner.setGamePaused(false);
		if (sender.equals(playAgain)) {
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f,GameScene.createGame(tipoJogo)));
		}
		if (sender.equals(back)) {
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f,new TitleScene().scene()));
		}
	}
	
	private int verificaPosicaoRanking(int score, int tipoJogo) {
		if(score == 0)
			return 0;
		
		if(tipoJogo == BABY)
			return 0;
		
		int result = 1;
		PontuacaoController pc = new PontuacaoController(CCDirector.sharedDirector().getActivity());
		ArrayList<Pontuacao> pontuacoes = (ArrayList<Pontuacao>) pc.buscar(Pontuacao.TIPO_JOGO+"=?", new String[]{tipoJogo+""}, null, null, Pontuacao.PONTOS+" desc", false);
		for(int i = 0; i < pontuacoes.size(); i++){
			Pontuacao p = pontuacoes.get(i);
			if(score > p.getPontos()){
				return result;
			}else{
				result++;
			}
		}
		//se não entrar na lista dos 5 ele zera o result
		if(result > 5){
			result = 0;
		}
		return result;
	}
	
	private boolean gravaNoRanking(String nome, int score, int tipoJogo) {
		if(nome.equals(""))
			return false;
		
		PontuacaoController pc = new PontuacaoController(CCDirector.sharedDirector().getActivity());
		Pontuacao p = new Pontuacao();
		p.setNome(nome);
		p.setData(new Date());
		p.setPontos(score);
		p.setTipoJogo(tipoJogo);
		String mensagem = pc.cadastrar(p);
		if(mensagem != null){
			return false;
		}
		limpaListaScore(tipoJogo);
		return true;
	}

	private void limpaListaScore(int tipoJogo) {
		ArrayList<Pontuacao> pontuacoes = new ArrayList<Pontuacao>();
		PontuacaoController pc = new PontuacaoController(CCDirector.sharedDirector().getActivity());
		pontuacoes = (ArrayList<Pontuacao>) pc.buscar(Pontuacao.TIPO_JOGO+"=?", new String[]{tipoJogo+""}, null, null, Pontuacao.PONTOS, false);
		for(int i = 0; i < pontuacoes.size()-5; i++){
			Pontuacao p = pontuacoes.get(i);
			pc.remover(p);
		}
	}
	
	@Override
	public void onExit(){
		super.onExit();
		//removendo o AdView
		
		//removendo o AdView e o EditText
		CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			public void run() {
		        /*final AdView ad = (AdView) CCDirector.sharedDirector().getActivity().findViewById(5000);
		        ad.setVisibility(View.INVISIBLE);
		        ad.destroy();*/
		        if(colocacao != 0){
		        	final EditText et = (EditText) CCDirector.sharedDirector().getActivity().findViewById(6000);
		        	gravaNoRanking(et.getText().toString(), score, tipoJogo);
			        et.setVisibility(View.INVISIBLE);
		        }
		    }
		});
	}
}