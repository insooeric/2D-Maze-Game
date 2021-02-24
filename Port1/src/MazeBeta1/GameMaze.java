package MazeBeta1;

import java.awt.Rectangle;
import java.util.ArrayList;

public class GameMaze implements Variable{
	
	GameMaze() {
		init();
	}
	
	static Block[][] map = new Block[MAPSIZE][MAPSIZE];
	
	ArrayList<Block> list = new ArrayList<Block>();

	public void init() {
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				map[i][j] = new Block(i, j, false);
			}
		}
		generateMap();
	}

	public void generateMap() {
		int randX = (int) (Math.random() * N) * 2 + 1;
		int randY = (int) (Math.random() * N) * 2 + 1;
		map[randY][randX].visited = true;
		System.out.println(randX + "  " + randY + "  " + map.length + " " + N);
		if (randX - 1 != 0) {
			list.add(map[randY][randX - 1]);
		}
		if (randX + 1 != MAPSIZE) {
			list.add(map[randY][randX + 1]);
		}
		if (randY - 1 != 0) {
			list.add(map[randY - 1][randX]);
		}
		if (randY + 1 != MAPSIZE) {
			list.add(map[randY + 1][randX]);
		}

		while (!list.isEmpty()) {
			int index = (int) (Math.random() * list.size());
			Block wall = list.get(index);
			if (wall.y % 2 == 1) { // 2nd row
				if (wall.x - 1 != 0 && map[wall.y][wall.x - 1].visited == false) {
					map[wall.y][wall.x - 1].visited = true;
					map[wall.y][wall.x].visited = true;
					if (wall.x - 2 != 0) {
						list.add(map[wall.y][wall.x - 2]);
					}
					if (wall.y - 1 != 0) {
						list.add(map[wall.y - 1][wall.x - 1]);
					}
					if (wall.y + 1 != 0) {
						list.add(map[wall.y + 1][wall.x - 1]);
					}
				} else if (wall.x + 1 <= MAPSIZE - 1 && map[wall.y][wall.x + 1].visited == false) {
					map[wall.y][wall.x + 1].visited = true;
					map[wall.y][wall.x].visited = true;
					if (wall.x + 2 != 0) {
						list.add(map[wall.y][wall.x + 2]);
					}
					if (wall.y - 1 != 0) {
						list.add(map[wall.y - 1][wall.x + 1]);
					}
					if (wall.y + 1 != 0) {
						list.add(map[wall.y + 1][wall.x + 1]);
					}
				}
			} else { // 3rd row
				if (wall.y - 1 != 0 && map[wall.y - 1][wall.x].visited == false) {
					map[wall.y - 1][wall.x].visited = true;
					map[wall.y][wall.x].visited = true;
					if (wall.y - 2 != 0) {
						list.add(map[wall.y - 2][wall.x]);
					}
					if (wall.x - 1 != 0) {
						list.add(map[wall.y - 1][wall.x - 1]);
					}
					if (wall.x + 1 != 0) {
						list.add(map[wall.y - 1][wall.x + 1]);
					}
				} else if (wall.y + 1 <= MAPSIZE - 1 && map[wall.y + 1][wall.x].visited == false) {
					map[wall.y + 1][wall.x].visited = true;
					map[wall.y][wall.x].visited = true;
					if (wall.y + 2 != 0) {
						list.add(map[wall.y + 2][wall.x]);
					}
					if (wall.x - 1 != 0) {
						list.add(map[wall.y + 1][wall.x - 1]);
					}
					if (wall.x + 1 != 0) {
						list.add(map[wall.y + 1][wall.x + 1]);
					}
				}
			}
			list.remove(index);
		}
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				if (map[i][j].visited == false) {
					if (i % 2 == 1) {
						if (j % 2 == 0) {
							m[i][j] = 1;
						}
					} else {
						m[i][j] = 1;
					}
				}
			}
		}
		for (int i = 0; i < MAPSIZE; i++) {
			m[i][MAPSIZE - 1] = 1;
			m[i][0] = 1;
			m[MAPSIZE - 1][i] = 1;
			m[0][i] = 1;
		}
		m[0][1] = 0;
		m[MAPSIZE - 1][MAPSIZE - 2] = 0;
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				switch(m[i][j]) {
				case 0:
					System.out.print("   ");
					break;
				case 1:
					System.out.print(" ▩");
					break;
				}
			}
			System.out.println();
		}
		System.out.println("\n");
	}
}

class Block extends Rectangle{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	int x;
	int y;
	boolean visited;

	public Block(int y, int x, boolean visited){
		this.x = x;   this.y = y;   this.visited = visited;  
	}

	public String toString() {
		return "x = " + x + "  y = " + y + "  visit = " + visited + "\n";
	}
}

