package net.drumber.nestedtictactoe.game.ai;

import java.util.Random;

import net.drumber.nestedtictactoe.game.GameState;
import net.drumber.nestedtictactoe.game.GameState.Side;
import net.drumber.nestedtictactoe.game.Grid.Point;


public abstract class AI {
	
	public static final Random RANDOM = new Random();
	
	public AI(){
	}
	
	public abstract Point getMove(GameState gs, Side side);

}
