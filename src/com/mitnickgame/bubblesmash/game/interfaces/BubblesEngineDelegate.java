package com.mitnickgame.bubblesmash.game.interfaces;

import com.mitnickgame.bubblesmash.game.objects.Bubble;

public interface BubblesEngineDelegate {
	public void createBubble(Bubble b);
	public void bubbleClicked(Bubble b);
	public void removeBubble(Bubble b);
}
