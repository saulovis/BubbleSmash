package com.mitnickgame.bubblesmash.game.control;

import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenHeight;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenResolution;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;

import android.content.Context;
import android.os.Vibrator;

import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.game.scenes.GameScene;


public class GameButtons extends CCLayer implements ButtonDelegate {

	private Button pauseButton;

	private GameScene delegate;

	public static GameButtons getGameButtons() {
		return new GameButtons();
	}

	public GameButtons() {
		// habilita o touch na tela
		setIsTouchEnabled(true);
		// cria os botões
		pauseButton = new Button(Assets.PAUSE);
		// configura as delegações
		pauseButton.setDelegate(this);
		// set position
		setButtonspPosition();
		addChild(pauseButton);
	}

	private void setButtonspPosition() {
		pauseButton.setPosition(screenResolution(CGPoint.ccp( 50 , screenHeight() - 50 ))) ;
	}

	@Override
	public void buttonClicked(Button sender) {
		Vibrator v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(20);
		
		if (sender.equals(pauseButton)) {
			delegate.pauseGameAndShowLayer();
		}
	}

	public void setDelegate(GameScene gameScene) {
		delegate = gameScene;
	}

}