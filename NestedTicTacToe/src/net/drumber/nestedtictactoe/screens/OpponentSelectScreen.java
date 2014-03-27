package net.drumber.nestedtictactoe.screens;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.NestedPreferences;
import net.drumber.nestedtictactoe.NestedTicTacToe;
import net.drumber.nestedtictactoe.game.CrossesPlayer;
import net.drumber.nestedtictactoe.game.NoughtsPlayer;
import net.drumber.nestedtictactoe.game.GameState.Side;
import net.drumber.nestedtictactoe.game.ai.AI;
import net.drumber.nestedtictactoe.game.ai.MinMaxAI;
import net.drumber.nestedtictactoe.game.ai.RandomAI;
import net.drumber.nestedtictactoe.screens.ui.ButtonManager;
import net.drumber.nestedtictactoe.screens.ui.PlayerNameListener;
import net.drumber.nestedtictactoe.screens.ui.TextBoxButton;
import net.drumber.nestedtictactoe.screens.ui.TextureButton;
import net.drumber.nestedtictactoe.screens.ui.ToggleButton;
import net.drumber.nestedtictactoe.screens.ui.TextureButton.ButtonType;

import com.badlogic.gdx.Gdx;


public class OpponentSelectScreen extends BasicScreen {
	
	private static int MAXAILEVEL = 6;
	
	private static String[] AINAMES = {"AI 0", "AI 1", "AI 2", "AI 3", "AI 4", "AI 5", "AI 6"};

	private ButtonManager buttonManager;
	private ButtonManager crossesButtonsHuman;
	private ButtonManager crossesButtonsAI;
	private ButtonManager noughtsButtonsHuman;
	private ButtonManager noughtsButtonsAI;
	
	boolean crossesIsAI;
	boolean noughtsIsAI;
	
	int crossesAILevel;
	int noughtsAILevel;
	
	String crossesName;
	String noughtsName;
	
	public OpponentSelectScreen(NestedTicTacToe nestedTicTacToe) {
		super(nestedTicTacToe);
		
		crossesIsAI = NestedPreferences.isCrossesAI();
		noughtsIsAI = NestedPreferences.isNoughtsAI();
		crossesAILevel = NestedPreferences.getCrossesAILevel();
		noughtsAILevel = NestedPreferences.getNoughtsAILevel();
		crossesName = NestedPreferences.getCrossesName();
		noughtsName = NestedPreferences.getNoughtsName();
		
		
		buttonManager = new ButtonManager();
		crossesButtonsHuman = new ButtonManager();
		crossesButtonsAI = new ButtonManager();
		noughtsButtonsHuman = new ButtonManager();
		noughtsButtonsAI = new ButtonManager();
		String[] playerStrings = {"HUMAN","AI"};
		
		
		buttonManager.add(new TextureButton(0.25f, 7.75f, 1.0f, false, ButtonType.BACK) {
			@Override
			public void click() {
				backPressed();
			}
		});
		
		buttonManager.add(new ToggleButton(3.5f, 5.0f, playerStrings, 0.8f, boolToInt(crossesIsAI)) {
			@Override
			public void click(int stateClicked, int newState) {
				if(newState == 0){
					NestedPreferences.setCrossesAI(false);
					crossesIsAI = false;
				} else {
					NestedPreferences.setCrossesAI(true);
					crossesIsAI = true;
				}
			}
		});
		
		buttonManager.add(new ToggleButton(12.5f, 5.0f, playerStrings, 0.8f, boolToInt(NestedPreferences.isNoughtsAI())) {
			@Override
			public void click(int stateClicked, int newState) {
				if(newState == 0){
					NestedPreferences.setNoughtsAI(false);
					noughtsIsAI = false;
				} else {
					NestedPreferences.setNoughtsAI(true);
					noughtsIsAI = true;
				}
			}
		});
		
		buttonManager.add(new TextBoxButton(8.0f, 1.0f, "BEGIN", 1.0f) {
			@Override
			public void click() {
				startGame();
			}
		});
		
		crossesButtonsAI.add(new TextureButton(2.5f, 2.5f, 0.7f, true, ButtonType.LEFT) {
			@Override
			public void click() {
				if(crossesAILevel > 0){
					crossesAILevel--;
					NestedPreferences.setCrossesAILevel(crossesAILevel);
				}
			}
		});
		crossesButtonsAI.add(new TextureButton(4.5f, 2.5f, 0.7f, true, ButtonType.RIGHT) {
			@Override
			public void click() {
				if(crossesAILevel < MAXAILEVEL){
					crossesAILevel++;
					NestedPreferences.setCrossesAILevel(crossesAILevel);
				}
			}
		});
		
		noughtsButtonsAI.add(new TextureButton(11.5f, 2.5f, 0.7f, true, ButtonType.LEFT) {
			@Override
			public void click() {
				if(noughtsAILevel > 0){
					noughtsAILevel--;
					NestedPreferences.setNoughtsAILevel(noughtsAILevel);
				}
			}
		});
		noughtsButtonsAI.add(new TextureButton(13.5f, 2.5f, 0.7f, true, ButtonType.RIGHT) {
			@Override
			public void click() {
				if(noughtsAILevel < MAXAILEVEL){
					noughtsAILevel++;
					NestedPreferences.setNoughtsAILevel(noughtsAILevel);
				}
			}
		});
		
		crossesButtonsHuman.add(new TextureButton(3.5f, 2.35f, 6.0f, 0.6f, true, ButtonType.UNDERLINE) {
			@Override
			public void click() {
				PlayerNameListener listener = new PlayerNameListener(Side.CROSSES);
				Gdx.input.getTextInput(listener, "Enter player name", NestedPreferences.getCrossesName());
			}
		});
		
		noughtsButtonsHuman.add(new TextureButton(12.5f, 2.35f, 6.0f, 0.6f, true, ButtonType.UNDERLINE) {
			@Override
			public void click() {
				PlayerNameListener listener = new PlayerNameListener(Side.NOUGHTS);
				Gdx.input.getTextInput(listener, "Enter player name", NestedPreferences.getNoughtsName());
			}
		});
		
	}
	
