package grid;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;

import gameobject.GameObject;
import gameobject.IGameObject;

public class GameGridTest {
	
	private GameGrid<IGameObject> gameGrid;
	
	private class GameObject01 extends GameObject {
		@Override
		public void update() {}
	}
	
	private class GameObject02 extends GameObject {
		@Override
		public void update() {}
	}
	
	@Before
	public void before() {
		gameGrid = new GameGrid<>(3, 3);
		gameGrid.fillWithGameObjectTemplate(() -> new GameObject01());
	}
	
	@Test
	public void testFillWithGameObjectTemplate() {
		gameGrid.forEach(gameObject -> {
			assertTrue("Game Grid not filled correctly with GameObjects", gameObject instanceof GameObject01);
		});
	}
	
	@Test
	public void testReplace() {
		gameGrid.replace(gameGrid.getAt(1, 1), new GameObject02());
		assertTrue("Replace did not work", gameGrid.getAt(1, 1) instanceof GameObject02);
	}
	
	@Test
	public void testGetNeighbours() {
		gameGrid.replace(gameGrid.getAt(1, 0), new GameObject02());
		gameGrid.replace(gameGrid.getAt(2, 1), new GameObject02());
		gameGrid.replace(gameGrid.getAt(1, 2), new GameObject02());
		gameGrid.replace(gameGrid.getAt(0, 1), new GameObject02());
		
		List<IGameObject> list = gameGrid.getNeighbours(gameGrid.getAt(1, 1)).collect(Collectors.toList());
		assertFalse("Neighbour List is empty", list.isEmpty());
		
		long count = list.stream().filter(gameObject -> gameObject instanceof GameObject02).count();
		assertEquals("Not all Neighbours are loaded correctly", 4, count);
	}
	
	@Test
	public void testEachGetNeighbour() {
		GameObject02 gameObject0201 = new GameObject02();
		gameGrid.replace(gameGrid.getAt(1, 0), gameObject0201);
		assertEquals("Top Neighbour was not set correctly", gameObject0201,
				gameGrid.getTopNeighbour(gameGrid.getAt(1, 1)).get());
		assertNotEquals("Right Neighbour should not be set", gameObject0201,
				gameGrid.getRightNeighbour(gameGrid.getAt(1, 1)).get());
		
		GameObject02 gameObject0202 = new GameObject02();
		gameGrid.replace(gameGrid.getAt(2, 1), gameObject0202);
		assertEquals("Right Neighbour was not set correctly", gameObject0202,
				gameGrid.getRightNeighbour(gameGrid.getAt(1, 1)).get());
		
		GameObject02 gameObject0203 = new GameObject02();
		gameGrid.replace(gameGrid.getAt(1, 2), gameObject0203);
		assertEquals("Bottom Neighbour was not set correctly", gameObject0203,
				gameGrid.getBottomNeighbour(gameGrid.getAt(1, 1)).get());
		
		GameObject02 gameObject0204 = new GameObject02();
		gameGrid.replace(gameGrid.getAt(0, 1), gameObject0204);
		assertEquals("Left Neighbour was not set correctly", gameObject0204,
				gameGrid.getLeftNeighbour(gameGrid.getAt(1, 1)).get());
	}
	
}
