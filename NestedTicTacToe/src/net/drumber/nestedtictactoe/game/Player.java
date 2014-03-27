package net.drumber.nestedtictactoe.game;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.game.GameState.Element;
import net.drumber.nestedtictactoe.game.GameState.GridState;
import net.drumber.nestedtictactoe.game.GameState.Side;
import net.drumber.nestedtictactoe.game.ai.AI;

import com.badlogic.gdx.graphics.g2d.TextureRegion;


public abstract class Player {
	
	//public static Player noughts = new Player(Side.NOUGHTS);
	//public static Player crosses = new Player(Side.CROSSES);
	
	private final Side side;
	private final GridState winState;
	private final Element element;
	private final TextureRegion highLightTexture;
	private final TextureRegion elementTexture;
	
	private final AI ai;
	private final String name;
	
	protected Player(Side side, AI ai, String name){
		this.ai = ai;
		this.name = name;
		this.side = side;
		if(side == Side.CROSSES){
			winState = GridState.WIN_CROSS;
			element = Element.CROSS;
			highLightTexture = Art.cross_highlight;
			elementTexture = Art.cross;
		} else {
			winState = GridState.WIN_NOUGHT;
			element = Element.NOUGHT;
			highLightTexture = Art.nought_highlight;
			elementTexture = Art.nought;
		}
	}

	public Side getSide() {
		return side;
	}

	public AI getAi() {
		return ai;
	}
	
	public String getName(){
		return name;
	}
	
	public GridState getWinState() {
		return winState;
	}

	public Element getElement() {
		return element;
	}

	public TextureRegion getHighLightTexture() {
		return highLightTexture;
	}

	public TextureRegion getElementTexture() {
		return elementTexture;
	}

}
