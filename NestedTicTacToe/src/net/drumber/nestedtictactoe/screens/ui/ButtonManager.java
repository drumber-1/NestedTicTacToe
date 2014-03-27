package net.drumber.nestedtictactoe.screens.ui;

import java.util.ArrayList;

import net.drumber.nestedtictactoe.Input;
import net.drumber.nestedtictactoe.screens.BasicScreen;

import com.badlogic.gdx.math.Vector2;


public class ButtonManager {

	protected ArrayList<Button> buttons = new ArrayList<Button>();

	public ButtonManager() {}

	public void add(Button button) {
		buttons.add(button);
	}

	public void render(BasicScreen screen) {
		for (Button b : buttons) {
			if(b.isActive()) {
				b.render(screen);
			}
		}
	}

	public void update(Input input) {
		this.reset();
		Vector2 pos = new Vector2(input.getPointerLocation());
		if (input.isPointerDown() || input.isPointerUpLast()) {
			for (Button b : buttons) {
				if (b.isActive() && b.isInside(pos.x, pos.y)) {
					if (input.isPointerDown()) {
						b.setPointerOver(true);
					} else if (input.isPointerUpLast()) {
						b.click();
						b.setClickLast(true);
					}
					break; // Assume we can't click on two buttons at once
				}
			}
		}
	}

	private void reset() {
		for (Button b : buttons) {
			b.setPointerOver(false);
			b.setClickLast(false);
		}
	}
	
	public Button getButton(int n){
		return buttons.get(n);
	}

}
