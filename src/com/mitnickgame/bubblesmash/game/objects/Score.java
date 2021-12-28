package com.mitnickgame.bubblesmash.game.objects;

import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenHeight;
import static com.mitnickgame.bubblesmash.config.DeviceSettings.screenWidth;

import org.cocos2d.layers.CCLayer;
import org.cocos2d.nodes.CCSprite;
import org.cocos2d.opengl.CCBitmapFontAtlas;
import org.cocos2d.types.ccColor3B;

import com.mitnickgame.bubblesmash.config.Assets;
import com.mitnickgame.bubblesmash.game.scenes.GameScene;

public class Score extends CCLayer {
	
	private int score, life;
	private CCBitmapFontAtlas textScore, textLife;
	
	private GameScene delegate;
	
	public GameScene getDelegate() {
		return delegate;
	}

	public void setDelegate(GameScene delegate) {
		this.delegate = delegate;
	}

	public Score(boolean showLife){
		score = life = 0;
		//imagem do score de bolhas
		CCSprite imagemScore = CCSprite.sprite(Assets.SMALLBUBBLE);
		imagemScore.setPosition(screenWidth() / 2, screenHeight()-50);
		addChild(imagemScore);
		//texto do score de bolhas
		textScore = CCBitmapFontAtlas.bitmapFontAtlas(String.valueOf(this.score),"UniSansSemiBold_Numbers_240.fnt");
		textScore.setScale((float) 40 / 120);
		textScore.setColor(ccColor3B.ccWHITE);
		textScore.setPosition(screenWidth() / 2 + 30, screenHeight()-50);
		addChild(textScore);
		if(showLife){
			//imagem da life
			CCSprite imagemLife = CCSprite.sprite(Assets.SMALL_LIFE);
			imagemLife.setPosition(screenWidth() * 0.85f, screenHeight()-50);
			addChild(imagemLife);
			//texto do numero de vidas
			textLife = CCBitmapFontAtlas.bitmapFontAtlas(String.valueOf(this.life),"UniSansSemiBold_Numbers_240.fnt");
			textLife.setScale((float) 40 / 120);
			textLife.setColor(ccColor3B.ccGREEN);
			textLife.setPosition(screenWidth() * 0.9f, screenHeight()-50);
			addChild(textLife);
		}
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLife() {
		return life;
	}

	public void setLife(int life) {
		this.life = life;
	}

	public void increase() {
		score++;
		textScore.setString(String.valueOf(score));
		
		/*if(score >= 30){
			delegate.startFinalScreen();
		}*/
		
	}
	public void lifeUp() {
		life++;
		textLife.setString(String.valueOf(life));
	}
	public void lifeDown() {
		life--;
		textLife.setString(String.valueOf(life));
	}
	
}
