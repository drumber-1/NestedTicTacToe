package close.nestedtictactoe;

import net.drumber.nestedtictactoe.NestedTicTacToe;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;

public class NestedTicTacToeDesktop {
	public static void main(String[] args) {
		new LwjglApplication(new NestedTicTacToe(), "Nested Tic Tac Toe", 640, 360, true);
	}

}
