package com.mitnickgame.bubblesmash.config;

import org.cocos2d.nodes.CCDirector;
import org.cocos2d.types.CGPoint;
import org.cocos2d.types.CGSize;

public class DeviceSettings {
	
	public static boolean telaInicial, jaCriouEditText;
	
	public static CGPoint screenResolution(CGPoint gcPoint) {
		return gcPoint;
	}

	public static float screenWidth() {
		return winSize().width; 
	}

	public static float screenHeight() {
		return winSize().height; 
	}
	
	public static CGSize winSize() {
		return CCDirector.sharedDirector().winSize();
	}
}
