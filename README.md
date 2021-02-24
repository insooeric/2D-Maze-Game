# 2D-Maze-Game
2D Maze game. The player moves around the maze to escape from the randomly generated maze. Clearing time will be recorded and displayed once the game is cleared.

This MazeGame was developed in Eclipse, with Java.

# Description
## Input
The input of this game is simple and easy.

Player Movement: W/A/S/D



If you 
In order to generate a randome maze, a following Pseudocode is used.
Type of map array is setted in Integer.
Odd number represents wall, and even number represents ground.

<pre>
<code>
  1 1 1 1 1 1 1 1 1 1 1
  1 x 1 x 1 x 1 x 1 x 1
  1 1 1 1 1 1 1 1 1 1 1
  1 x 1 x 1 x 1 x 1 x 1
  1 1 1 1 1 1 1 1 1 1 1
  1 x 1 x 1 x 1 x 1 x 1
  1 1 1 1 1 1 1 1 1 1 1
  1 x 1 x 1 x 1 x 1 x 1
  1 1 1 1 1 1 1 1 1 1 1
  1 x 1 x 1 x 1 x 1 x 1
  1 1 1 1 1 1 1 1 1 1 1
</code>
</pre>

X is ground, 1 is wall

<pre>
<code>
  Pick Random_Cell
  RandomCell->Visited=true;
  wallList Add(Random_Cell's Surrounding_walls)
  while( wallList Not Empty) loop
  
    wall = wallList->getRandomWall();
    if(wall has unvisited cell on either side)
    {
      unvisited cell = visited;
      wallList Add(wall->newly_visited_cell's- Surrounding_walls);
    }
    Remove wall from wallList;
}
</code>
</pre>

For more details
(https://bytes.com/topic/software-development/answers/823704-maze-generation-algorithm-s)

### Adventages
Only one solution exists
Player can approach to every ground. (all the grounds are connected)
