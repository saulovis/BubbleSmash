package com.mitnickgame.bubblesmash.game.scenes;

import static com.mitnickgame.bubblesmash.MainActivity.config;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.*;



import java.util.ArrayList;
import java.util.Random;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;

import org.cocos2d.sound.SoundEngine;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.config.Runner;
import com.mitnickgame.bubblesmash.game.control.GameButtons;
import com.mitnickgame.bubblesmash.game.engines.BubblesEngine;
import com.mitnickgame.bubblesmash.game.interfaces.BubblesEngineDelegate;
import com.mitnickgame.bubblesmash.game.interfaces.PauseDelegate;
import com.mitnickgame.bubblesmash.game.objects.Bubble;
import com.mitnickgame.bubblesmash.game.objects.Score;
import com.mitnickgame.bubblesmash.screens.PauseScreen;
import com.mitnickgame.bubblesmash.screens.ScreenBackground;
import com.mitnickgame.bubblesmash.R;

public class GameScene extends CCLayer implements BubblesEngineDelegate, PauseDelegate {
	//tela de fundo
	private ScreenBackground background;
	// bolhas
	private BubblesEngine bubblesEngine;
	//camada das bolhas
	private CCLayer bubblesLayer;
	//ArrayList contendo todas as bolhas
	private ArrayList<Bubble> bubblesArray;

	//pontuação
	private CCLayer scoreLayer;
	private Score score;
	// tela de pause
	private PauseScreen pauseScreen;
	private CCLayer layerPause;
	
	private int tipoJogo;
	
	public static final int J_NORMAL = 0;
	public static final int J_ENEMY = 1;
	public static final int J_BABY = 2;
	public static final int J_ARCADE = 3;
	
	public static final int B_NORMAL = 0;
	public static final int B_ENEMY = 1;
	public static final int B_FAST = 2;
	public static final int B_SLOW = 3;
	public static final int B_LIFE = 4;
	public static final int B_RANDOM = 5;
	
	private GameScene(int tipo) {
		telaInicial = false;
		//carregando a config
		tipoJogo = tipo;
		if(config.getPlayMusic())
			SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(), R.raw.game, true);
		
