package net.drumber.nestedtictactoe;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Input implements InputProcessor {

	private Vector2 pointerLocation = new Vector2(); //Current pointer location
	private Vector2 pointerLocationFirst = new Vector2(); //Pointer location when last touched down
	private Vector2 pointerDrag = new Vector2(); //Distance pointer has been dragged
	private boolean pointerDown = false; //True if pointer is down
	private boolean pointerDownLast = false; //True if point was moved down last frame
	private boolean pointerUpLast = false; //True if pointer was moved up last frame
	private boolean backPressedLast = false; //True if the back button was pressed last frame
	
	protected OrthographicCamera cam;
	
	public Input(OrthographicCamera cam){
		this.cam = cam;
		Gdx.input.setCatchBackKey(true);
	}
	
	//Called at end of each frame
	public void reset(){
		pointerDownLast = false;
		pointerUpLast = false;
		backPressedLast = false;
	}
	
	//Show a summary of the input state
	public void report(){
		System.out.println("Current input state:");
		System.out.println("\tPointer at: " + pointerLocation);
		System.out.println("\tPointer down: " + pointerDown);
		System.out.println("\tPointer drag: " + pointerDrag);
		System.out.println("\tpointer down last frame: " + pointerDownLast);
		System.out.println("\tPointer up last frame: " + pointerUpLast);
		System.out.println("\tBack pressed last frame: " + backPressedLast);
	}
	
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.BACK || keycode == Keys.ESCAPE){
			backPressedLast = true;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		pointerLocation = unprojectPointer(screenX, screenY);
		pointerLocationFirst = unprojectPointer(screenX, screenY);
		pointerDown = true;
		pointerDownLast = true;
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		pointerLocation = unprojectPointer(screenX, screenY);
		pointerDrag.set(0, 0);
		pointerDown = false;
		pointerUpLast = true;
		return true;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		pointerLocation = unprojectPointer(screenX, screenY);
		pointerDrag = pointerLocation.cpy().sub(pointerLocationFirst);
		return true;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}
	
	//Converts pixel coordinates to world coordinates
	private Vector2 unprojectPointer(int screenX, int screenY){
		Vector3 pointer = new Vector3(screenX, screenY, 0);
		cam.unproject(pointer);
		return new Vector2(pointer.x, pointer.y);
	}
	
	public Vector2 getPointerLocation() {
		return pointerLocation;
	}
	
	public Vector2 getPointerLocationFirst() {
		return pointerLocationFirst;
	}
	
	public Vector2 getPointerDrag() {
		return pointerDrag;
	}

	public boolean isPointerDown() {
		return pointerDown;
	}

	public boolean isPointerDownLast() {
		return pointerDownLast;
	}

	public boolean isPointerUpLast() {
		return pointerUpLast;
	}
	
	public boolean isBackPressedLast(){
		return backPressedLast;
	}

}
