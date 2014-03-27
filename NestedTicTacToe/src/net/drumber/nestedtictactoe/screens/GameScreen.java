package net.drumber.nestedtictactoe.screens;

import java.io.IOException;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.NestedPreferences;
import net.drumber.nestedtictactoe.NestedTicTacToe;
import net.drumber.nestedtictactoe.Save;
import net.drumber.nestedtictactoe.game.CrossesPlayer;
import net.drumber.nestedtictactoe.game.GameState;
import net.drumber.nestedtictactoe.game.Grid;
import net.drumber.nestedtictactoe.game.NoughtsPlayer;
import net.drumber.nestedtictactoe.game.GameState.GridState;
import net.drumber.nestedtictactoe.game.GameState.Side;
import net.drumber.nestedtictactoe.screens.ui.ButtonManager;
import net.drumber.nestedtictactoe.screens.ui.LayeredTextBoxButton;
import net.drumber.nestedtictactoe.screens.ui.TextBoxButton;
import net.drumber.nestedtictactoe.screens.ui.TextureButton;
import net.drumber.nestedtictactoe.screens.ui.TextureButton.ButtonType;


public class GameScreen extends BasicScreen {

	protected ButtonManager buttonManager;
	protected Grid grid;
	
	protected LayeredTextBoxButton makeMoveButton;
	protected TextBoxButton againButton;

	protected CrossesPlayer crossesPlayer;
	protected NoughtsPlayer noughtsPlayer;
	protected Side first;
	
	private float aiMoveTimer = 0.0f;
	
	public GameScreen(NestedTicTacToe nestedTicTacToe) throws IOException {
		super(nestedTicTacToe);
		init();
		
		GameState gs;
		
		if (Save.saveExists()) {
			gs = Save.loadGameState();
		} else {
			throw new IOException("Cannot load gamestate, no save exists");
		}
		
		grid = new Grid(4.0f, 0.5f, gs);
		
		crossesPlayer = new CrossesPlayer(gs.crossesName, gs.crossesAI);
		noughtsPlayer = new NoughtsPlayer(gs.noughtsName, gs.noughtsAI);
	}

	public GameScreen(NestedTicTacToe nestedTicTacToe, CrossesPlayer crossesPlayer, NoughtsPlayer noughtsPlayer, Side first) {
		super(nestedTicTacToe);
		init();

		this.crossesPlayer = crossesPlayer;
		this.noughtsPlayer = noughtsPlayer;
		
		this.first = first;

		Save.deleteSave();
		grid = new Grid(4.0f, 0.5f, this.crossesPlayer, this.noughtsPlayer, first);
	}
	
	private void init() {
		
		//long seed = System.nanoTime();
		//long seed = 15081223558177l;
		//System.out.println(seed);
		//AI.RANDOM.setSeed(seed);
		
		buttonManager = new ButtonManager();
		buttonManager.add(new TextureButton(0.25f, 7.75f, 1.0f, false, ButtonType.BACK) {
			@Override
			public void click() {
				backPressed();
			}
		});
		
		buttonManager.add(new TextureButton(14.75f, 7.75f, 1.0f, false, ButtonType.OPTIONS) {
			@Override
			public void click() {
				nestedTicTacToe.setScreen(new OptionsScreenInGame(nestedTicTacToe));
			}
		});

		String[] moveStrings = {"MAKE", "MOVE"};
		makeMoveButton = new LayeredTextBoxButton(14.0f, 4.5f, moveStrings, 0.8f) {
			@Override
			public void click() {
				grid.makeMove();
				Save.saveGameState(grid.getGameState());
			}
		};
		makeMoveButton.setActive(false);
		buttonManager.add(makeMoveButton);

		againButton = new TextBoxButton(14.0f, 4.0f, "AGAIN!", 0.5f) {
			@Override
			public void click() {
				newGame();
			}
		};
		againButton.setActive(false);
		buttonManager.add(againButton);
	}

	private void newGame() {
		if(first == Side.CROSSES){
			nestedTicTacToe.setScreen(new GameScreen(nestedTicTacToe, crossesPlayer, noughtsPlayer, Side.NOUGHTS));
		} else {
			nestedTicTacToe.setScreen(new GameScreen(nestedTicTacToe, crossesPlayer, noughtsPlayer, Side.CROSSES));
		}
	}

	public void hide() {
		super.hide();
		Save.saveGameState(grid.getGameState());
	}

