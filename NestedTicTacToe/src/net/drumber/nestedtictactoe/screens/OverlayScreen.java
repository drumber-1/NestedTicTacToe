package net.drumber.nestedtictactoe.screens;

import net.drumber.nestedtictactoe.NestedTicTacToe;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public abstract class OverlayScreen extends BasicScreen {
	
	protected TextureRegion parentMask;
	protected final BasicScreen parent; // This screen will be set when the back button is pressed

	public OverlayScreen(NestedTicTacToe nestedTicTacToe, BasicScreen parent) {
		super(nestedTicTacToe);
		this.parent = parent;
		
		//Create mask to grey out background
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(0.0f, 0.0f, 0.0f, 0.8f);
		pixmap.fill();
		parentMask = new TextureRegion(new Texture(pixmap));
	}

	@Override
	public void render(float delta) {
		parent.render(delta);
		spriteBatch.begin();
		draw(parentMask, 0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		spriteBatch.end();
	}
	
	public BasicScreen getParent() {
		return parent;
	}

	public void setScreenToParent() {
		nestedTicTacToe.setScreen(parent);
	}
	
	protected void hideParent(){
		parent.hide();
	}

}
