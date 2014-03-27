package net.drumber.nestedtictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

// Note that playerinfo is NOT used for saving/loading the gamestate, this is handled by the GameState and Save classes
// playerinfo is only used to reconstruct the values used previously when a new game is being set up

public class NestedPreferences {

	private static Preferences options = Gdx.app.getPreferences("nestedtictactoe.options"); //Global application options (changed in options menu)
	private static Preferences playerinfo = Gdx.app.getPreferences("nestedtictactoe.playerinfo"); //Last used player options (from opponent select screen)

	private NestedPreferences() {
	}
	
	public static boolean isShowNextMove(){
		return options.getBoolean("shownextmove", true);
	}
	
	public static void setShowNextMove(boolean showNextMove){
		options.putBoolean("shownextmove", showNextMove);
		options.flush();
	}
	
	public static boolean isInvertPlayerTwo(){
		return options.getBoolean("invertplayertwo", false);
	}
	
	public static void setInvertPlayerTwo(boolean invertPlayerTwo){
		options.putBoolean("invertplayertwo", invertPlayerTwo);
		options.flush();
	}
	
	public static boolean isMoreMagic(){
		return options.getBoolean("moremagic", false);
	}
	
	public static void setMoreMagic(boolean moreMagic){
		options.putBoolean("moremagic", moreMagic);
		options.flush();
	}
	
	public static String getTheme(){
		return options.getString("theme", "light");
	}
	
	public static void setTheme(String theme){
		options.putString("theme", theme);
		options.flush();
	}
	
	public static boolean isNoughtsAI(){
		return playerinfo.getBoolean("noughtsIsAI", false);
	}
	
	public static void setNoughtsAI(boolean noughtIsAI){
		playerinfo.putBoolean("noughtsIsAI", noughtIsAI);
		playerinfo.flush();
	}
	
	public static boolean isCrossesAI(){
		return playerinfo.getBoolean("crossesIsAI", false);
	}
	
	public static void setCrossesAI(boolean crossesIsAI){
		playerinfo.putBoolean("crossesIsAI", crossesIsAI);
		playerinfo.flush();
	}
	
	public static int getNoughtsAILevel(){
		return playerinfo.getInteger("noughtsAILevel", 0);
	}
	
	public static void setNoughtsAILevel(int noughtsAILevel){
		playerinfo.putInteger("noughtsAILevel", noughtsAILevel);
		playerinfo.flush();
	}
	
	public static int getCrossesAILevel(){
		return playerinfo.getInteger("crossesAILevel", 0);
	}
	
	public static void setCrossesAILevel(int crossesAILevel){
		playerinfo.putInteger("crossesAILevel", crossesAILevel);
		playerinfo.flush();
	}
	
	public static String getNoughtsName(){
		return playerinfo.getString("noughtsName", "NOUGHTS");
	}
	
	public static void setNoughtsName(String noughtsName){
		playerinfo.putString("noughtsName", noughtsName);
		playerinfo.flush();
	}
	
	public static String getCrossesName(){
		return playerinfo.getString("crossesName", "CROSSES");
	}
	
	public static void setCrossesName(String crossesName){
		playerinfo.putString("crossesName", crossesName);
		playerinfo.flush();
	}

}
