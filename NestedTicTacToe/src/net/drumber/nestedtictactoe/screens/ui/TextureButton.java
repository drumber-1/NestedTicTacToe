package net.drumber.nestedtictactoe.screens.ui;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.screens.BasicScreen;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public abstract class TextureButton extends Button {
	
	public enum ButtonType {
		BACK(Art.back_button),
		OPTIONS(Art.options_button),
		EXIT(Art.exit_button),
		CONFIRM(Art.confirm_button),
		CANCEL(Art.cancel_button),
		LEFT(Art.left_button),
		RIGHT(Art.right_button),
		UNDERLINE(Art.underline);
		
		private TextureRegion[] textures;
		
		private ButtonType(TextureRegion[] textures){
			this.textures = textures;
		}
		
		//If only given one texture, use it for both states
		private ButtonType(TextureRegion texture){
			this.textures = new TextureRegion[2];
			this.textures[0] = texture;
			this.textures[1] = texture;
		}
		
		public TextureRegion getTexture(int i){
			return textures[i];
		}
	}
	
	//protected final float scale;
	private final ButtonType bt;

	public TextureButton(float x, float y, float scale, boolean centre, ButtonType bt) {
		super(x, y, bt.getTexture(0).getRegionWidth() * scale/ bt.getTexture(0).getRegionHeight(), scale, centre);
		//this.scale = scale;
		this.bt = bt;
	}
	
	public TextureButton(float x, float y, float width, float height, boolean centre, ButtonType bt) {
		super(x, y, width, height, centre);
		//this.scale = scale;
		this.bt = bt;
	}

	@Override
	public void render(BasicScreen screen) {
		if(pointerOver){
			screen.draw(bt.getTexture(0), pos.x, pos.y, width, height, centre);
		} else {
			screen.draw(bt.getTexture(1), pos.x, pos.y, width, height, centre);
		}
	}

}
