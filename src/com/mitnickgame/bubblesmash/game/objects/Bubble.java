package com.mitnickgame.bubblesmash.game.objects;

import java.util.Random;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.sound.SoundEngine;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.config.Runner;
import com.mitnickgame.bubblesmash.game.interfaces.BubblesEngineDelegate;
import com.mitnickgame.bubblesmash.game.scenes.GameScene;
import com.mitnickgame.bubblesmash.R;

import android.content.Context;
import android.os.Vibrator;
import android.view.MotionEvent;
import static com.mitnickgame.bubblesmash.MainActivity.config;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenHeight;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenResolution;

public class Bubble extends CCLayer {
	private float x, y, v;
	private CCSprite imagem;
	private BubblesEngineDelegate delegate;
	private GameScene scene;
	private boolean fazBarulho;
	private int tipoBolha;
	
	public static final int B_NORMAL = 0;
	public static final int B_ENEMY = 1;
	public static final int B_FAST = 2;
	public static final int B_SLOW = 3;
	public static final int B_LIFE = 4;
	public static final int B_RANDOM = 5;
	
	public Bubble(float velocidade, boolean fazBarulho, int tipoBolha) {
		setIsTouchEnabled(true);
		//carregando a config
		this.fazBarulho = fazBarulho;
		this.tipoBolha = tipoBolha;
		v = velocidade;
		
		if(tipoBolha == B_NORMAL)
			imagem = CCSprite.sprite(Assets.BUBBLE);
		
		if(tipoBolha == B_ENEMY)
			imagem = CCSprite.sprite(Assets.BUBBLE_BAD);
		
		if(tipoBolha == B_FAST)
			imagem = CCSprite.sprite(Assets.BUBBLE_FAST);
		
		if(tipoBolha == B_SLOW)
			imagem = CCSprite.sprite(Assets.BUBBLE_SLOW);
		
		if(tipoBolha == B_LIFE)
			imagem = CCSprite.sprite(Assets.BUBBLE_LIFE);
		
		if(tipoBolha == B_RANDOM)
			imagem = CCSprite.sprite(Assets.BUBBLE_RANDOM);
		
		addChild(imagem);
		int sort = new Random().nextInt(4);
		switch (sort) {
		case 0:
			x = 90f;
			break;
		case 1:
			x = 190f;
			break;
		case 2:
			x = 290f;
			break;
		case 3:
			x = 390;
			break;
		}
		y = screenHeight();
	}

	public void setDelegate(BubblesEngineDelegate sender) {
		this.delegate = sender;
	}
	
	public void start() {
		schedule("update");
	}

	@SuppressWarnings("static-access")
	public void update(float dt) {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			y -= v;
			if(y < 90){
				//Se for uma "bubble_bad" remove ela sem erro. Caso contrário, game over!
				if(getTipoBolha() == B_NORMAL){
					if(config.getPlayEffects())
						SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.over);
					removeChild(imagem,true);
					imagem = CCSprite.sprite(Assets.BUBBLE_BAD);
					addChild(imagem);
					scene.lifeDown();
					if(scene.getLife() >= 0){
						removeBubbleDiferente();
					}else{
						unschedule("update");
						scene.gameOverScreen();
					}
				}else{
					removeBubbleDiferente();
				}
			}
			setPosition(screenResolution(CGPoint.ccp(x, y)));
		}
	}
	
	public void removeBubbleDiferente() {
		unschedule("update");
		delegate.removeBubble(this);
		removeFromParentAndCleanup(true);
	}
	
	public void bursted(boolean semLife) {
		unschedule("update");
		if(fazBarulho)
			executaSomBolha(getTipoBolha());
		
		if(getTipoBolha() == B_ENEMY){
			if(config.getPlayEffects())
				SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.over);
			if(semLife){
				scene.gameOverScreen();
			}
		}
		delegate.removeBubble(this);
		Vibrator v = (Vibrator) CCDirector.sharedDirector().getActivity().getSystemService(Context.VIBRATOR_SERVICE);
		v.vibrate(20);
		removeFromParentAndCleanup(true);
	}
	
	private void executaSomBolha(int tipoSomBolha) {
		if(tipoSomBolha == B_NORMAL)
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.burst);
		if(tipoSomBolha == B_ENEMY)
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.over);
		if(tipoSomBolha == B_FAST)
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.fast);
		if(tipoSomBolha == B_SLOW)
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.slow);
		if(tipoSomBolha == B_LIFE)
			SoundEngine.sharedEngine().playEffect(CCDirector.sharedDirector().getActivity(), R.raw.life);
	}

	// faz com que o próprio objeto botão receba as notificações de toque
	@Override
	protected void registerWithTouchDispatcher() {
		CCTouchDispatcher.sharedDispatcher().addTargetedDelegate(this, 0, false);
	}
	
	/*verificar se o menu em questão foi tocado	através do método CGRect.containsPoint,
	que verificará se o local tocado (touchLocation) está dentro da “caixa” que a imagem
	do menu forma (buttonImage.getBoudingBox())*/
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchLocation = CGPoint.make(event.getX(), event.getY());
		touchLocation = CCDirector.sharedDirector().convertToGL(touchLocation);
		touchLocation = this.convertToNodeSpace(touchLocation);

		// Check Button touched
		if (CGRect.containsPoint(imagem.getBoundingBox(), touchLocation)) {
			delegate.bubbleClicked(this);
		}
		return true;
	}

	public GameScene getScene() {
		return scene;
	}

	public void setScene(GameScene scene) {
		this.scene = scene;
	}
	
	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public CCSprite getImagem() {
		return imagem;
	}

	public void setImagem(CCSprite imagem) {
		this.imagem = imagem;
	}

	public int getTipoBolha() {
		return tipoBolha;
	}

	public void setTipoBolha(int tipoBolha) {
		this.tipoBolha = tipoBolha;
	}
}
