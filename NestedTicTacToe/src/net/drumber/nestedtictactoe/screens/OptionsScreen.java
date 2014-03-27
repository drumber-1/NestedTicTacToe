package net.drumber.nestedtictactoe.screens;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.NestedPreferences;
import net.drumber.nestedtictactoe.NestedTicTacToe;
import net.drumber.nestedtictactoe.Art.Theme;
import net.drumber.nestedtictactoe.screens.ui.ButtonManager;
import net.drumber.nestedtictactoe.screens.ui.TextureButton;
import net.drumber.nestedtictactoe.screens.ui.ToggleButton;
import net.drumber.nestedtictactoe.screens.ui.TextureButton.ButtonType;

public class OptionsScreen extends BasicScreen {
	
	protected ButtonManager buttonManager;

	public OptionsScreen(NestedTicTacToe nestedTicTacToe) {
		super(nestedTicTacToe);
		buttonManager = new ButtonManager();
		
		buttonManager.add(new TextureButton(0.25f, 7.75f, 1.0f, false, ButtonType.BACK) {
			@Override
			public void click() {
				backPressed();
			}
		});
		
		String[] moveStrings = {"OFF", "ON"};
		int nShowMove = 0;
		if(NestedPreferences.isShowNextMove()){
			nShowMove = 1;
		} else {
			nShowMove = 0;
		}	
		buttonManager.add(new ToggleButton(12.5f, 5.2f, moveStrings, 0.8f, nShowMove) {
			
			@Override
			public void click(int stateClicked, int newState) {
				if(newState == 0){
					NestedPreferences.setShowNextMove(false);
				} else {
					NestedPreferences.setShowNextMove(true);
				}
			}
		});
		
		String[] themeStrings = {"LIGHT", "DARK"};
		int nTheme = 0;
		if(NestedPreferences.getTheme().equals("light")){
			nTheme = 0;
		} else {
			nTheme = 1;
		}	
		buttonManager.add(new ToggleButton(12.5f, 4.0f, themeStrings, 0.8f, nTheme) {
			
			@Override
			public void click(int stateClicked, int newState) {
				if(newState == 0){
					NestedPreferences.setTheme("light");
					Art.load(Theme.LIGHT);
				} else {
					NestedPreferences.setTheme("dark");
					Art.load(Theme.DARK);
				}
			}
		});
		
		String[] invertStrings = {"ON", "OFF"};
		int nInvert = 0;
		if(NestedPreferences.isInvertPlayerTwo()){
			nInvert = 0;
		} else {
			nInvert = 1;
		}	
		buttonManager.add(new ToggleButton(12.5f, 2.8f, invertStrings, 0.8f, nInvert) {
			
			@Override
			public void click(int stateClicked, int newState) {
				if(newState == 0){
					NestedPreferences.setInvertPlayerTwo(true);
				} else {
					NestedPreferences.setInvertPlayerTwo(false);
				}
			}
		});
		
		String[] magicStrings = {"MAGIC", "MORE MAGIC"};
		int nMagic = 0;
		if(NestedPreferences.isMoreMagic()){
			nMagic = 1;
		} else {
			nMagic = 0;
		}			
		buttonManager.add(new ToggleButton(8.0f, 1.6f, magicStrings, 0.8f, nMagic){
			@Override
			public void click(int stateClicked, int newState) {
				if(newState == 0){
					NestedPreferences.setMoreMagic(false);
				} else {
					NestedPreferences.setMoreMagic(true);
				}
			}
		});

	}

	@Override
	public void render(float delta) {
		spriteBatch.begin();
		draw(Art.title_options, 8.0f, 7.0f, 6.0f, -1.0f, true);
		drawString("SHOW NEXT MOVE -", 6.0f, 5.2f, 0.5f, true, Art.textColor_2);
		drawString("THEME -", 8.25f, 4.0f, 0.5f, true, Art.textColor_2);
		drawString("2P INVERT -", 7.25f, 2.8f, 0.5f, true, Art.textColor_2);
		buttonManager.render(this);
		spriteBatch.end();
	}

	@Override
	public void update(float deltaTime) {
		buttonManager.update(input);
	}

	@Override
	public void backPressed() {
		nestedTicTacToe.setScreen(new MenuScreen(nestedTicTacToe));
	}

}
