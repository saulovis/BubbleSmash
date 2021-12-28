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
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;

//import android.view.View;

//import com.google.android.gms.ads.AdView;
import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.game.control.MenuButtons;
import com.mitnickgame.bubblesmash.game.engines.SmallBubblesEngine;
import com.mitnickgame.bubblesmash.game.interfaces.SmallBubblesEngineDelegate;
import com.mitnickgame.bubblesmash.game.objects.SmallBubble;
import com.mitnickgame.bubblesmash.screens.ScreenBackground;
import com.mitnickgame.bubblesmash.R;



public class TitleScene extends CCLayer implements SmallBubblesEngineDelegate{
	private ScreenBackground background;
	private CCLayer smallBubblesLayer;
	private SmallBubblesEngine smallBubblesEngine;
	private ArrayList<SmallBubble> smallBubblesArray;
	//public static AdView ad;
	
	public TitleScene() {
		telaInicial = true;
		//carregando a config
		if(config.getPlayMusic())
			SoundEngine.sharedEngine().playSound(CCDirector.sharedDirector().getActivity(),	R.raw.abertura, true);
		if(config.getPlayEffects())
			SoundEngine.sharedEngine().preloadEffect(CCDirector.sharedDirector().getActivity(), R.raw.burst);
		//background
		background = new ScreenBackground(Assets.BACKGROUND);
		background.setPosition(screenResolution(CGPoint.ccp(screenWidth() / 2.0f,screenHeight() / 2.0f)));
		addChild(this.background);
		
		//layer e array das bolhas
		smallBubblesLayer = CCLayer.node();
		addChild(smallBubblesLayer);
		
		//instanciando a engine e o ArrayList
		smallBubblesArray = new ArrayList<SmallBubble>();
		
		smallBubblesEngine = new SmallBubblesEngine();
		addChild(smallBubblesEngine);
		smallBubblesEngine.setDelegate(this);
		
		//logo
		CCSprite logo = CCSprite.sprite(Assets.LOGO);
		logo.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() -160 )));
		addChild(logo);
		
		//menu com os botões
		MenuButtons menuLayer = new MenuButtons();
		addChild(menuLayer);
		
		//carregando um novo AdView
		/*CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			public void run() {
		        final AdView ad = (AdView) CCDirector.sharedDirector().getActivity().findViewById(5000);
		        AdRequest request = new AdRequest.Builder().build();
		        ad.loadAd(request);
		        ad.setVisibility(View.VISIBLE);
		    }
		});*/
		/*CCDirector.sharedDirector().getActivity().runOnUiThread(new Runnable() {
			public void run() {
		        final AdView ad = (AdView) CCDirector.sharedDirector().getActivity().findViewById(5000);
		        ad.setVisibility(View.INVISIBLE);
		        ad.destroy();
		    }
		});*/
	}
	
	public CCScene scene() {
		CCScene ccscene = CCScene.node();
		ccscene.addChild(this);
		schedule("checkSmallBubbles", 1.0f / 10f);
		return ccscene;
	}

	public void checkSmallBubbles(float dt){
		Boolean bursted = false;
		int i = 0;
		while(!bursted && i < smallBubblesArray.size()){
			SmallBubble bolha = smallBubblesArray.get(i);
			if(bolha.getY() > screenHeight() || bolha.getX() > screenWidth() || bolha.getX() < 0){
				bolha.bursted();
				bursted = true;
			}
			i++;
		}
	}
	
	@Override
	public void createSmallBubble(SmallBubble sb) {
		sb.setDelegate(this);
		sb.setScene(this);
		smallBubblesLayer.addChild(sb);
		sb.start();
		smallBubblesArray.add(sb);
	}

	@Override
	public void removeSmallBubble(SmallBubble sb) {
		smallBubblesArray.remove(sb);
	}
}
