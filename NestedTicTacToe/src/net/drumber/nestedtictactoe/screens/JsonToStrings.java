package net.drumber.nestedtictactoe.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

public class JsonToStrings {
	
	public String[] strings;
	
	@SuppressWarnings("unused")
	private JsonToStrings(){}
	
	public JsonToStrings(String filename){
		FileHandle fh = Gdx.files.internal(filename);
		if(fh.exists()){
			String fileString = fh.readString();
			Json json = new Json();
			JsonToStrings jts = json.fromJson(JsonToStrings.class, fileString);
			this.strings = jts.strings;
		}
	}
}
