package net.drumber.nestedtictactoe.screens;

import java.io.IOException;

import net.drumber.nestedtictactoe.NestedTicTacToe;


//Same as optionscreen, but loads game back up when left

public class OptionsScreenInGame extends OptionsScreen {

	public OptionsScreenInGame(NestedTicTacToe nestedTicTacToe) {
		super(nestedTicTacToe);
	}
	
	public void backPressed() {
		try {
			nestedTicTacToe.setScreen(new GameScreen(nestedTicTacToe));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