	@Override
	public void render(float delta) {
		spriteBatch.setProjectionMatrix(cam.combined);
		spriteBatch.begin();
		buttonManager.render(this);
		
		//Left info panel
		draw(grid.getCrossesPlayer().getElementTexture(), 2.0f, 6.2f, 1.0f, -1.0f, true);
		if(!grid.isFinished() && grid.getCurrentPlayer() == grid.getCrossesPlayer()){
			drawTextBox(0, 2.0f, 5.1f, grid.getCrossesPlayer().getName().length()*0.4f*1.2f, 0.4f*1.5f, true);
			drawString(grid.getCrossesPlayer().getName(), 2.0f, 5.1f, 0.4f, true, Art.textColor_1, invertPlayerTwo());
		} else {
			drawString(grid.getCrossesPlayer().getName(), 2.0f, 5.1f, 0.4f, true, Art.textColor_2, invertPlayerTwo());
		}
		drawString("vs", 2.0f, 4.5f, 0.4f, true, Art.textColor_2);
		if(!grid.isFinished() && grid.getCurrentPlayer() == grid.getNoughtsPlayer()){
			drawTextBox(0, 2.0f, 3.9f, grid.getNoughtsPlayer().getName().length()*0.4f*1.2f, 0.4f*1.5f, true);
			drawString(grid.getNoughtsPlayer().getName(), 2.0f, 3.9f, 0.4f, true, Art.textColor_1);
		} else {
			drawString(grid.getNoughtsPlayer().getName(), 2.0f, 3.9f, 0.4f, true, Art.textColor_2);
		}
		draw(grid.getNoughtsPlayer().getElementTexture(), 2.0f, 2.8f, 1.0f, -1.0f, true);
		
		//Centre (grid)
		grid.render(this);
		
		//Right info panel
		if (grid.isFinished()) {
			if (grid.getState() == GridState.WIN_CROSS) {
				
				if(invertPlayerTwo()){
					drawString("WINS!", 14.0f, 6.0f, 0.4f, true, Art.textColor_2, true);
					drawString(grid.getCrossesPlayer().getName(), 14.0f, 5.6f, 0.4f, true, Art.textColor_2, true);
				} else {
					drawString(grid.getCrossesPlayer().getName(), 14.0f, 6.0f, 0.4f, true, Art.textColor_2, false);
					drawString("WINS!", 14.0f, 5.6f, 0.4f, true, Art.textColor_2, false);
				}
				
			} else if (grid.getState() == GridState.WIN_NOUGHT) {
				drawString(grid.getNoughtsPlayer().getName(), 14.0f, 6.0f, 0.4f, true, Art.textColor_2);
				drawString("WINS!", 14.0f, 5.6f, 0.4f, true, Art.textColor_2);
			} else {
				drawString("NOBODY", 14.0f, 6.0f, 0.4f, true, Art.textColor_2);
				drawString("WINS!", 14.0f, 5.6f, 0.4f, true, Art.textColor_2);
			}
		} else if(grid.isWaitingOnAI() && aiMoveTimer > 0.1f){
			drawString("THINKING", 14.0f, 4.5f - (float)Math.sin(aiMoveTimer), 0.4f, true, Art.textColor_2);
		}

		spriteBatch.end();
	}

	@Override
	public void update(float deltaTime) {
		
		if(grid.isWaitingOnAI()){
			aiMoveTimer += deltaTime;
		} else {
			aiMoveTimer = 0.0f;
		}
		
		buttonManager.update(input);
		if (!grid.isFinished()) {
			grid.update(input);
			if (grid.isCellIsSelected()) {
				makeMoveButton.setActive(true);
			} else {
				makeMoveButton.setActive(false);
			}
		} else {
			againButton.setActive(true);
			makeMoveButton.setActive(false);
		}
		
		if(invertPlayerTwo() && grid.getCurrentPlayer() == grid.getCrossesPlayer()){
			makeMoveButton.setInvertText(true);
		} else {
			makeMoveButton.setInvertText(false);
		}
	}

	@Override
	public void backPressed() {
		if (grid.getGameState().gameStarted) {
			nestedTicTacToe.setScreen(new ConfirmBackScreen(nestedTicTacToe, this));
		} else {
			nestedTicTacToe.setScreen(new MenuScreen(nestedTicTacToe));
		}
	}
	
	private boolean invertPlayerTwo(){
		return (NestedPreferences.isInvertPlayerTwo() && noughtsPlayer.getAi() == null && crossesPlayer.getAi() == null);
	}

}
