package net.drumber.nestedtictactoe.screens.ui;

public abstract class ToggleButton extends TextBoxButton {

	private final int nstates;
	private int currentState;
	private String[] texts;

	public ToggleButton(float x, float y, String[] texts, float scale, int startState) {
		super(x, y, texts[startState], scale);
		this.texts = texts;
		this.nstates = texts.length;
		this.currentState = startState;
		this.width = maxTextLength() * scale; // Set width to fit longest string
	}

	@Override
	public void click() {
		int oldState = currentState;
		currentState++;
		if (currentState >= nstates) {
			currentState -= nstates;
		}
		text = texts[currentState];
		click(oldState, currentState);
	}

	private int maxTextLength() {
		int max = 0;
		for (String s : texts) {
			if (s.length() > max) {
				max = s.length();
			}
		}
		return max;
	}

	public abstract void click(int stateClicked, int newState);

}
