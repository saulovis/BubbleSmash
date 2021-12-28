package com.mitnickgame.bubblesmash.game.engines;

import java.util.Random;

import org.cocos2d.layers.CCLayer;

import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.game.interfaces.SmallBubblesEngineDelegate;
import com.mitnickgame.bubblesmash.game.objects.SmallBubble;


public class SmallBubblesEngine extends CCLayer {
	private SmallBubblesEngineDelegate delegate;
	
	public SmallBubblesEngine() {
		this.schedule("smallBubblesEngine", 1.0f / 10f);
	}
	
	public void smallBubblesEngine(float dt) {
		if (new Random().nextInt(10) == 0) {
			this.getDelegate().createSmallBubble(new SmallBubble(Assets.SMALLBUBBLE));
		}
	}
	
	public void setDelegate(SmallBubblesEngineDelegate delegate) {
		this.delegate = delegate;
	}

	public SmallBubblesEngineDelegate getDelegate() {
		return delegate;
	}
}