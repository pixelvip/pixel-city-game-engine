package grid;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import gameobject.IGameClickable;
import gameobject.IGameObject;
import handler.GameRegistration;

/**
 * Grid for GameObjects
 */
public class GameGrid<T extends IGameObject> {
	
	private GameGridGameObjectWrapper<T>[][] gameObjectGrid;
	private int width;
	private int height;
	
	@SuppressWarnings("unchecked")
	public GameGrid(int width, int height) {
		this.width = width;
		this.height = height;
		gameObjectGrid = new GameGridGameObjectWrapper[width][height];
	}
	
	/**
	 * Uses Supplier to populate the grid with new GameObjects. Also
	 * automatically sets the corresponding neighbours.
	 */
	public void fillWithGameObjectTemplate(Supplier<T> gameObjectSupplier) {
		forEach((x, y) -> {
			T gameObject = gameObjectSupplier.get();
			gameObject.setX(x * gameObject.getWidth());
			gameObject.setY(y * gameObject.getHeight());
			GameGridGameObjectWrapper<T> gameObjectWrapper = new GameGridGameObjectWrapper<>(gameObject);
			
			if (x > 0) {
				gameObjectWrapper.setLeftNeighbour(gameObjectGrid[x - 1][y]);
				if (x < width) {
					gameObjectGrid[x - 1][y].setRightNeighbour(gameObjectWrapper);
				}
			}
			if (y > 0) {
				gameObjectWrapper.setTopNeighbour(gameObjectGrid[x][y - 1]);
				if (y < height) {
					gameObjectGrid[x][y - 1].setBottomNeighbour(gameObjectWrapper);
				}
			}
			gameObjectGrid[x][y] = gameObjectWrapper;
		});
	}
	
	public Stream<T> filter(Predicate<IGameObject> predicate) {
		return filterWrappers(x -> true).map(GameGridGameObjectWrapper<T>::getGameObject).filter(predicate);
	}
	
	public void forEach(Consumer<T> consumer) {
		forEach((x, y) -> consumer.accept(gameObjectGrid[x][y].getGameObject()));
	}
	
	private void forEach(BiConsumer<Integer, Integer> biConsumer) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				biConsumer.accept(x, y);
			}
		}
	}
	
	private Stream<GameGridGameObjectWrapper<T>> filterWrappers(Predicate<GameGridGameObjectWrapper<T>> predicate) {
		ArrayList<GameGridGameObjectWrapper<T>> gameObjectWrapperList = new ArrayList<>();
		forEach((x, y) -> {
			if (predicate.test(gameObjectGrid[x][y])) {
				gameObjectWrapperList.add(gameObjectGrid[x][y]);
			}
		});
		return gameObjectWrapperList.stream();
	}
	
	public Stream<T> getNeighbours(T gameObject) {
		GameGridGameObjectWrapper<T> wrapper = getWrapperFor(gameObject);
		if (wrapper.getGameObject() != null) {
			return wrapper.getNeighbours().filter(Objects::nonNull).map(GameGridGameObjectWrapper::getGameObject);
		} else {
			return Stream.empty();
		}
	}
	
	public Optional<T> getTopNeighbour(T gameObject) {
		return getNeighbour(gameObject, GameGridGameObjectWrapper::getTopNeighbour);
	}
	
	public Optional<T> getRightNeighbour(T gameObject) {
		return getNeighbour(gameObject, GameGridGameObjectWrapper::getRightNeighbour);
	}
	
	public Optional<T> getBottomNeighbour(T gameObject) {
		return getNeighbour(gameObject, GameGridGameObjectWrapper::getBottomNeighbour);
	}
	
	public Optional<T> getLeftNeighbour(T gameObject) {
		return getNeighbour(gameObject, GameGridGameObjectWrapper::getLeftNeighbour);
	}
	
	private GameGridGameObjectWrapper<T> getWrapperFor(T gameObject) {
		return filterWrappers(wrapper -> wrapper.getGameObject().equals(gameObject)).findFirst()
				.orElseThrow(WrapperNotFoundException::new);
	}
	
	private Optional<T> getNeighbour(T gameObject,
			Function<GameGridGameObjectWrapper<T>, GameGridGameObjectWrapper<T>> getNeighbourfunction) {
		GameGridGameObjectWrapper<T> wrapper = getWrapperFor(gameObject);
		if (wrapper.getGameObject() != null) {
			return Optional.of(getNeighbourfunction.apply(wrapper).getGameObject());
		} else {
			return Optional.empty();
		}
	}
	
	public void replace(T oldGameObject, T newGameObject) {
		newGameObject.setX(oldGameObject.getX());
		newGameObject.setY(oldGameObject.getY());
		getWrapperFor(oldGameObject).setGameObject(newGameObject);
		
		oldGameObject.setVisible(false);
		if (oldGameObject instanceof IGameClickable) {
			((IGameClickable) oldGameObject).setEnabled(false);
		}
		GameRegistration.registerGameObjectToBeRemoved(oldGameObject);
	}
	
	public T getAt(int x, int y) {
		return gameObjectGrid[x][y].getGameObject();
	}
}
