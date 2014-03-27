package net.drumber.nestedtictactoe.game.ai;

import java.util.ArrayList;

import net.drumber.nestedtictactoe.game.GameState;
import net.drumber.nestedtictactoe.game.GameState.Side;
import net.drumber.nestedtictactoe.game.Grid.Point;


public class RandomAI extends AI {

	@Override
	public Point getMove(GameState gs, Side side) {
		ArrayList<Point> moves = gs.getValidMoves();
		return moves.get(RANDOM.nextInt(moves.size()));
	}

}
