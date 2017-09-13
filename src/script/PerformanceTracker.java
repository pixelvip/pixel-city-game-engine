/**
 * 
 */
package script;

import java.awt.Graphics;

import gameobject.GameObject;
import logger.GameLogger;

public class PerformanceTracker extends GameObject {
	
	private long lastUpdateTime = System.currentTimeMillis();
	private long lastRenderTime = System.currentTimeMillis();
	private int updateTimes;
	private int renderTimes;
	
	@Override
	public void update() {
		updateTimes++;
		if (lastUpdateTime + 1000 <= System.currentTimeMillis()) {
			lastUpdateTime = System.currentTimeMillis();
			GameLogger.performance("UPS: " + updateTimes);
			updateTimes = 0;
		}
	}
	
	@Override
	public void render(Graphics graphics) {
		renderTimes++;
		if (lastRenderTime + 1000 <= System.currentTimeMillis()) {
			lastRenderTime = System.currentTimeMillis();
			GameLogger.performance("FPS: " + renderTimes);
			renderTimes = 0;
		}
	}
}
