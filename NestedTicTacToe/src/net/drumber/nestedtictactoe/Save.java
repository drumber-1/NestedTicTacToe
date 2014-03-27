package net.drumber.nestedtictactoe;

import java.io.IOException;

import net.drumber.nestedtictactoe.game.GameState;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class Save {

	private static String SAVEFILE_NAME = "gamestate.json";
	private static FileHandle SAVEFILE = Gdx.files.local(SAVEFILE_NAME);

	private Save() {
	}

	public static void saveGameState(GameState gs) {
		
		Json json = new Json();
		String gsString = json.prettyPrint(gs);

		if (!SAVEFILE.exists()) {
			try {
				SAVEFILE.file().createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		SAVEFILE.writeString(gsString, false);
	}

	public static GameState loadGameState() {
		if (saveExists()) {
			String gsString = SAVEFILE.readString();
			Json json = new Json();
			return json.fromJson(GameState.class, gsString);
		} else {
			return new GameState();
		}
	}

	public static boolean saveExists() {
		return SAVEFILE.exists();
	}

	public static void deleteSave() {
		SAVEFILE.delete();
	}

}
