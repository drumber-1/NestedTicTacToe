package net.drumber.nestedtictactoe;

import net.drumber.nestedtictactoe.Art.Theme;
import net.drumber.nestedtictactoe.game.StateLookUp;
import net.drumber.nestedtictactoe.screens.BasicScreen;
import net.drumber.nestedtictactoe.screens.MenuScreen;
import net.drumber.nestedtictactoe.screens.OverlayScreen;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL10;

public class NestedTicTacToe implements ApplicationListener {

	private BasicScreen screen;

	@Override
	public void create() {
		
		Art.init();
		if (NestedPreferences.getTheme().equals("light")) {
			Art.load(Theme.LIGHT);
		} else {
			Art.load(Theme.DARK);
		}
		StateLookUp.load();
		setScreen(new MenuScreen(this));
	}

	@Override
	public void render() {
		Gdx.gl.glClearColor(Art.backgroundColor.r, Art.backgroundColor.g, Art.backgroundColor.b, Art.backgroundColor.a);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		if (getScreen() != null) {
			if (screen.getInput().isBackPressedLast()) {
				screen.backPressed();
			}
			screen.update(Gdx.graphics.getDeltaTime());
			screen.render(Gdx.graphics.getDeltaTime());
			screen.resetInput();
		}
	}

	@Override
	public void dispose() {
		if (screen != null) {
			screen.hide();
		}
	}

	@Override
	public void pause() {
		if (screen != null) {
			screen.pause();
		}
	}

	@Override
	public void resume() {
		if (screen != null) {
			screen.resume();
		}
	}

	@Override
	public void resize(int width, int height) {
		if (screen != null) {
			screen.resize(width, height);
		}
	}

	public void setScreen(BasicScreen newScreen) {
		if (newScreen == null) {
			return;
		} else {
			if (!(newScreen instanceof OverlayScreen) && this.screen != null) { // Moving to an overlay
				this.screen.hide();
			}
			this.screen = newScreen;
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}
	
	public void exit() {
		Gdx.app.exit();
	}

	public BasicScreen getScreen() {
		return screen;
	}

}
