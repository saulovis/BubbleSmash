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
import com.mitnickgame.bubblesmash.controller.ConfigController;
import com.mitnickgame.bubblesmash.game.scenes.TitleScene;
import com.mitnickgame.bubblesmash.R;


public class CheckButtons extends CCLayer implements CheckDelegate, ButtonDelegate {
	private Check musicCheck;
	private Check effectsCheck;
	private Button backButton;
	ConfigController cc;
	
	public CheckButtons() {
		setIsTouchEnabled(true);
		cc = new ConfigController(CCDirector.sharedDirector().getActivity());
		//musicas
		if(config.getPlayMusic()){
			musicCheck = new Check(Assets.OP_MUSIC_ON);
		}else{
			musicCheck = new Check(Assets.OP_MUSIC_OFF);
		}
		//efeitos
		if(config.getPlayEffects()){
			effectsCheck = new Check(Assets.OP_EFFECTS_ON);
		}else{
			effectsCheck = new Check(Assets.OP_EFFECTS_OFF);
		}
		backButton = new Button(Assets.BACK);
		// coloca botões na posição correta
		setCheckPosition();
		
		addChild(musicCheck);
		addChild(effectsCheck);
		addChild(backButton);
		musicCheck.setDelegate(this);
		effectsCheck.setDelegate(this);
		backButton.setDelegate(this);
	}
	
	private void setCheckPosition() {
		musicCheck.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.5f) )));
		effectsCheck.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.6f) )));
		backButton.setPosition(screenResolution(CGPoint.ccp( screenWidth() /2 , screenHeight() - (screenHeight() * 0.8f) )));
	}

	@Override
	public void checkClicked(Check sender) {
		Vibrator v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(20);
		
		if (sender.equals(musicCheck)) {
			removeChild(musicCheck, true);
			if(config.getPlayMusic()){
				config.setPlayMusic(false);
				musicCheck = new Check(Assets.OP_MUSIC_OFF);
				SoundEngine.sharedEngine().pauseSound();
			}else{
				config.setPlayMusic(true);
				musicCheck = new Check(Assets.OP_MUSIC_ON);
				SoundEngine.sharedEngine().resumeSound();
			}
			addChild(musicCheck);
			musicCheck.setDelegate(this);
		}
		
		if (sender.equals(effectsCheck)) {
			removeChild(effectsCheck, true);
			if(config.getPlayEffects()){
				config.setPlayEffects(false);
				effectsCheck = new Check(Assets.OP_EFFECTS_OFF);
			}else{
				config.setPlayEffects(true);
				effectsCheck = new Check(Assets.OP_EFFECTS_ON);
			}
			addChild(effectsCheck);
			effectsCheck.setDelegate(this);
		}
		if(config.getPlayEffects())
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.burst);
		
		setCheckPosition();
		String mensagem = cc.cadastrar(config);
		if(mensagem != null){
			System.out.print(mensagem);
		}
	}
	
	@Override
	public void buttonClicked(Button sender) {
		Vibrator v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(20);
		
		if (sender.equals(backButton)) {
			if(config.getPlayEffects())
				SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.burst);
			CCDirector.sharedDirector().replaceScene(new TitleScene().scene());
		}
	}
}
