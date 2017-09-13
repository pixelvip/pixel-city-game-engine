/**
 * 
 */
package handler;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class GameKeyListener implements KeyListener {
	private boolean[] keyArray = new boolean[256];
	
	public GameKeyListener(Component component) {
		component.addKeyListener(this);
	}
	
	public boolean isKeyDown(int keyCode) {
		if (keyCode > 0 && keyCode < 256) {
			return keyArray[keyCode];
		}
		return false;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() > 0 && e.getKeyCode() < 256) {
			keyArray[e.getKeyCode()] = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() > 0 && e.getKeyCode() < 256) {
			keyArray[e.getKeyCode()] = false;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		// Not used
	}
}
