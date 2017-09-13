package gameobject;

import java.awt.Graphics;

public interface IGameObject {
	public void render(Graphics graphics);
	
	public int getWidth();
	
	public int getHeight();
	
	public int getX();
	
	public int getY();
	
	public void setX(int x);
	
	public void setY(int y);
	
	public boolean isVisible();
	
	public void setVisible(boolean visible);
}
