package net.drumber.nestedtictactoe.screens;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.NestedTicTacToe;
import net.drumber.nestedtictactoe.screens.ui.ButtonManager;
import net.drumber.nestedtictactoe.screens.ui.TextBoxButton;

public class ConfirmExitScreen extends OverlayScreen {

	ButtonManager buttonManager;

	public ConfirmExitScreen(NestedTicTacToe nestedTicTacToe, BasicScreen parent) {
		super(nestedTicTacToe, parent);
		buttonManager = new ButtonManager();
		buttonManager.add(new TextBoxButton(8.0f, 3.85f, "EXIT", 0.8f) {
			@Override
			public void click() {
				ConfirmExitScreen.this.nestedTicTacToe.exit();
			}
		});
		buttonManager.add(new TextBoxButton(8.0f, 2.55f, "CANCEL", 0.8f) {
			@Override
			public void click() {
				backPressed();
			}
		});
	}

	public void render(float delta) {
		super.render(delta);
		spriteBatch.begin();
		buttonManager.render(this);
		drawTextBox(0, 8.0f, 7.0f, 5.7f, 1.4f, true);
		drawString("Are you sure", 8.0f, 7.2f, 0.3f, true, Art.textColor_1);
		drawString("you want to exit?", 8.0f, 6.8f, 0.3f, true, Art.textColor_1);
		spriteBatch.end();
	}

	@Override
	public void update(float deltaTime) {
		buttonManager.update(input);
	}

	@Override
	public void backPressed() {
		setScreenToParent();
	}

}
