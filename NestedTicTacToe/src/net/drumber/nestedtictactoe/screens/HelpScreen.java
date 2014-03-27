package net.drumber.nestedtictactoe.screens;

import net.drumber.nestedtictactoe.Art;
import net.drumber.nestedtictactoe.NestedTicTacToe;
import net.drumber.nestedtictactoe.game.CrossesPlayer;
import net.drumber.nestedtictactoe.game.Grid;
import net.drumber.nestedtictactoe.game.NoughtsPlayer;
import net.drumber.nestedtictactoe.game.GameState.Side;
import net.drumber.nestedtictactoe.game.ai.MinMaxAI;
import net.drumber.nestedtictactoe.screens.ui.Button;
import net.drumber.nestedtictactoe.screens.ui.ButtonManager;
import net.drumber.nestedtictactoe.screens.ui.TextBoxButton;
import net.drumber.nestedtictactoe.screens.ui.TextureButton;
import net.drumber.nestedtictactoe.screens.ui.TextureButton.ButtonType;

import com.badlogic.gdx.math.Vector2;

public class HelpScreen extends BasicScreen {
	
	private static final float TEXT_XMIN = 0.75f;
	private static final float TEXT_XMAX = 7.0f;
	private static final float TEXT_YMIN = 0.0f;
	private static final float TEXT_YMAX = 9.0f;
	
	protected ButtonManager buttonManager;
	private Button moveButton;
	
	private Grid grid;
	
	private float textPos = 0.0f;
	private float textFocal = 0.0f; //Text offset focal point for hooks law
	
	private boolean startMoveMade = false;
	
	private String[] strings;
	
	public HelpScreen(NestedTicTacToe nestedTicTacToe) {
		super(nestedTicTacToe);
		
		grid = new Grid(7.5f,0.5f, new CrossesPlayer("", new MinMaxAI(0)), new NoughtsPlayer("", null), Side.CROSSES);
		JsonToStrings jts = new JsonToStrings("tutorialstrings.json");
		strings = jts.strings;
		
		buttonManager = new ButtonManager();
		buttonManager.add(new TextureButton(0.25f, 7.75f, 1.0f, false, ButtonType.BACK) {
			@Override
			public void click() {
				backPressed();
			}
		});
		
		moveButton = new TextBoxButton(4.5f, 1.6f - 9.0f, "MAKE MOVE", 0.6f) {
			@Override
			public void click() {
				grid.makeMove();
			}
		};
		
		buttonManager.add(moveButton);
	}

	@Override
	public void render(float delta) {
		spriteBatch.begin();
		
		grid.render(this);
		
		draw(Art.ring, 0.375f, 5.0f, 0.4f, 0.4f, true);
		draw(Art.ring, 0.375f, 4.0f, 0.4f, 0.4f, true);
		draw(Art.dot, 0.375f, 5.0f - textPos/9.0f, 0.4f, 0.4f, true);
		
		//Page one
		drawString(strings[0], 0.75f, 7.0f + textPos, 0.35f, false, Art.textColor_2, 18);
		drawString(strings[1], 0.75f, 6.0f + textPos, 0.35f, false, Art.textColor_2, 18);
		drawString(strings[2], 0.75f, 4.65f + textPos, 0.35f, false, Art.textColor_2, 18);
		drawString(strings[3], 0.75f, 3.3f + textPos, 0.35f, false, Art.textColor_2, 18);
		drawString(strings[4], 0.75f, 1.95f + textPos, 0.35f, false, Art.textColor_2, 18);
		
		//Page two
		drawString(strings[5], 0.75f, 7.0f - 9.0f + textPos, 0.35f, false, Art.textColor_2, 18);
		drawString(strings[6], 0.75f, 5.65f - 9.0f + textPos, 0.35f, false, Art.textColor_2, 18);
		drawString(strings[7], 0.75f, 4.3f - 9.0f + textPos, 0.35f, false, Art.textColor_2, 18);
		drawString(strings[8], 0.75f, 2.6f - 9.0f + textPos, 0.35f, false, Art.textColor_2, 18);
		
		buttonManager.render(this);
		spriteBatch.end();
	}

	@Override
	public void update(float deltaTime) {
		buttonManager.update(input);
		if(startMoveMade){
			grid.update(input);
		}
		
		if(input.isPointerDown() && inTextBox(input.getPointerLocationFirst())){
			textPos = input.getPointerDrag().y*3.0f + textFocal;
		}
		if(input.isPointerUpLast() && inTextBox(input.getPointerLocationFirst())){
			textFocal = Math.round((textPos/9.0f))*9.0f;
			if(textFocal < 0.0f){
				textFocal = 0.0f;
			} else if(textFocal > 9.0f){
				textFocal = 9.0f;
			}
		}
		
		float dy = textPos - textFocal;
		textPos -= dy*0.1f;
		if(textPos < 0.0f){
			textPos -= (textPos - 0.0f)*0.95f;
		} else if(textPos > 9.0f){
			textPos -= (textPos - 9.0f)*0.95f;
		}
		
		if(!startMoveMade && textFocal == 9.0f){
			startMoveMade = true;
			grid.makeMove(3, 3);
		}
		
		moveButton.setPosition(3.6f, 1.0f - 9.0f + textPos);
		
	}

	@Override
	public void backPressed() {
		nestedTicTacToe.setScreen(new MenuScreen(nestedTicTacToe));
	}
	
	private boolean inTextBox(Vector2 pos){
		if(pos.x > TEXT_XMIN && pos.x < TEXT_XMAX && pos.y > TEXT_YMIN && pos.y < TEXT_YMAX){
			return true;
		} else {
			return false;
		}
	}

}
