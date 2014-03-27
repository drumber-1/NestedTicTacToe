package net.drumber.nestedtictactoe.game;

import net.drumber.nestedtictactoe.game.GameState.Side;
import net.drumber.nestedtictactoe.game.ai.AI;

public class CrossesPlayer extends Player {

	public CrossesPlayer(String name, AI ai) {
		super(Side.CROSSES, ai, name);
	}

}
