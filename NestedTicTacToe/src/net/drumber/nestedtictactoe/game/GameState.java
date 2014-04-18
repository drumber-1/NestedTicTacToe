package net.drumber.nestedtictactoe.game;

import java.util.ArrayList;

import net.drumber.nestedtictactoe.game.Grid.Point;
import net.drumber.nestedtictactoe.game.ai.AI;


//Acts as an api for saving/loading games
public class GameState {

	public enum Element {
		NOUGHT, CROSS, EMPTY
	}

	public enum Side {
		NOUGHTS, CROSSES
	}

	// The close states are when one or both sides can win the grid in one move
	public enum GridState {
		WIN_NOUGHT, WIN_CROSS, DRAW, UNDECIDED, CLOSE_NOUGHT, CLOSE_CROSS, CLOSE_BOTH;
	}

	public Element[][] elements = new Element[9][9];

	public AI noughtsAI;
	public AI crossesAI;

	public String noughtsName;
	public String crossesName;

	public Side currentSide;
	public Side firstSide;
	public boolean globalMove;
	public boolean gameStarted;
	public Point currentSubGrid;
	public GridState currentState;
	public GridState[][] subGridStates = new GridState[3][3];

	public GameState() {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				elements[i][j] = Element.EMPTY;
			}
		}
		this.globalMove = true;
		this.gameStarted = false;
		this.crossesAI = null;
		this.noughtsAI = null;
		this.currentSubGrid = new Point();
		this.currentState = GridState.UNDECIDED;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				subGridStates[i][j] = GridState.UNDECIDED;
			}
		}
		noughtsName = "Noughts";
		crossesName = "Crosses";
	}

	public GameState(GameState gs) {
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9; j++) {
				this.elements[i][j] = gs.elements[i][j];
			}
		}
		this.noughtsAI = gs.noughtsAI;
		this.crossesAI = gs.crossesAI;
		this.currentSide = gs.currentSide;
		this.firstSide = gs.firstSide;
		this.globalMove = gs.globalMove;
		this.gameStarted = gs.gameStarted;
		this.crossesName = gs.crossesName;
		this.noughtsName = gs.noughtsName;
		this.currentSubGrid = new Point(gs.currentSubGrid);
		this.currentState = gs.currentState;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				subGridStates[i][j] = gs.subGridStates[i][j];
			}
		}
	}

	public boolean isValidMove(int x, int y) {
		// Coordinates of subgrid
		int sg_x = (x / 3);
		int sg_y = (y / 3);

		if (!globalMove) {
			if (sg_x != currentSubGrid.x || sg_y != currentSubGrid.y) {
				return false; // Not on correct subgrid, return
			}
		}
		if (!(subGridStates[sg_x][sg_y] == GridState.WIN_CROSS || subGridStates[sg_x][sg_y] == GridState.WIN_NOUGHT || subGridStates[sg_x][sg_y] == GridState.DRAW)) {
			if (elements[x][y] == Element.EMPTY) {
				return true;
			} else {
				return false; // Subgrid is complete, return
			}
		} else {
			return false; // Cell is already occupied
		}
	}

	public boolean makeMove(int xMove, int yMove) {
		if (!isValidMove(xMove, yMove)) {
			return false;
		}

		gameStarted = true;

		if (currentSide == Side.CROSSES) {
			elements[xMove][yMove] = Element.CROSS;
			updateSubGridState(xMove / 3, yMove / 3);
			currentSide = Side.NOUGHTS;
		} else {
			elements[xMove][yMove] = Element.NOUGHT;
			updateSubGridState(xMove / 3, yMove / 3);
			currentSide = Side.CROSSES;
		}

		currentSubGrid.set(xMove % 3, yMove % 3); // Move mod 3 is the position in the subgrid

		if (subGridStates[currentSubGrid.x][currentSubGrid.y] == GridState.WIN_CROSS || subGridStates[currentSubGrid.x][currentSubGrid.y] == GridState.WIN_NOUGHT || subGridStates[currentSubGrid.x][currentSubGrid.y] == GridState.DRAW) {
			globalMove = true;
		} else {
			globalMove = false;
		}

		return true;
	}

	private void updateSubGridState(int sg_x, int sg_y) {
		int mask = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Element e = elements[(sg_x * 3) + i][(sg_y * 3) + j];
				if (e == Element.CROSS) {
					mask += Math.round(Math.pow(3, i + 3 * j));
				} else if (e == Element.NOUGHT) {
					mask += Math.round(2 * Math.pow(3, i + 3 * j));
				}
			}
		}
		System.out.println("mask: " + mask);
		System.out.println("new State: " + StateLookUp.getState(mask));
		subGridStates[sg_x][sg_y] = StateLookUp.getState(mask);
		if (subGridStates[sg_x][sg_y] == GridState.WIN_CROSS || subGridStates[sg_x][sg_y] == GridState.WIN_NOUGHT || subGridStates[sg_x][sg_y] == GridState.DRAW) {
			updateState();
		}
	}

	//The global grid actually has 4 states per cell, (draw being the extra one)
	//This is only a problem when every subgrid is finished and some are draws.
	//In this case we need to tell the game it is actually a draw
	private void updateState() {
		boolean full = true;
		int mask = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				GridState s = subGridStates[i][j];
				if (s == GridState.WIN_CROSS) {
					mask += Math.round(Math.pow(3, i + 3 * j));
				} else if (s == GridState.WIN_NOUGHT) {
					mask += Math.round(2 * Math.pow(3, i + 3 * j));
				} else if (s != GridState.DRAW) {
					full = false;
				}
			}
		}
		currentState = StateLookUp.getState(mask);
		//If grid is full and no-one has won, must be a draw
		if (full && currentState != GridState.WIN_CROSS && currentState != GridState.WIN_NOUGHT) {
			currentState = GridState.DRAW;
		}
	}

	public ArrayList<Point> getValidMoves() {
		ArrayList<Point> moves = new ArrayList<Point>();

		if (globalMove) {
			for (int i = 0; i < 9; i++) {
				for (int j = 0; j < 9; j++) {
					if (subGridStates[i / 3][j / 3] == GridState.WIN_CROSS || subGridStates[i / 3][j / 3] == GridState.WIN_NOUGHT || subGridStates[i / 3][j / 3] == GridState.DRAW) {
						continue;
					}
					if (elements[i][j] == Element.EMPTY) {
						moves.add(new Point(i, j));
					}
				}
			}
		} else {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					if (elements[i + currentSubGrid.x * 3][j + currentSubGrid.y * 3] == Element.EMPTY) {
						moves.add(new Point(i + currentSubGrid.x * 3, j + currentSubGrid.y * 3));
					}
				}
			}
		}

		return moves;
	}

}
