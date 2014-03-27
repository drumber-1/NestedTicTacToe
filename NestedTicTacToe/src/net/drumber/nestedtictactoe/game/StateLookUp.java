package net.drumber.nestedtictactoe.game;

import net.drumber.nestedtictactoe.game.GameState.GridState;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;


public class StateLookUp {
	
	private static String STATEFILE_NAME = "states.json";
	private static FileHandle STATEFILE = Gdx.files.internal(STATEFILE_NAME);
	
	private GridState states[];
	
	private static StateLookUp instance;
	
	private StateLookUp (){
		
	}
	
	public static void load(){
		if(STATEFILE.exists()){
			String fileString = STATEFILE.readString();
			Json json = new Json();
			instance = json.fromJson(StateLookUp.class, fileString);
		}
	}
	
	public static GridState getState(int mask){
		if(mask < 0 || mask >= instance.states.length){
			return GridState.UNDECIDED;
		}
		return instance.states[mask];
	}

}
