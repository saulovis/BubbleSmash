package com.mitnickgame.bubblesmash.screens;

import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenHeight;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenResolution;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCColorLayer;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.ccColor4B;

import android.content.Context;
import android.os.Vibrator;

import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.game.control.Button;
import com.mitnickgame.bubblesmash.game.control.ButtonDelegate;
import com.mitnickgame.bubblesmash.game.interfaces.PauseDelegate;


public class PauseScreen extends CCLayer implements ButtonDelegate {

	private Button resumeButton;
	private Button quitButton;
	private PauseDelegate delegate;
	private CCColorLayer background;

	public PauseScreen() {
		// Enable Touch
		setIsTouchEnabled(true);
		// Adds background
		background = CCColorLayer.node(ccColor4B.ccc4(0, 0, 0, 175), screenWidth(), screenHeight());
		addChild(this.background);
		// logo
		CCSprite title = CCSprite.sprite(Assets.LOGO);
		title.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() -160 ))) ;
		addChild(title);
		// Add Buttons
		resumeButton = new Button(Assets.RESUME);
		quitButton = new Button(Assets.EXIT);
		resumeButton.setDelegate(this);
		quitButton.setDelegate(this);
		addChild(resumeButton);
		addChild(quitButton);
		// Position Buttons
		resumeButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.6f) )));
		quitButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.7f) )));
	}

	public void setDelegate(PauseDelegate delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public void buttonClicked(Button sender) {
		Vibrator v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(20);
		// Check Resume Button touched
		if (sender == resumeButton) {
			delegate.resumeGame();
			removeFromParentAndCleanup(true);
		}

		// Check Quit Button touched
		if (sender == quitButton) {
			delegate.quitGame();
		}
	}
}
