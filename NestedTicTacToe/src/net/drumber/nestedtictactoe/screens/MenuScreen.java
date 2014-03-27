package net.drumber.nestedtictactoe.screens;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.NestedTicTacToe;
import net.drumber.nestedtictactoe.Save;
import net.drumber.nestedtictactoe.screens.ui.ButtonManager;
import net.drumber.nestedtictactoe.screens.ui.TextBoxButton;
import net.drumber.nestedtictactoe.screens.ui.TextureButton;
import net.drumber.nestedtictactoe.screens.ui.TextureButton.ButtonType;

public class MenuScreen extends BasicScreen {

	protected ButtonManager buttonManager;

	public MenuScreen(NestedTicTacToe nestedTicTacToe) {
		super(nestedTicTacToe);
		buttonManager = new ButtonManager();

		buttonManager.add(new TextBoxButton(8.0f, 4.5f, "START", 1.0f) {
			@Override
			public void click() {
				startPressed();
			}
		});

		buttonManager.add(new TextBoxButton(8.0f, 3.2f, "OPTIONS", 1.0f) {
			@Override
			public void click() {
				optionsPressed();
			}
		});

		buttonManager.add(new TextBoxButton(8.0f, 1.9f, "HELP", 1.0f) {
			@Override
			public void click() {
				helpPressed();
			}
		});

		buttonManager.add(new TextureButton(0.25f, 7.75f, 1.0f, false, ButtonType.EXIT) {
			@Override
			public void click() {
				backPressed();
			}
		});
	}

	public void show() {
		super.show();
	}

	@Override
	public void render(float delta) {
		spriteBatch.begin();
		draw(Art.title, 4.0f, 6.0f, 8.0f, -1.0f);
		buttonManager.render(this);
		spriteBatch.end();
	}

	@Override
	public void update(float deltaTime) {
		buttonManager.update(input);
	}

	@Override
	public void backPressed() {
		nestedTicTacToe.setScreen(new ConfirmExitScreen(nestedTicTacToe, this));
	}
	
	private void startPressed(){
		if (Save.saveExists()) {
			nestedTicTacToe.setScreen(new ConfirmLoadScreen(nestedTicTacToe, this));
		} else {
			nestedTicTacToe.setScreen(new OpponentSelectScreen(nestedTicTacToe));
		}
	}
	
	private void optionsPressed(){
		nestedTicTacToe.setScreen(new OptionsScreen(nestedTicTacToe));

	}
	
	private void helpPressed(){
		nestedTicTacToe.setScreen(new HelpScreen(nestedTicTacToe));
	}

}
