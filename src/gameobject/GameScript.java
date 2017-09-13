package gameobject;

import handler.GameKeyListener;
import handler.GameRegistration;

public abstract class GameScript implements IGameScript {
	protected GameKeyListener input;
	
	public GameScript() {
		GameRegistration.registerGameScript(this);
		input = GameRegistration.getGameInputHandler();
	}
	
}
