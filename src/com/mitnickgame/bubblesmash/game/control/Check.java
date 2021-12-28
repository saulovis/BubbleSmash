package com.mitnickgame.bubblesmash.game.control;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.view.MotionEvent;

public class Check extends CCLayer {
	private CCSprite checkImage;
	private CheckDelegate delegate;

	public Check(String checkImage) {
		this.setIsTouchEnabled(true);
		this.checkImage = CCSprite.sprite(checkImage);
		addChild(this.checkImage);
	}

	public void setDelegate(CheckDelegate sender) {
		this.delegate = sender;
	}
	
	public CCSprite getCheckImage() {
		return checkImage;
	}

	public void setCheckImage(CCSprite checkImage) {
		this.checkImage = checkImage;
	}

	// faz com que o pr�prio objeto bot�o receba as notifica��es de toque
	@Override
	protected void registerWithTouchDispatcher() {
		CCTouchDispatcher.sharedDispatcher().addTargetedDelegate(this, 0, false);
	}
	
	/*verificar se o menu em quest�o foi tocado	atrav�s do m�todo CGRect.containsPoint,
	que verificar� se o local tocado (touchLocation) est� dentro da �caixa� que a imagem
	do menu forma (buttonImage.getBoudingBox())*/
	@Override
	public boolean ccTouchesBegan(MotionEvent event) {
		CGPoint touchLocation = CGPoint.make(event.getX(), event.getY());
		touchLocation = CCDirector.sharedDirector().convertToGL(touchLocation);
		touchLocation = this.convertToNodeSpace(touchLocation);

		// Check Button touched
		if (CGRect.containsPoint(this.checkImage.getBoundingBox(), touchLocation)) {
			delegate.checkClicked(this);
		}
		return true;
	}

}
