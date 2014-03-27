package net.drumber.nestedtictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Art {

	public static Color backgroundColor;
	public static Color textColor_1;
	public static Color textColor_2;
	
	public static TextureRegion[] chars;

	public static TextureRegion title;
	public static TextureRegion title_help;
	public static TextureRegion title_opponent;
	public static TextureRegion title_options;

	public static TextureRegion nought;
	public static TextureRegion cross;
	public static TextureRegion grid;
	public static TextureRegion nought_highlight;
	public static TextureRegion cross_highlight;
	public static TextureRegion yellow_highlight;
	public static TextureRegion yellow_highlight_large;

	public static TextureRegion underline;
	
	public static TextureRegion ring;
	public static TextureRegion dot;
	
	public static TextureRegion[] textbox;
	public static TextureRegion[] textbox_end_left;
	public static TextureRegion[] textbox_end_right;
	
	public static TextureRegion[] exit_button;
	public static TextureRegion[] options_button;
	public static TextureRegion[] back_button;

	public static TextureRegion[] confirm_button;
	public static TextureRegion[] cancel_button;
	public static TextureRegion[] left_button;
	public static TextureRegion[] right_button;
	
	
	public enum Theme {
		LIGHT, DARK
	}
	
	public static void init(){
		backgroundColor = new Color();
		textColor_1 = new Color();
		textColor_2 = new Color();

		chars = split("light/ui/chars.png", 8, 8);

		title = new TextureRegion();
		title_help =  new TextureRegion();
		title_opponent =  new TextureRegion();
		title_options =  new TextureRegion();

		nought =  new TextureRegion();
		cross =  new TextureRegion();
		grid =  new TextureRegion();
		nought_highlight =  new TextureRegion();
		cross_highlight =  new TextureRegion();
		yellow_highlight =  new TextureRegion();
		yellow_highlight_large =  new TextureRegion();
		
		underline = new TextureRegion();
		
		ring = new TextureRegion();
		dot = new TextureRegion();

		textbox = new TextureRegion[2];
		textbox[0] =  new TextureRegion();
		textbox[1] =  new TextureRegion();

		textbox_end_left = new TextureRegion[2];
		textbox_end_left[0] =  new TextureRegion();
		textbox_end_left[1] =  new TextureRegion();

		textbox_end_right = new TextureRegion[2];
		textbox_end_right[0] = new TextureRegion();
		textbox_end_right[1] = new TextureRegion();
		textbox_end_right[0].flip(true, false);
		textbox_end_right[1].flip(true, false);

		exit_button = new TextureRegion[2];
		exit_button[0] = new TextureRegion();
		exit_button[1] = new TextureRegion();
		
		options_button = new TextureRegion[2];
		options_button[0] = new TextureRegion();
		options_button[1] = new TextureRegion();

		back_button = new TextureRegion[2];
		back_button[0] = new TextureRegion();
		back_button[1] = new TextureRegion();

		confirm_button = new TextureRegion[2];
		confirm_button[0] = new TextureRegion();
		confirm_button[1] = new TextureRegion();

		cancel_button = new TextureRegion[2];
		cancel_button[0] = new TextureRegion();
		cancel_button[1] = new TextureRegion();
		
		left_button = new TextureRegion[2];
		left_button[0] = new TextureRegion();
		left_button[1] = new TextureRegion();
		
		right_button = new TextureRegion[2];
		right_button[0] = new TextureRegion();
		right_button[1] = new TextureRegion();
	}

	public static void load(Theme theme) {
		
		String themeDir;
		
		switch (theme) {
			case LIGHT:
				backgroundColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
				textColor_1 = new Color(1.0f, 1.0f, 1.0f, 1.0f);
				textColor_2 = new Color(0.0f, 0.0f, 0.0f, 1.0f);
				themeDir = "light";
				break;
			case DARK:
				backgroundColor = new Color(0.0f, 0.0f, 0.0f, 1.0f);
				textColor_1 = new Color(0.0f, 0.0f, 0.0f, 1.0f);
				textColor_2 = new Color(1.0f, 1.0f, 1.0f, 1.0f);
				themeDir = "dark";
				break;
			default:
				backgroundColor = new Color(1.0f, 1.0f, 1.0f, 1.0f);
				textColor_1 = new Color(1.0f, 1.0f, 1.0f, 1.0f);
				textColor_2 = new Color(0.0f, 0.0f, 0.0f, 1.0f);
				themeDir = "light";
				break;
		}

		chars = split(themeDir + "/ui/chars.png", 8, 8);

		load(title, themeDir + "/ui/title.png");
		load(title_help, themeDir + "/ui/help_title.png");
		load(title_opponent, themeDir + "/ui/opponent_title.png");
		load(title_options, themeDir + "/ui/options_title.png");

		load(nought, themeDir + "/board/o.png");
		load(cross, themeDir + "/board/x.png");
		load(grid, themeDir + "/board/grid.png");
		load(nought_highlight, themeDir + "/board/nought_highlight.png");
		load(cross_highlight, themeDir + "/board/cross_highlight.png");
		load(yellow_highlight, themeDir + "/board/yellow_highlight.png");
		load(yellow_highlight_large, themeDir + "/board/yellow_highlight_large.png");
		
		load(underline, themeDir + "/ui/underline.png");

		load(ring, themeDir + "/ring.png");
		load(dot, themeDir + "/dot.png");

		load(textbox[0], themeDir + "/ui/textbox_0.png");
		load(textbox[1], themeDir + "/ui/textbox_1.png");

		load(textbox_end_left[0], themeDir + "/ui/textbox_end_0.png");
		load(textbox_end_left[1], themeDir + "/ui/textbox_end_1.png");

		load(textbox_end_right[0], themeDir + "/ui/textbox_end_0.png");
		textbox_end_right[0].flip(true, false);
		load(textbox_end_right[1], themeDir + "/ui/textbox_end_1.png");
		textbox_end_right[1].flip(true, false);

		load(exit_button[0], themeDir + "/ui/exit_0.png");
		load(exit_button[1], themeDir + "/ui/exit_1.png");
		
		load(options_button[0], themeDir + "/ui/options_0.png");
		load(options_button[1], themeDir + "/ui/options_1.png");

		load(back_button[0], themeDir + "/ui/back_0.png");
		load(back_button[1], themeDir + "/ui/back_1.png");

		load(confirm_button[0], themeDir + "/ui/confirm_0.png");
		load(confirm_button[1], themeDir + "/ui/confirm_1.png");

		load(cancel_button[0], themeDir + "/ui/cancel_0.png");
		load(cancel_button[1], themeDir + "/ui/cancel_1.png");
		
		load(left_button[0], themeDir + "/ui/left_0.png");
		load(left_button[1], themeDir + "/ui/left_1.png");
		
		load(right_button[0], themeDir + "/ui/right_0.png");
		load(right_button[1], themeDir + "/ui/right_1.png");
	}

	public static void load(TextureRegion tex, String name) {
		Texture texture = new Texture(Gdx.files.internal(name));
		tex.setRegion(texture);
		return;
	}

	private static TextureRegion[] split(String name, int dx, int dy) {
		Texture texture = new Texture(Gdx.files.internal(name));
		int xSlices = texture.getWidth() / dx;
		int ySlices = texture.getHeight() / dy;
		TextureRegion[] out = new TextureRegion[xSlices * ySlices];
		for (int x = 0; x < xSlices; x++) {
			for (int y = 0; y < ySlices; y++) {
				out[x + y * xSlices] = new TextureRegion(texture, x * dx, y * dy, dx, dy);
			}
		}
		return out;
	}

}
