package com.mitnickgame.bubblesmash.game.control;

import static com.mitnickgame.bubblesmash.MainActivity.config;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenHeight;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenResolution;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.transitions.CCFadeTransition;
import org.cocos2d.types.CGPoint;

import android.content.Context;
import android.os.Vibrator;

import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.game.scenes.GameScene;
import com.mitnickgame.bubblesmash.game.scenes.TitleScene;
import com.mitnickgame.bubblesmash.R;


public class TipoJogosButtons extends CCLayer implements ButtonDelegate {

	private Button normalButton;
	private Button enemyButton;
	private Button babyButton;
	private Button arcadeButton;
	private Button backButton;
	
	public static final int NORMAL = 0;
	public static final int ENEMY = 1;
	public static final int BABY = 2;
	public static final int ARCADE = 3;
	
	public TipoJogosButtons() {
		setIsTouchEnabled(true);
		normalButton = new Button(Assets.JOGO_NORMAL);
		enemyButton = new Button(Assets.JOGO_ENEMY);
		babyButton = new Button(Assets.JOGO_BABY);
		arcadeButton = new Button(Assets.JOGO_ARCADE);
		backButton = new Button(Assets.BACK);
		// coloca botões na posição correta
		setButtonspPosition();
		
		addChild(normalButton);
		addChild(enemyButton);
		addChild(babyButton);
		addChild(arcadeButton);
		addChild(backButton);
		normalButton.setDelegate(this);
		enemyButton.setDelegate(this);
		babyButton.setDelegate(this);
		arcadeButton.setDelegate(this);
		backButton.setDelegate(this);
	}
	
	private void setButtonspPosition() {
		normalButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.5f) )));
		arcadeButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.6f) )));
		enemyButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.7f) )));
		babyButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.8f) )));
		backButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.9f) )));
	}

	@Override
	public void buttonClicked(Button sender) {
		//removendo o AdView
		/*CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			public void run() {
		        final AdView ad = (AdView) CCDirector.sharedDirector().getActivity().findViewById(5000);
		        ad.setVisibility(View.INVISIBLE);
		        ad.destroy();
		    }
		});*/
		
		Vibrator v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(20);
		
		if(config.getPlayEffects())
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.burst);
		
		if (sender.equals(normalButton)) {
			SoundEngine.sharedEngine().realesAllSounds();
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f,GameScene.createGame(NORMAL)));
		}
		if (sender.equals(enemyButton)) {
			SoundEngine.sharedEngine().realesAllSounds();
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f,GameScene.createGame(ENEMY)));
		}
		if (sender.equals(babyButton)) {
			SoundEngine.sharedEngine().realesAllSounds();
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f,GameScene.createGame(BABY)));
		}
		if (sender.equals(arcadeButton)) {
			SoundEngine.sharedEngine().realesAllSounds();
			CCDirector.sharedDirector().replaceScene(CCFadeTransition.transition(0.5f,GameScene.createGame(ARCADE)));
		}
		if (sender.equals(backButton)) {
			CCDirector.sharedDirector().replaceScene(new TitleScene().scene());
		}
		
	}
}
