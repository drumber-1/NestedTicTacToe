package net.drumber.nestedtictactoe.screens.ui;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.screens.BasicScreen;

public abstract class TextButton extends Button {
	
	protected final String text;
	protected final float scale;

	public TextButton(float x, float y, String text, float scale, boolean centre) {
		super(x, y, text.length() * scale, scale, centre);
		this.text = text;
		this.scale = scale;
	}

	@Override
	public void render(BasicScreen screen) {
		if(pointerOver){
			screen.drawString(text, pos.x, pos.y, scale*1.2f, false, Art.textColor_1);
		} else {
			screen.drawString(text, pos.x, pos.y, scale, false, Art.textColor_1);
		}
	}

}