	private void startGame(){
		AI crossesAI;
		AI noughtsAI;
		String crossesName;
		String noughtsName;
		
		if(NestedPreferences.isNoughtsAI()){
			int aiLevel = NestedPreferences.getNoughtsAILevel();
			if(aiLevel == 0){
				noughtsAI = new RandomAI();
			} else {
				noughtsAI = new MinMaxAI(aiLevel - 1);
			}
			noughtsName = AINAMES[aiLevel];
		} else {
			noughtsAI = null;
			noughtsName = NestedPreferences.getNoughtsName();
		}
		
		if(NestedPreferences.isCrossesAI()){
			int aiLevel = NestedPreferences.getCrossesAILevel();
			if(aiLevel == 0){
				crossesAI = new RandomAI();
			} else {
				crossesAI = new MinMaxAI(aiLevel - 1);
			}
			crossesName = AINAMES[aiLevel];
		} else {
			crossesAI = null;
			crossesName = NestedPreferences.getCrossesName();
		}
		
		nestedTicTacToe.setScreen(new GameScreen(nestedTicTacToe, new CrossesPlayer(crossesName, crossesAI), new NoughtsPlayer(noughtsName, noughtsAI), Side.CROSSES));
	}
	
	private int boolToInt(boolean b){
		if(b){
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public void render(float delta) {
		spriteBatch.begin();
		draw(Art.title_opponent, 8.0f, 6.5f, 5.0f, -1.0f, true);
		buttonManager.render(this);
		draw(Art.cross, 3.5f, 6.5f, 1.5f, -1.0f, true);
		draw(Art.nought, 12.5f, 6.5f, 1.5f, -1.0f, true);
		if(crossesIsAI){
			crossesButtonsAI.render(this);
			drawString("LEVEL:", 3.5f, 3.75f, 0.7f, true, Art.textColor_2);
			drawString(Integer.toString(crossesAILevel), 3.5f, 2.5f, 0.7f, true, Art.textColor_2);
		} else {
			crossesButtonsHuman.render(this);
			drawString("NAME:", 3.5f, 3.75f, 0.7f, true, Art.textColor_2);
			drawString(crossesName, 3.5f, 2.5f, 0.6f, true, Art.textColor_2);
		}
		if(noughtsIsAI){
			noughtsButtonsAI.render(this);
			drawString("LEVEL:", 12.5f, 3.75f, 0.7f, true, Art.textColor_2);
			drawString(Integer.toString(noughtsAILevel), 12.5f, 2.5f, 0.7f, true, Art.textColor_2);
		} else {
			noughtsButtonsHuman.render(this);
			drawString("NAME:", 12.5f, 3.75f, 0.7f, true, Art.textColor_2);
			drawString(noughtsName, 12.5f, 2.5f, 0.6f, true, Art.textColor_2);
		}
		spriteBatch.end();
	}

	@Override
	public void update(float deltaTime) {
		//Might have been changed so update them
		crossesName = NestedPreferences.getCrossesName();
		noughtsName = NestedPreferences.getNoughtsName();
		
		buttonManager.update(input);
		if(noughtsIsAI){
			noughtsButtonsAI.update(input);
		} else {
			noughtsButtonsHuman.update(input);
		}
		if(crossesIsAI){
			crossesButtonsAI.update(input);		
		} else {
			crossesButtonsHuman.update(input);
		}
	}

	@Override
	public void backPressed() {
		nestedTicTacToe.setScreen(new MenuScreen(nestedTicTacToe));
	}

}
