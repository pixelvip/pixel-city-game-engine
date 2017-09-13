package canvas;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import gameobject.IGameScript;
import handler.GameRegistration;

public abstract class GameCanvas extends Canvas implements Runnable {
	protected static final long serialVersionUID = 1L;
	
	private static final String GAME_NAME = "Pixel Game";
	private static final int GAME_WIDTH = 2400;
	private static final int GAME_HEIGHT = 1600;
	private static final int UPS = 60;
	private static final int FPS = 60;
	
	private JFrame frame = new JFrame();
	private boolean running = false;
	
	private transient BufferedImage backBuffer = new BufferedImage(GAME_WIDTH, GAME_HEIGHT, BufferedImage.TYPE_INT_RGB);
	
	public GameCanvas() {
		GameRegistration.registerGameCanvas(this);
		start();
		init();
	}
	
	public abstract void start();
	
	public abstract void stop();
	
	private void init() {
		frame.setTitle(GAME_NAME);
		frame.setPreferredSize(new Dimension(GAME_WIDTH, GAME_HEIGHT));
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		running = true;
		new Thread(this).start();
	}
	
	public void stopGame() {
		running = false;
	}
	
	@Override
	public void run() {
		long lastUPSTime = 0;
		long lastFPSTime = 0;
		
		while (running) {
			if (System.currentTimeMillis() - lastUPSTime > (1000 / UPS)) {
				lastUPSTime = System.currentTimeMillis();
				GameRegistration.getGameScriptStream().forEach(IGameScript::update);
			}
			
			if (System.currentTimeMillis() - lastFPSTime > (1000 / FPS)) {
				lastFPSTime = System.currentTimeMillis();
				render();
			}
		}
	}
	
	private void render() {
		Graphics canvas = getGraphics();
		Graphics bbg = backBuffer.getGraphics();
		
		bbg.setColor(Color.WHITE);
		bbg.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
		
		GameRegistration.getGameObjectStream().forEach(obj -> obj.render(bbg));
		
		canvas.drawImage(backBuffer, 0, 0, this);
	}
}
