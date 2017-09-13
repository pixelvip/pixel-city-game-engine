package gameobject;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import handler.GameRegistration;

public abstract class GameClickableObject extends GameObject implements IGameClickable, MouseListener {
	
	protected boolean isMouseDown;
	protected boolean enabled = true;
	
	public GameClickableObject() {
		GameRegistration.registerMouseListener(this);
	}
	
	@Override
	public final void mouseClicked(MouseEvent e) {
		if (mouseOverThisObject(e) && enabled) {
			clicked();
		}
	}
	
	@Override
	public final void mousePressed(MouseEvent e) {
		isMouseDown = true;
	}
	
	@Override
	public final void mouseReleased(MouseEvent e) {
		isMouseDown = false;
	}
	
	@Override
	public final void mouseEntered(MouseEvent e) {
		if (isMouseDown) {
			mouseClicked(e);
		}
	}
	
	@Override
	public final void mouseExited(MouseEvent e) {}
	
	private boolean mouseOverThisObject(MouseEvent e) {
		return e.getX() >= this.x && e.getX() <= this.x + getWidth() && e.getY() >= this.y
				&& e.getY() <= this.y + getHeight();
	}
	
	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}