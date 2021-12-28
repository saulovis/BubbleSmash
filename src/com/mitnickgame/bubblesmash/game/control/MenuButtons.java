package com.mitnickgame.bubblesmash.game.control;

import static com.mitnickgame.bubblesmash.MainActivity.config;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenHeight;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenResolution;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import android.content.Context;
import android.os.Vibrator;

import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.game.scenes.HighScoreScene;
import com.mitnickgame.bubblesmash.game.scenes.JogosScene;
import com.mitnickgame.bubblesmash.game.scenes.OptionsScene;
import com.mitnickgame.bubblesmash.R;




public class MenuButtons extends CCLayer implements ButtonDelegate {
	private Button playButton, optionsButton, highScoreButton, exitButton/*, rateButton*/;
	
	public MenuButtons() {
		setIsTouchEnabled(true);
		playButton = new Button(Assets.PLAY);
		optionsButton = new Button(Assets.OPTIONS);
		highScoreButton = new Button(Assets.HIGHSCORE);
		exitButton = new Button(Assets.EXIT);
		//rateButton = new Button(Assets.RATE);
		// coloca botões na posição correta
		setButtonspPosition();
		
		addChild(playButton);
		addChild(optionsButton);
		addChild(highScoreButton);
		addChild(exitButton);
		//addChild(rateButton);
		playButton.setDelegate(this);
		optionsButton.setDelegate(this);
		highScoreButton.setDelegate(this);
		exitButton.setDelegate(this);
		//rateButton.setDelegate(this);
	}
	
	private void setButtonspPosition() {
		playButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.5f) )));
		optionsButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.6f) )));
		highScoreButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.7f) )));
		exitButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.8f) )));
		//rateButton.setPosition(screenResolution(CGPoint.ccp(screenWidth() - 50 , 50 ))) ;
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
		
		if (sender.equals(playButton)) {
			CCDirector.sharedDirector().replaceScene(JogosScene.scene());
		}
		if (sender.equals(optionsButton)) {
			CCDirector.sharedDirector().replaceScene(OptionsScene.scene());
		}
		if (sender.equals(highScoreButton)) {
			CCDirector.sharedDirector().replaceScene(HighScoreScene.scene(0));
		}
		if (sender.equals(exitButton)) {
			CCDirector.sharedDirector().getActivity().finish();
		}
		/*if (sender.equals(rateButton)){
			Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.savismobile.bubblesmash");
	    	Intent it = new Intent(Intent.ACTION_VIEW,uri);
	    	CCDirector.sharedDirector().getActivity().startActivity(it);
		}*/
	}
}
