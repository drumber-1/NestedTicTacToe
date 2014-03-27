package net.drumber.nestedtictactoe.screens.ui;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.screens.BasicScreen;

public abstract class LayeredTextBoxButton extends TextBoxButton {

	private int rows;
	private String[] strings;

	public LayeredTextBoxButton(float x, float y, String[] strings, float scale) {
		super(x, y, longestString(strings), scale);
		this.rows = strings.length;
		this.strings = strings;
		this.height *= rows;
	}

	@Override
	public void render(BasicScreen screen) {
		int textBoxStyle;
		float scaleMod;
		if (pointerOver) {
			textBoxStyle = 1;
			scaleMod = 0.8f;
		} else {
			textBoxStyle = 0;
			scaleMod = 0.7f;
		}

		screen.drawTextBox(textBoxStyle, pos.x, pos.y, width, height, centre);
		for(int i = 0; i < rows; i++){
			//screen.drawString(strings[i], pos.x, pos.y + scale*1.2f*(rows - 0.5f + i), scale * scaleMod, centre, Art.textColor_1, invertText);
			if(invertText){
				screen.drawString(strings[rows - i - 1], pos.x, pos.y + scale*0.8f*(0.5f - i), scale * scaleMod, centre, Art.textColor_1, true);				
			} else {
				screen.drawString(strings[i], pos.x, pos.y + scale*0.8f*(0.5f - i), scale * scaleMod, centre, Art.textColor_1, false);				
			}
		}
	}

	private static String longestString(String[] strings) {
		int max = 0;
		String maxString = "X";
		for (String s : strings) {
			if (s.length() >= max) {
				max = s.length();
				maxString = s;
			}
		}
		return maxString;
	}

}
