package com.mitnickgame.bubblesmash.game.scenes;

import static com.mitnickgame.bubblesmash.MainActivity.config;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenHeight;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenResolution;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenWidth;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.telaInicial;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.layers.CCScene;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.game.control.CheckButtons;
import com.mitnickgame.bubblesmash.screens.ScreenBackground;
import com.mitnickgame.bubblesmash.R;

public class OptionsScene extends CCLayer {
	private ScreenBackground background;
	
	public OptionsScene() {
		telaInicial = false;
		if(config.getPlayMusic())
			SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(),	R.raw.abertura, true);
		if(config.getPlayEffects())
			SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.burst);
		//background
		background = new ScreenBackground(Assets.BACKGROUND);
		background.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0f,screenHeight() / 2.0f)));
		addChild(this.background);
		
		//logo
		CCSprite logo = CCSprite.sprite(Assets.LOGO);
		logo.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() -160 )));
		addChild(logo);
		
		//menu com os botões
		CheckButtons checkLayer = new CheckButtons();
		addChild(checkLayer);
		
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
	
	public static CCScene scene() {
		CCScene ccscene = CCScene.node();
		OptionsScene layer = new OptionsScene();
		ccscene.addChild(layer);
		return ccscene;
	}
	
	/*@Override
	public void onExit(){
		super.onExit();
		//carregando um novo AdView
		CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			public void run() {
		        final AdView ad = (AdView) CCDirector.sharedDirector().getActivity().findViewById(5000);
		        AdRequest request = new AdRequest.Builder().build();
		        ad.loadAd(request);
		        ad.setVisibility(View.VISIBLE);
		    }
		});
	}*/
}
