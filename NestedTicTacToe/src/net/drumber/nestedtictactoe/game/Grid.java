package net.drumber.nestedtictactoe.game;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.Input;
import net.drumber.nestedtictactoe.NestedPreferences;
import net.drumber.nestedtictactoe.game.GameState.Element;
import net.drumber.nestedtictactoe.game.GameState.GridState;
import net.drumber.nestedtictactoe.game.GameState.Side;
import net.drumber.nestedtictactoe.game.ai.AI;
import net.drumber.nestedtictactoe.screens.BasicScreen;

import com.badlogic.gdx.Gdx;


public class Grid {

	public static class Point {
		public int x;
		public int y;

		public Point() {
			set(0, 0);
		}

		public Point(int x, int y) {
			set(x, y);
		}

		public Point(Point p) {
			set(p.x, p.y);
		}

		public void set(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}

	protected final static float SIZE = 8.0f;
	protected final static float CELL_SIZE = SIZE / 9.0f;
	protected final static float SUBGRID_SIZE = SIZE / 3.0f;
	protected final static float SMALL_HIGHLIGHT_SIZE = CELL_SIZE * 62.0f / 56.0f;
	protected final static float LARGE_HIGHLIGHT_SIZE = SUBGRID_SIZE * 162.0f / 156.0f;
	protected final static float SMALL_ELEMENT_SIZE = CELL_SIZE * 56.0f / 62.0f;
	protected final static float LARGE_ELEMENT_SIZE = SUBGRID_SIZE * 156.0f / 162.0f;
	protected final static float ELEMENT_OFFSET = CELL_SIZE * 6.0f / 56.0f;

	// Position of the top left of the grid in the screens camera coordinates
	protected float xScreen;
	protected float yScreen;

	protected Point selectedCell;
	protected boolean cellIsSelected; // True if one of the cells has been selected

	protected boolean waitingonAi = false;
	private Point aiMove = null; //Field for ai move thread to populate, needs to be reset to null after it is used

	protected GameState gameState;

	protected CrossesPlayer crossesPlayer;
	protected NoughtsPlayer noughtsPlayer;
	protected Player currentPlayer;

	public Grid(float x, float y, CrossesPlayer crossesPlayer, NoughtsPlayer noughtsPlayer, Side first) {
		this.xScreen = x;
		this.yScreen = y;

		this.crossesPlayer = crossesPlayer;
		this.noughtsPlayer = noughtsPlayer;

		this.gameState = new GameState();
		this.gameState.crossesAI = crossesPlayer.getAi();
		this.gameState.noughtsAI = noughtsPlayer.getAi();
		this.gameState.crossesName = crossesPlayer.getName();
		this.gameState.noughtsName = noughtsPlayer.getName();
		this.gameState.currentSide = first;
		this.gameState.firstSide = first;

		if (first == Side.CROSSES) {
			currentPlayer = crossesPlayer;
		} else {
			currentPlayer = noughtsPlayer;
		}

		selectedCell = new Point();
		cellIsSelected = false;
	}

	public Grid(float x, float y, GameState gameState) {
		this.xScreen = x;
		this.yScreen = y;

		this.gameState = gameState;
		this.crossesPlayer = new CrossesPlayer(gameState.crossesName, gameState.crossesAI);
		this.noughtsPlayer = new NoughtsPlayer(gameState.noughtsName, gameState.noughtsAI);
		if (gameState.currentSide == Side.NOUGHTS) {
			currentPlayer = noughtsPlayer;
		} else {
			currentPlayer = crossesPlayer;
		}

		selectedCell = new Point();
		cellIsSelected = false;
	}

	public void update(Input input) {
		if (isFinished()) {
			return;
		}

		AI currentAI;
		currentAI = getCurrentPlayer().getAi();

		if (currentAI == null) {
			checkPlayerInput(input);
		} else {
			if (!waitingonAi) {
				startAIMoveThread(currentAI);
			} else {
				if(aiMove != null){
					makeMove(aiMove.x, aiMove.y);
					aiMove = null;
					waitingonAi = false;
				}
			}
		}

	}

