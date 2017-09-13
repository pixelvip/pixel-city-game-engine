/**
 * 
 */
package gameobject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.Random;

import javax.imageio.ImageIO;

import handler.GameRegistration;
import logger.GameLogger;

public abstract class GameObject extends GameScript implements IGameObject {
	
	private static final int WIDTH_SCALE = 5;
	private static final int HEIGHT_SCALE = 5;
	
	protected BufferedImage image;
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean visible = true;
	
	public GameObject() {
		super();
		GameRegistration.registerGameObject(this);
	}
	
	protected void setImage(String path) {
		try {
			URL resource = getClass().getResource("/" + path);
			if (resource == null) {
				throw new IOException("Invalid path: " + path);
			}
			BufferedImage newImage = ImageIO.read(resource);
			image = scale(newImage);
		} catch (IOException e) {
			GameLogger.error("Image at path " + path + "could not be loaded.\nException: ", e);
		}
	}
	
	protected void setImages(String... paths) {
		Random random = new Random();
		setImage(paths[random.nextInt(paths.length)]);
	}
	
	protected void rotate(double degree) {
		BufferedImage rotatedImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = rotatedImage.createGraphics();
		AffineTransform affineTransform = AffineTransform.getRotateInstance(Math.toRadians(degree), width / 2.0,
				height / 2.0);
		graphics.drawRenderedImage(image, affineTransform);
		image = rotatedImage;
		
		// /////////////////////////////////////////////
		// NOTES FOR NEXT TIME:
		// Rotation is actually working, yeah!
		// I should work on the bugs, when building Streets on the edge of the
		// world, and make the Street tile updateTile method simpler.
		// /////////////////////////////////////////////
	}
	
	private BufferedImage scale(BufferedImage image) {
		BufferedImage scaledImage = new BufferedImage(image.getWidth() * WIDTH_SCALE, image.getHeight() * HEIGHT_SCALE,
				BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = scaledImage.createGraphics();
		AffineTransform affineTransform = AffineTransform.getScaleInstance(WIDTH_SCALE, HEIGHT_SCALE);
		graphics.drawRenderedImage(image, affineTransform);
		
		this.width = scaledImage.getWidth();
		this.height = scaledImage.getHeight();
		
		return scaledImage;
	}
	
	@Override
	public void render(Graphics graphics) {
		if (visible) {
			graphics.drawImage(image, x, y, null);
		}
	}
	
	@Override
	public int getX() {
		return x;
	}
	
	@Override
	public int getY() {
		return y;
	}
	
	@Override
	public void setX(int x) {
		this.x = x;
	}
	
	@Override
	public void setY(int y) {
		this.y = y;
	}
	
	@Override
	public int getWidth() {
		return width;
	}
	
	@Override
	public int getHeight() {
		return height;
	}
	
	@Override
	public boolean isVisible() {
		return visible;
	}
	
	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
