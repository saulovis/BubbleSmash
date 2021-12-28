package com.mitnickgame.bubblesmash.game.control;

import org.cocos2d.events.CCTouchDispatcher;
import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCDirector;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGRect;

import android.view.MotionEvent;

public class Button extends CCLayer {

	private CCSprite buttonImage;
	private ButtonDelegate delegate;

	public Button(String buttonImage) {
		this.setIsTouchEnabled(true);
		this.buttonImage = CCSprite.sprite(buttonImage);
		addChild(this.buttonImage);
	}

	public void setDelegate(ButtonDelegate sender) {
		this.delegate = sender;
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
		if (CGRect.containsPoint(this.buttonImage.getBoundingBox(), touchLocation)) {
			delegate.buttonClicked(this);
		}
		return true;
	}

}
