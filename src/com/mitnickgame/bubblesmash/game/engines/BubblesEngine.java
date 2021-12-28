package com.mitnickgame.bubblesmash.game.engines;

import java.util.Random;

import org.cocos2d.layers.CCLayer;

import com.mitnickgame.bubblesmash.config.Runner;
import com.mitnickgame.bubblesmash.game.interfaces.BubblesEngineDelegate;
import com.mitnickgame.bubblesmash.game.objects.Bubble;

import static com.mitnickgame.bubblesmash.MainActivity.config;

public class BubblesEngine extends CCLayer {
	private BubblesEngineDelegate delegate;
	private float velocidade;
	private int tipoJogo;
	
	public static final int J_NORMAL = 0;
	public static final int J_ENEMY = 1;
	public static final int J_BABY = 2;
	public static final int J_ARCADE = 3;

	public BubblesEngine(float velocidade, int tipoJogo) {
		//carregando a config
		this.velocidade = velocidade;
		this.tipoJogo = tipoJogo;
		this.schedule("bubblesEngine", 1.0f / 10f);
	}

	@SuppressWarnings("static-access")
	public void bubblesEngine(float dt) {
		if (Runner.check().isGamePlaying() && !Runner.check().isGamePaused()) {
			if(tipoJogo == J_NORMAL || tipoJogo == J_BABY){
				//jogo NORMAL ou BABY, todas as bolhas são normais
				this.getDelegate().createBubble(new Bubble(velocidade,config.getPlayEffects(), J_NORMAL));
			}else{
				boolean diferente = new Random().nextInt(8) == 0;
				if(tipoJogo == J_ENEMY){
					//jogo no modo ENEMY, caso o random dê zero cria-se uma bolha vermelha
					this.getDelegate().createBubble(new Bubble(velocidade,config.getPlayEffects(), diferente ? J_ENEMY : J_NORMAL));
				}else{
					//jogo no modo ARCADE, caso o random dê zero cria-se uma bolha diferente, que pode ser fast, slow, life ou random.
					int tipoBolha = 0;
					if(diferente){
						tipoBolha = new Random().nextInt(5) + 1;
					}
					this.getDelegate().createBubble(new Bubble(velocidade,config.getPlayEffects(),tipoBolha));
				}
			}
		}
	}
	
	// a cada bolha estourada aumenta em 0.06 a velocidade da descida
	public void aumentaVelocidade(float velocidade) {
		this.velocidade += velocidade;
	}
	
	public void diminuiVelocidade(float velocidade) {
		if(this.velocidade > 5)
			this.velocidade -= velocidade;
	}

	public void setDelegate(BubblesEngineDelegate delegate) {
		this.delegate = delegate;
	}

	public BubblesEngineDelegate getDelegate() {
		return delegate;
	}
}