	private void startAIMoveThread(final AI ai) {
		waitingonAi = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				final Point move = ai.getMove(gameState, getCurrentPlayer().getSide());
				Gdx.app.postRunnable(new Runnable() {
					@Override
					public void run() {
						aiMove = move;
					}
				});
			}
		}).start();
	}

	private void checkPlayerInput(Input input) {
		if (input.isPointerDown()) {
			// Position relative to grid corner
			float gx = input.getPointerLocation().x - this.xScreen;
			float gy = input.getPointerLocation().y - this.yScreen;
			if (gx >= 0 && gx < SIZE && gy >= 0 && gy < SIZE) {
				// Coordinates of cell
				int cx = (int) (gx / CELL_SIZE);
				int cy = (int) (gy / CELL_SIZE);
				if (gameState.isValidMove(cx, cy)) {
					selectedCell.set(cx, cy);
					cellIsSelected = true;
				}
			}

		}
	}

	public void render(BasicScreen screen) {
		screen.draw(Art.grid, xScreen, yScreen, SIZE, SIZE);

		if (!gameState.globalMove && !isFinished()) {
			if (getCurrentPlayer().getAi() == null) {
				screen.draw(getCurrentPlayer().getHighLightTexture(), xScreen + gameState.currentSubGrid.x * SUBGRID_SIZE, yScreen + gameState.currentSubGrid.y * SUBGRID_SIZE, LARGE_HIGHLIGHT_SIZE, -1.0f);
			}
		}

		if (cellIsSelected && !isFinished()) {
			if (NestedPreferences.isShowNextMove()) {
				screen.draw(Art.yellow_highlight_large, xScreen + (selectedCell.x % 3) * SUBGRID_SIZE, yScreen + (selectedCell.y % 3) * SUBGRID_SIZE, LARGE_HIGHLIGHT_SIZE, -1.0f);
			}
			screen.draw(Art.yellow_highlight, xScreen + selectedCell.x * CELL_SIZE, yScreen + selectedCell.y * CELL_SIZE, SMALL_HIGHLIGHT_SIZE, -1.0f);
		}

		for (int sg_i = 0; sg_i < 3; sg_i++) {
			for (int sg_j = 0; sg_j < 3; sg_j++) {
				if (gameState.subGridStates[sg_i][sg_j] == GridState.WIN_NOUGHT) {
					screen.draw(Art.nought, ELEMENT_OFFSET + xScreen + sg_i * SUBGRID_SIZE, ELEMENT_OFFSET + yScreen + sg_j * SUBGRID_SIZE, LARGE_ELEMENT_SIZE, -1.0f);
					continue;
				} else if (gameState.subGridStates[sg_i][sg_j] == GridState.WIN_CROSS) {
					screen.draw(Art.cross, ELEMENT_OFFSET + xScreen + sg_i * SUBGRID_SIZE, ELEMENT_OFFSET + yScreen + sg_j * SUBGRID_SIZE, LARGE_ELEMENT_SIZE, -1.0f);
					continue;
				}

				for (int cell_i = 0; cell_i < 3; cell_i++) {
					for (int cell_j = 0; cell_j < 3; cell_j++) {
						if (gameState.elements[cell_i + sg_i * 3][cell_j + sg_j * 3] == Element.NOUGHT) {
							screen.draw(Art.nought, ELEMENT_OFFSET + xScreen + sg_i * SUBGRID_SIZE + cell_i * CELL_SIZE, ELEMENT_OFFSET + yScreen + sg_j * SUBGRID_SIZE + cell_j * CELL_SIZE, SMALL_ELEMENT_SIZE, -1.0f);
						} else if (gameState.elements[cell_i + sg_i * 3][cell_j + sg_j * 3] == Element.CROSS) {
							screen.draw(Art.cross, ELEMENT_OFFSET + xScreen + sg_i * SUBGRID_SIZE + cell_i * CELL_SIZE, ELEMENT_OFFSET + yScreen + sg_j * SUBGRID_SIZE + cell_j * CELL_SIZE, SMALL_ELEMENT_SIZE, -1.0f);
						}
					}
				}
			}
		}

	}

	public boolean isFinished() {
		if (gameState.currentState == GridState.WIN_CROSS || gameState.currentState == GridState.WIN_NOUGHT || gameState.currentState == GridState.DRAW) {
			return true;
		} else {
			return false;
		}
	}

	public boolean makeMove() {
		return makeMove(selectedCell.x, selectedCell.y);
	}

	public boolean makeMove(int x, int y) {
		if (gameState.makeMove(x, y)) {
			if (currentPlayer == noughtsPlayer) {
				currentPlayer = crossesPlayer;
			} else {
				currentPlayer = noughtsPlayer;
			}
			cellIsSelected = false;
			return true;
		} else {
			return false;
		}
	}

	public void cancelSelectedMove() {
		cellIsSelected = false;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}

	public NoughtsPlayer getNoughtsPlayer() {
		return noughtsPlayer;
	}

	public CrossesPlayer getCrossesPlayer() {
		return crossesPlayer;
	}

	public GridState getState() {
		return gameState.currentState;
	}

	public boolean isCellIsSelected() {
		return cellIsSelected;
	}

	public GameState getGameState() {
		return gameState;
	}

	public boolean isWaitingOnAI() {
		return waitingonAi;
	}

}
