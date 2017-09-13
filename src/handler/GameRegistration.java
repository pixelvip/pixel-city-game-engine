package handler;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import canvas.GameCanvas;
import gameobject.IGameObject;
import gameobject.IGameScript;

public class GameRegistration {
	private static GameCanvas gameCanvas;
	private static List<IGameScript> gameScriptList = new ArrayList<>();
	private static List<IGameObject> gameObjectList = new ArrayList<>();
	private static List<IGameObject> gameObjectToBeAddedList = new ArrayList<>();
	private static List<IGameObject> gameObjectToBeRemovedList = new ArrayList<>();
	private static GameKeyListener inputHandler;
	
	private GameRegistration() {}
	
	public static void registerGameCanvas(GameCanvas gameCanvas) {
		GameRegistration.gameCanvas = gameCanvas;
		inputHandler = new GameKeyListener(gameCanvas);
	}
	
	public static void registerGameScript(IGameScript gameScript) {
		gameScriptList.add(gameScript);
	}
	
	public static void registerGameObject(IGameObject gameObject) {
		gameObjectToBeAddedList.add(gameObject);
	}
	
	public static void registerMouseListener(MouseListener mouseListener) {
		gameCanvas.addMouseListener(mouseListener);
	}
	
	public static void registerGameObjectToBeRemoved(IGameObject gameObjectToBeRemoved) {
		if (!gameObjectToBeRemovedList.contains(gameObjectToBeRemoved)) {
			gameObjectToBeRemovedList.add(gameObjectToBeRemoved);
		}
	}
	
	public static Stream<IGameScript> getGameScriptStream() {
		return gameScriptList.stream();
	}
	
	public static Stream<IGameObject> getGameObjectStream() {
		GameRegistration.clearUnusedGameObjects();
		GameRegistration.loadNewGameObjects();
		return gameObjectList.stream();
	}
	
	public static GameKeyListener getGameInputHandler() {
		return inputHandler;
	}
	
	private static void clearUnusedGameObjects() {
		if (!gameObjectToBeRemovedList.isEmpty()) {
			gameObjectList.removeAll(gameObjectToBeRemovedList);
			gameObjectToBeRemovedList.clear();
		}
	}
	
	private static void loadNewGameObjects() {
		gameObjectList.addAll(gameObjectToBeAddedList);
		gameObjectToBeAddedList.clear();
	}
}
