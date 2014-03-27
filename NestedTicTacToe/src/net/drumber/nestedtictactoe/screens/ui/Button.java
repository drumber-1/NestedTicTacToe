package net.drumber.nestedtictactoe.screens.ui;

import net.drumber.nestedtictactoe.screens.BasicScreen;

import com.badlogic.gdx.math.Vector2;


public abstract class Button {

	protected Vector2 pos;
	protected float width;
	protected float height;
	protected boolean centre;
	
	private boolean active = true;

	protected boolean pointerOver = false; // True when pointer is over the button
	protected boolean clickLast = false; // True if button has been clicked in the last frame

	public abstract void click();

	public abstract void render(BasicScreen screen);

	public Button(float x, float y, float width, float height, boolean centre) {
		pos = new Vector2(x, y);
		this.width = width;
		this.height = height;
		this.centre = centre;
	}

	public boolean isInside(float x, float y) {
		if (!centre) {
			if (x > pos.x && x < pos.x + width && y > pos.y && y < pos.y + height) {
				return true;
			} else {
				return false;
			}
		} else {
			if (x > pos.x - width/2.0f && x < pos.x + width/2.0f && y > pos.y - height/2.0f && y < pos.y + height/2.0f) {
				return true;
			} else {
				return false;
			}
		}
	}

	public void setPointerOver(boolean pointerOver) {
		this.pointerOver = pointerOver;
	}

	public void setClickLast(boolean clickLast) {
		this.clickLast = clickLast;
	}

	public Vector2 getPosition() {
		return pos;
	}

	public void setPosition(float x, float y) {
		pos.set(x, y);
	}

	public void setPosition(Vector2 newPos) {
		pos.set(newPos);
	}
	
	public boolean isActive(){
		return active;
	}
	
	public void setActive(boolean active){
		this.active = active;
	}

}
