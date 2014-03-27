package net.drumber.nestedtictactoe.game.ai;

import java.util.ArrayList;

import net.drumber.nestedtictactoe.game.GameState;
import net.drumber.nestedtictactoe.game.GameState.GridState;
import net.drumber.nestedtictactoe.game.GameState.Side;
import net.drumber.nestedtictactoe.game.Grid.Point;


//Uses the minmax algorithm to determine the best move (negamax with alpha-beta pruning)
//Moves good for crosses are given a positive value, moves good for noughts are given a negative value
//See http://en.wikipedia.org/wiki/Negamax for details
//Note that the first "plie" is done in getMove() to get the best move (rather than its weighting)
//So minimum depth is 1, and the first call to minMax switches the sign (as we are then looking for our opponents move next)
public class MinMaxAI extends AI {
	
	private int maxDepth;
	
	//Allows class to be serialised
	@SuppressWarnings("unused")
	private MinMaxAI(){	}
	
	public MinMaxAI(int maxDepth){
		if(maxDepth < 0){
			maxDepth = 0;
		}
		this.maxDepth = maxDepth;
	}

	@Override
	public Point getMove(GameState gs, Side side) {
		ArrayList<Point> moves = gs.getValidMoves();
		
		if(!gs.gameStarted){ //Pick a random move if in the initial state (As all reasonable depths can't see a difference between initial moves anyway)
			return moves.get(RANDOM.nextInt(moves.size()));
		}
		
		int maxRating = Integer.MIN_VALUE + 1;
		ArrayList<Point> bestMoves = new ArrayList<Point>();
		for (Point move : moves) {
			GameState nextGameState = new GameState(gs);
			nextGameState.makeMove(move.x, move.y);
			int rating;
			if(side == Side.CROSSES){
				rating = -1*minMax(nextGameState, maxDepth,  -1);
			} else {
				rating = -1*minMax(nextGameState, maxDepth,  1);
			}
			//System.out.println(move.x + ", " + move.y + ": " + rating);
			if (rating > maxRating) {
				maxRating = rating;
				bestMoves.clear();
				bestMoves.add(move);
			} else if (rating == maxRating) {
				bestMoves.add(move);
			}
		}
		return bestMoves.get(RANDOM.nextInt(bestMoves.size()));
	}
	
	private int minMax(GameState gs, int depth, int sign){
		return minMax(gs, depth, Integer.MIN_VALUE + 1, Integer.MAX_VALUE, sign);
	}

	private int minMax(GameState gs, int depth, int alpha, int beta, int sign){
		if (depth == 0 || gs.currentState == GridState.WIN_CROSS || gs.currentState == GridState.WIN_NOUGHT || gs.currentState == GridState.DRAW) {
			return sign*stateValue(gs);
		}
		int bestValue = Integer.MIN_VALUE + 1;
		for (Point nextMove : gs.getValidMoves()) {
			GameState nextState = new GameState(gs);
			nextState.makeMove(nextMove.x, nextMove.y);
			int value = -1*minMax(nextState, depth - 1, -1*beta, -1*alpha, -1*sign);
			bestValue = Math.max(bestValue, value);
			alpha = Math.max(alpha, value);
			if(alpha >= beta){
				break;
			}
		}
		return bestValue;
	}

	private int stateValue(GameState gs) {
		if(gs.currentState == GridState.WIN_CROSS){
			return Integer.MAX_VALUE;
		} else if(gs.currentState == GridState.WIN_NOUGHT){
			return Integer.MIN_VALUE + 1;
		}
		
		int closeToWin = 0; //+1 if crosses can win with 1 more subgrid, -1 for noughts
		if(gs.currentState == GridState.CLOSE_CROSS){
			closeToWin = 1;
		} else if(gs.currentState == GridState.CLOSE_NOUGHT){
			closeToWin = -1;
		}
		
		int subGridsWon = 0;
		int closeSubGrids = 0; //Number of subgrids that can be won with a single move
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if(gs.subGridStates[i][j] == GridState.WIN_CROSS){
					subGridsWon++;
				} else if(gs.subGridStates[i][j] == GridState.WIN_NOUGHT){
					subGridsWon--;
				} else if(gs.subGridStates[i][j] == GridState.CLOSE_CROSS){
					closeSubGrids++;
				} else if(gs.subGridStates[i][j] == GridState.CLOSE_NOUGHT){
					closeSubGrids--;
				}
			}
		}
		
		return closeToWin*100 + subGridsWon*10 + closeSubGrids;
	}

}
