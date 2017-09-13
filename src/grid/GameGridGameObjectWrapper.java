package grid;

import java.util.EnumMap;
import java.util.stream.Stream;

import gameobject.IGameObject;

public class GameGridGameObjectWrapper<T extends IGameObject> {
	private T gameObject;
	private EnumMap<Neighbour, GameGridGameObjectWrapper<T>> neighbourMap;
	
	public GameGridGameObjectWrapper(T gameObject) {
		this.gameObject = gameObject;
		neighbourMap = new EnumMap<>(Neighbour.class);
	}
	
	public void setNeighbours(GameGridGameObjectWrapper<T> topNeighbour, GameGridGameObjectWrapper<T> rightNeighbour,
			GameGridGameObjectWrapper<T> bottomNeighbour, GameGridGameObjectWrapper<T> leftNeighbour) {
		neighbourMap.put(Neighbour.TOP, topNeighbour);
		neighbourMap.put(Neighbour.RIGHT, rightNeighbour);
		neighbourMap.put(Neighbour.BOTTOM, bottomNeighbour);
		neighbourMap.put(Neighbour.LEFT, leftNeighbour);
	}
	
	public T getGameObject() {
		return gameObject;
	}
	
	public void setGameObject(T gameObject) {
		this.gameObject = gameObject;
	}
	
	public GameGridGameObjectWrapper<T> getNeighbour(Neighbour neighbour) {
		return neighbourMap.get(neighbour);
	}
	
	public void setNeighbour(Neighbour neighbour, GameGridGameObjectWrapper<T> wrapper) {
		neighbourMap.put(neighbour, wrapper);
	}
	
	/**
	 * Returns all neighbours in the natural order starting from top. Returns
	 * neighbours as a Stream.
	 * 
	 * @return stream of Neighbours in order: Top, right, bottom and left.
	 */
	public Stream<GameGridGameObjectWrapper<T>> getNeighbours() {
		return neighbourMap.values().stream();
	}
	
	public void setTopNeighbour(GameGridGameObjectWrapper<T> wrapper) {
		neighbourMap.put(Neighbour.TOP, wrapper);
	}
	
	public GameGridGameObjectWrapper<T> getTopNeighbour() {
		return neighbourMap.get(Neighbour.TOP);
	}
	
	public void setRightNeighbour(GameGridGameObjectWrapper<T> wrapper) {
		neighbourMap.put(Neighbour.RIGHT, wrapper);
	}
	
	public GameGridGameObjectWrapper<T> getRightNeighbour() {
		return neighbourMap.get(Neighbour.RIGHT);
	}
	
	public void setBottomNeighbour(GameGridGameObjectWrapper<T> wrapper) {
		neighbourMap.put(Neighbour.BOTTOM, wrapper);
	}
	
	public GameGridGameObjectWrapper<T> getBottomNeighbour() {
		return neighbourMap.get(Neighbour.BOTTOM);
	}
	
	public void setLeftNeighbour(GameGridGameObjectWrapper<T> wrapper) {
		neighbourMap.put(Neighbour.LEFT, wrapper);
	}
	
	public GameGridGameObjectWrapper<T> getLeftNeighbour() {
		return neighbourMap.get(Neighbour.LEFT);
	}
}