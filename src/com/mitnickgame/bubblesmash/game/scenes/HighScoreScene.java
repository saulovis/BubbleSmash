package com.mitnickgame.bubblesmash.game.scenes;

import static com.mitnickgame.bubblesmash.MainActivity.config;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenHeight;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenResolution;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenWidth;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.telaInicial;

import java.util.ArrayList;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor3B;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Vibrator;
//import android.view.View;

//import com.google.android.gms.ads.AdRequest;
//import com.google.android.gms.ads.AdView;
import com.mitnickgame.bubblesmash.classes.Pontuacao;
import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.controller.PontuacaoController;
import com.mitnickgame.bubblesmash.game.control.Button;
import com.mitnickgame.bubblesmash.game.control.ButtonDelegate;
import com.mitnickgame.bubblesmash.screens.ScreenBackground;
import com.mitnickgame.bubblesmash.R;

public class HighScoreScene extends CCLayer implements ButtonDelegate{
	private ScreenBackground background;
	private Button back, normalButton,enemyButton,arcadeButton;
	
	public HighScoreScene(int tipoJogo) {
		telaInicial = false;
		initHighScore(tipoJogo);
		
		//carregando um novo AdView
		/*CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			public void run() {
		        final AdView ad = (AdView) CCDirector.sharedDirector().getActivity().findViewById(5000);
		        AdRequest request = new AdRequest.Builder().build();
		        ad.loadAd(request);
		        ad.setVisibility(View.VISIBLE);
		    }
		});*/
	}
	
	private void initHighScore(int tipoJogo) {
		this.removeAllChildren(true);
		//background
		background = new ScreenBackground(Assets.BACKGROUND);
		background.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0f,screenHeight() / 2.0f)));
		addChild(this.background);
		
		//logo
		CCSprite logo = CCSprite.sprite(Assets.LOGO);
		logo.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() -160 )));
		addChild(logo);
		
		//botão voltar
		back = new Button(Assets.BACK);
		back.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.85f) )));
		back.setDelegate(this);
		addChild(back);
		
		//botão ranking normal
		normalButton = new Button(tipoJogo == 0?Assets.RANKING_NORMAL_S:Assets.RANKING_NORMAL);
		normalButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /4 , screenHeight() - (screenHeight() * 0.46f) )));
		normalButton.setDelegate(this);
		addChild(normalButton);
		
		//botão ranking arcade
		arcadeButton = new Button(tipoJogo == 3?Assets.RANKING_ARCADE_S:Assets.RANKING_ARCADE);
		arcadeButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.46f) )));
		arcadeButton.setDelegate(this);
		addChild(arcadeButton);
		
		//botão ranking enemy
		enemyButton = new Button(tipoJogo == 1?Assets.RANKING_ENEMY_S:Assets.RANKING_ENEMY);
		enemyButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 * 1.5f , screenHeight() - (screenHeight() * 0.46f) )));
		enemyButton.setDelegate(this);
		addChild(enemyButton);
		
		alimentaRankingTela(tipoJogo);
	}

	public static CCScene scene(int tipoJogo) {
		CCScene ccscene = CCScene.node();
		HighScoreScene layer = new HighScoreScene(tipoJogo);
		ccscene.addChild(layer);
		return ccscene;
	}

	@Override
	public void buttonClicked(Button sender) {
		Vibrator v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(20);
		if(config.getPlayEffects())
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.burst);
		
		if (sender.equals(back)) {
			CCDirector.sharedDirector().replaceScene(new TitleScene().scene());
		}
		if (sender.equals(normalButton)) {
			initHighScore(0);
		}
		if (sender.equals(enemyButton)) {
			initHighScore(1);
		}
		if (sender.equals(arcadeButton)) {
			initHighScore(3);
		}
	}
	
	@SuppressLint("DefaultLocale")
	public void alimentaRankingTela(int tipoJogo){
		PontuacaoController pc = new PontuacaoController(CCDirector.sharedDirector().getActivity());
		ArrayList<Pontuacao> pontuacoes = (ArrayList<Pontuacao>) pc.buscar(Pontuacao.TIPO_JOGO+"=?", new String[]{tipoJogo+""}, null, null, Pontuacao.PONTOS+" desc,"+Pontuacao._ID, false);
		if(pontuacoes.size() != 0){
			float indice = 0.53f;
			for(Pontuacao p : pontuacoes){
				//texto com o nome
				CCBitmapFontAtlas textNome = CCBitmapFontAtlas.bitmapFontAtlas(p.getNome().toUpperCase(),"UniSansBold_AlphaNum_50.fnt");
				textNome.setScale((float) 50 / 50);
				textNome.setColor(ccColor3B.ccWHITE);
				textNome.setPosition(screenResolution(CGPoint.ccp( screenWidth()/3 , screenHeight() - (screenHeight() * indice) )));
				/*if(!firstTime){
					removeChild(textNome, true);
				}*/
				addChild(textNome);
				
				//texto com a pontuação
				CCBitmapFontAtlas textPontos = CCBitmapFontAtlas.bitmapFontAtlas(p.getPontos()+"","UniSansBold_AlphaNum_50.fnt");
				textPontos.setScale((float) 50 / 50);
				textPontos.setColor(ccColor3B.ccWHITE);
				textPontos.setPosition(screenResolution(CGPoint.ccp(screenWidth() - 120 , screenHeight() - (screenHeight() * indice) )));
				/*if(!firstTime){
					removeChild(textPontos, true);
				}*/
				addChild(textPontos);
				indice += 0.06f;
			}
		}
	}
	
	@Override
	public void onExit(){
		super.onExit();
		//removendo o AdView
		/*CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			public void run() {
				final AdView ad = (AdView) CCDirector.sharedDirector().getActivity().findViewById(5000);
		        ad.setVisibility(View.INVISIBLE);
		        ad.destroy();
		    }
		});*/
	}
}