		// background
		background = new ScreenBackground(Assets.BACKGROUND);
		background.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0f, screenHeight() / 2.0f)));
		// meteoros inimigos
		addChild(background);
		bubblesLayer = CCLayer.node();
		addChild(bubblesLayer);
		//pontuação
		scoreLayer = CCLayer.node();
		addChild(scoreLayer);
		//tela de pause
		layerPause = CCLayer.node();
		addChild(layerPause);
		//botões do jogo
		GameButtons gameButtons = GameButtons.getGameButtons();
		gameButtons.setDelegate(this);
		addChild(gameButtons);
		
		setIsTouchEnabled(true);
		addGameObjects(tipo);
		if(config.getPlayEffects()){
			preloadSounds();
		}
	}

	public static CCScene createGame(int tipo) {
		CCScene scene = CCScene.node();
		GameScene layer = new GameScene(tipo);
		scene.addChild(layer);
		return scene;
	}
	
	private void addGameObjects(int tipo) {
		//bolhas caindo
		bubblesArray = new ArrayList<Bubble>();
		if(tipoJogo == J_BABY){
			bubblesEngine = new BubblesEngine(2f, tipoJogo);
		}else{
			bubblesEngine = new BubblesEngine(7f, tipoJogo);
		}
		//pontuação
		score = new Score(tipoJogo == J_ARCADE);
		score.setDelegate(this);
		scoreLayer.addChild(score);
	}
	
	@Override
	public void createBubble(Bubble bubble) {
		if(bubblesArray.size() == 0){
			bubble.setDelegate(this);
			bubble.setScene(this);
			bubblesLayer.addChild(bubble);
			bubble.start();
			bubblesArray.add(bubble);
		}else{
			if(bubblesArray.get(bubblesArray.size()-1).getY() <= screenHeight() - 180){
				bubble.setDelegate(this);
				bubble.setScene(this);
				bubblesLayer.addChild(bubble);
				bubble.start();
				bubblesArray.add(bubble);
			}
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void bubbleClicked(Bubble clicada) {
		Boolean bursted = false;
		int i = 0;
		while(!bursted && i < bubblesArray.size()){
			Bubble bolha = bubblesArray.get(i);
			if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
				if(clicada.equals(bolha)){
					if(bolha.getTipoBolha() == B_RANDOM){
						bolha.setTipoBolha(new Random().nextInt(4) + 1);
					}
					
					if(bolha.getTipoBolha() == B_ENEMY){
						lifeDown();
					}
					if(bolha.getTipoBolha() == B_FAST){
						bubblesEngine.aumentaVelocidade(2f);
					}
					if(bolha.getTipoBolha() == B_SLOW){
						bubblesEngine.diminuiVelocidade(2f);
					}
					if(bolha.getTipoBolha() == B_LIFE){
						score.lifeUp();
					}
					
					if(tipoJogo == J_ARCADE){
						bolha.bursted(score.getLife()<0);//se vida for menor que zero é porque gastou a ultima vida
					}else{
						bolha.bursted(true);
					}
					score.increase();
					bursted = true;
					
					if(tipoJogo != J_BABY){
						bubblesEngine.aumentaVelocidade(0.06f);
					}
				}
			}
			i++;
		}
	}
	
	public int getLife(){
		if(tipoJogo == J_ARCADE){
			return score.getLife();
		}else{
			return -1;
		}
	}
	
	public void lifeDown(){
		if(tipoJogo == J_ARCADE){
			score.lifeDown();
		}else{
			
		}
	}
	
	@Override
	public void removeBubble(Bubble bubble) {
		bubblesArray.remove(bubble);
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void onEnter() {
		super.onEnter();
		Runner.check().setGamePlaying(true);
		Runner.check().setGamePaused(false);
		// pause
		SoundEngine.sharedEngine().setEffectsVolume(1f);
		SoundEngine.sharedEngine().setSoundVolume(1f);
		this.startEngines();
	}

	private void startEngines() {
		addChild(bubblesEngine);
		bubblesEngine.setDelegate(this);
	}
	
	
	//pre-carregando os sons para eles não serem carregados com delay na primeira vez que forem chamados
	private void preloadSounds() {
		SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.burst);
		SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.slow);
		SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.fast);
		SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.life);
		SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.over);
	}
	
	public void gameOverScreen() {
		Runner.setGamePaused(true);
		SoundEngine.sharedEngine().realesAllSounds();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f,new GameOverScene(score.getScore(),tipoJogo).scene()));
	}
	
	@SuppressWarnings("static-access")
	private void pauseGame() {
		if (!Runner.check().isGamePaused() && Runner.check().isGamePlaying()) {
			Runner.setGamePaused(true);
			if(config.getPlayMusic())
				SoundEngine.sharedEngine().pauseSound();
		}
	}

	@SuppressWarnings("static-access")
	@Override
	public void resumeGame() {
		if (Runner.check().isGamePaused() || !Runner.check().isGamePlaying()) {
			// Continua o jogo
			pauseScreen = null;
			Runner.setGamePaused(false);
			setIsTouchEnabled(true);
			if(config.getPlayMusic())
				SoundEngine.sharedEngine().resumeSound();
		}
	}

	@Override
	public void quitGame() {
		SoundEngine.sharedEngine().realesAllSounds();
		CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f,new TitleScene().scene()));
	}

	@SuppressWarnings("static-access")
	@Override
	public void pauseGameAndShowLayer() {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			pauseGame();
		}
		if (Runner.check().isGamePaused() && Runner.check().isGamePlaying() && pauseScreen == null) {
			pauseScreen = new PauseScreen();
			layerPause.addChild(pauseScreen);
			pauseScreen.setDelegate(this);
		}
	}
	
	@Override
	public void onExit() {
		//TODO testar saporra qdo liga pro celular e parar os sons
		SoundEngine.sharedEngine().realesAllSounds();
	}
}