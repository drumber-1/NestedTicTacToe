package net.drumber.nestedtictactoe.screens.ui;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.screens.BasicScreen;

public abstract class TextBoxButton extends Button {
	
	protected String text;
	protected final float scale;
	protected boolean invertText = false;

	public TextBoxButton(float x, float y, String text, float scale) {
		super(x, y, text.length()*scale*0.9f, scale*1.2f, true);
		this.text = text;
		this.scale = scale;
	}

	@Override
	public void render(BasicScreen screen) {
		if(pointerOver){
			screen.drawTextBox(1, pos.x, pos.y, width, height, centre);
			screen.drawString(text, pos.x, pos.y, scale * 0.8f, centre, Art.textColor_1, invertText);
		} else {
			screen.drawTextBox(0, pos.x, pos.y, width, height, centre);
			screen.drawString(text, pos.x, pos.y, scale * 0.7f, centre, Art.textColor_1, invertText);
		}
	}
	
	public void setInvertText(boolean invertText){
		this.invertText = invertText;
	}

}
