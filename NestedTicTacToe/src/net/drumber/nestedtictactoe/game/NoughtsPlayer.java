package net.drumber.nestedtictactoe.game;

import net.drumber.nestedtictactoe.game.GameState.Side;
import net.drumber.nestedtictactoe.game.ai.AI;

public class NoughtsPlayer extends Player {
	
	public NoughtsPlayer(String name, AI ai){
		super(Side.NOUGHTS, ai, name);
	}

}
