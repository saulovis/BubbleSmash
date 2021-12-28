package com.mitnickgame.bubblesmash.game.objects;

import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenResolution;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenWidth;

import java.util.Random;

import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;

import com.mitnickgame.bubblesmash.game.interfaces.SmallBubblesEngineDelegate;
import com.mitnickgame.bubblesmash.game.scenes.TitleScene;

public class SmallBubble extends CCSprite {
	private float x, y;
	int count, direcao;
	
	private SmallBubblesEngineDelegate delegate;
	private TitleScene scene;

	public void setDelegate(SmallBubblesEngineDelegate delegate) {
		this.delegate = delegate;
	}

	public SmallBubble(String image) {
		super(image);
		x = new Random().nextInt(Math.round(screenWidth()));
		y = 60;
		count = 0;
	}

	public float getX() {
		return x;
	}

	public float getY() {
		return y;
	}

	public void start() {
		this.schedule("update");
	}
	
	public void update(float dt) {
		y += 1;
		count++;
		//se o contador passou de 20 ele randomiza uma nova direção.
		if(count == 30){
			direcao = new Random().nextInt(3);
			count = 0;
		}
		//dependendo da direção ele diminui, aumenta ou não faz nada em X.
		if(direcao == 1){
			x += 0.5f;
		}
		if(direcao == 2){
			x -= 0.5f;
		}
		setPosition(screenResolution(CGPoint.ccp(x, y)));
	}
	
	public void bursted() {
		unschedule("update");
		delegate.removeSmallBubble(this);
		removeFromParentAndCleanup(true);
	}

	public TitleScene getScene() {
		return scene;
	}

	public void setScene(TitleScene scene) {
		this.scene = scene;
	}
}