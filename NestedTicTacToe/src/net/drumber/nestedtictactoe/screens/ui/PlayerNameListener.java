package net.drumber.nestedtictactoe.screens.ui;

import net.drumber.nestedtictactoe.NestedPreferences;
import net.drumber.nestedtictactoe.game.GameState.Side;

import com.badlogic.gdx.Input.TextInputListener;

public class PlayerNameListener implements TextInputListener {
	
	private static int MAXLENGTH = 10;
	private Side side;
	
	public PlayerNameListener(Side side){
		this.side = side;
	}

	@Override
	public void input(String text) {
		text = text.trim();
		if(text.length() > MAXLENGTH){
			text = text.substring(0, MAXLENGTH);
		}
		if(side == Side.CROSSES){
			NestedPreferences.setCrossesName(text);
		} else {
			NestedPreferences.setNoughtsName(text);
		}
	}

	@Override
	public void canceled() {
	}

}
