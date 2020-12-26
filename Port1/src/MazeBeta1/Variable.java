package MazeBeta1;

import java.util.ArrayList;

public interface Variable {
	final int SCRW = 1400;
	final int SCRH = 800;
	
	final int BLOCKW = 120;
	final int BLOCKH = BLOCKW;
	
	final int WALLNUM = 4;
	final int WALLW = BLOCKW/WALLNUM;
	final int WALLH = BLOCKH/WALLNUM;
	
	final int PLAYERW_CROP = 48;
	final int PLAYERH_CROP = 92;
	final int PLAYERW = 36;
	final int PLAYERH = 60;
	final int PLAYER_SPEED = 6;
	final int DIAGONAL_SPEED = PLAYER_SPEED/2;
	
	final int SUB_SCRW = 400;
	final int SUB_SCRH = 300;
	
	final int t_speed = 30; //30
	
	final int RGB_MAX = 255;
	final int SECOND = 1000;
	final int DIAMETER = 120;
	
	static final int N = 10; // Number of rooms in a row
	static final int MAPSIZE = N * 2 + 1; // Don't change
	static int[][] m = new int[MAPSIZE][MAPSIZE];
}