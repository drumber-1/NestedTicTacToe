package net.drumber.nestedtictactoe.screens;

import java.io.IOException;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.NestedTicTacToe;
import net.drumber.nestedtictactoe.screens.ui.ButtonManager;
import net.drumber.nestedtictactoe.screens.ui.TextBoxButton;


public class ConfirmLoadScreen extends OverlayScreen {
	
	ButtonManager buttonManager;

	public ConfirmLoadScreen(NestedTicTacToe nestedTicTacToe, BasicScreen parent) {
		super(nestedTicTacToe, parent);
		buttonManager = new ButtonManager();
		buttonManager.add(new TextBoxButton(8.0f, 4.5f, "RESUME GAME", 0.8f) {
			@Override
			public void click() {
				resumePressed();
				hideParent();
			}
		});
		buttonManager.add(new TextBoxButton(8.0f, 3.2f, "NEW GAME", 0.8f) {
			@Override
			public void click() {
				newGamePressed();
				hideParent();
			}
		});
		buttonManager.add(new TextBoxButton(8.0f, 1.9f, "CANCEL", 0.8f) {
			@Override
			public void click() {
				backPressed();
			}
		});
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		spriteBatch.begin();
		buttonManager.render(this);
		drawTextBox(0, 8.0f, 7.0f, 8.2f, 2.1f, true);
		drawString("Resume game?", 8.0f, 7.5f, 0.3f, true, Art.textColor_1);
		drawString("Starting a new game will", 8.0f, 7.0f, 0.3f, true, Art.textColor_1);
		drawString("erase previous progress", 8.0f, 6.5f, 0.3f, true, Art.textColor_1);

		spriteBatch.end();

	}

	@Override
	public void update(float deltaTime) {
		buttonManager.update(input);
	}

	@Override
	public void backPressed() {
		nestedTicTacToe.setScreen(parent);
	}
	
	private void resumePressed(){
		try {
			nestedTicTacToe.setScreen(new GameScreen(nestedTicTacToe));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void newGamePressed(){
		nestedTicTacToe.setScreen(new OpponentSelectScreen(nestedTicTacToe));
	}

}
