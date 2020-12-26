# 2D-Maze-Game
This game is programmed in Java.<br />
The primary goal of this project is to generate a random maze using an algorithm and escape from it.

## Algorithm
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
