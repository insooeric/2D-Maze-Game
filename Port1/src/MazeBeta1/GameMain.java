/*
* Programmer: Insoo Son
* Complete date: 26 Dec 2020
*/



package MazeBeta1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.*;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;



public class GameMain extends JFrame implements Variable, KeyListener {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				new GameMain();
			}
		});
	}
	
	DrawingPanel mainPanel;
	GameOver endPanel = new GameOver();
//	ArrayList<Rectangle> filledList = new ArrayList<Rectangle>();
//	ArrayList<Rectangle> unfilledList = new ArrayList<Rectangle>();

	ArrayList<BGcomponent> roofList = new ArrayList<BGcomponent>();
	ArrayList<BGcomponent> wallList = new ArrayList<BGcomponent>();
	ArrayList<BGcomponent> groundList = new ArrayList<BGcomponent>();
	ArrayList<BufferedImage> pSpriteList = new ArrayList<BufferedImage>();

	BGcomponent wallImg = new BGcomponent(1);
	BGcomponent groundImg = new BGcomponent(2);
	PlayerSprite player;
	CameraAngle camera;
	ScreenShader shader;
	
	HashSet<Integer> pressedKeys = new HashSet<Integer>();
	HashSet<Integer> existKeys = new HashSet<Integer>();
	Timer timer;
	
	BGcomponent eraseRect1;
	Destination finalRect;
	
/*
	Rectangle pTopRect = new Rectangle();
	Rectangle pBotRect = new Rectangle();
	Rectangle p
*/
	boolean isInitMotion = true, keyExist = true, isGameOver = false, isMoveable = true;
	boolean isRight = false, isLeft = false, isUp = false, isDown = false;
	boolean isUpR = false, isUpL = false, isDownR = false, isDownL = false;
	int motion, finalMotion, pSpeed;
	long beforeTime, afterTime, secDiffTime;

	GameMain() {
		setGUI();
		setMaze();
		setPlayer();
		setCamera();
		setBG();
		setKeySets();
		setBoard();
		startTimer();
		
		System.out.println("\n--------------------------------------");
		System.out.println();
		beforeTime = System.currentTimeMillis();
	}

	private void gameOver() {
		this.remove(mainPanel);
		endPanel.getClearTime(secDiffTime);
		this.add(endPanel);
		this.setVisible(true);
	}

	/*
	 * * * * * * * * * * * * * Timer * * * * * * * * * * * *
	 */

	private void startTimer() {
		timer = new Timer(t_speed, new TimerT());
		timer.start();
	}

	private class TimerT implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!pressedKeys.isEmpty()) {
				Iterator<Integer> i = pressedKeys.iterator();

				int n = 0;
				while (i.hasNext()) {
					n = i.next();
//					System.out.println("Before) isUp: " + isUp + " | isDown: " + isDown + " | isLeft: " + isLeft
//							+ " | isRight: " + isRight);
					if (n == KeyEvent.VK_W) {
						if (!isUp) {
							isInitMotion = true;
							keyExist = true;
						}
						isUp = true;
					}
					if (n == KeyEvent.VK_S) {
						if (!isDown) {
							isInitMotion = true;
							keyExist = true;
						}
						isDown = true;
					}
					if (n == KeyEvent.VK_A) {
						if (!isLeft) {
							isInitMotion = true;
							keyExist = true;
						}
						isLeft = true;
					}
					if (n == KeyEvent.VK_D) {
						if (!isRight) {
							isInitMotion = true;
							keyExist = true;
						}
						isRight = true;
					}

//					System.out.println("After ) isUp: " + isUp + " | isDown: " + isDown + " | isLeft: " + isLeft
//							+ " | isRight: " + isRight);
					if (isUp && isLeft) {
						isUpL = true;
					} else {
						isUpL = false;
					}
					if (isUp && isRight) {
						isUpR = true;
					} else {
						isUpR = false;
					}
					if (isDown && isLeft) {
						isDownL = true;
					} else {
						isDownL = false;
					}
					if (isDown && isRight) {
						isDownR = true;
					} else {
						isDownR = false;
					}

					switch (n) {
					case KeyEvent.VK_W:
					case KeyEvent.VK_S:
					case KeyEvent.VK_A:
					case KeyEvent.VK_D:
						break;

					default:
//						System.out.println("Key doesn't exist");
						keyExist = false;
						break;
					}

//					System.out.println("\n");
					if (keyExist && isMoveable) {
//						playerPreMove();
						playerMotion();
						movePlayer();
					}
				}
				isInitMotion = false;
			}
			
			mainPanel.repaint();
			if(isGameOver) {
				isMoveable = false;
				if(!isMoveable) {
					afterTime = System.currentTimeMillis();
					secDiffTime = (afterTime - beforeTime);
				}
				
				if(endPanel.popUp) {
					timer.stop();
//					System.out.println("pop");
					gameOver();
				}
			}
		}
	}

	private void moveComponents(int x, int y) {
		for (BGcomponent roof : roofList) {
			roof.moveLocation(x, y);
		}
		for (BGcomponent wall : wallList) {
			wall.moveLocation(x, y);
		}
		for (BGcomponent ground : groundList) {
			ground.moveLocation(x, y);
		}
		
		finalRect.moveRect(x, y);
	}

	private void movePlayer() {
//		System.out.println("false");
		if (isUpL || isUpR || isDownL || isDownR) {
			pSpeed = DIAGONAL_SPEED;
		} else {
			pSpeed = PLAYER_SPEED;
		}

		if (isUp) {
			if (collision(player.getBotRect(), camera.top())) {
				moveComponents(0, pSpeed);
			} else {
				player.movePlayer(0, -pSpeed);
				shader.moveShade(0, -pSpeed);
			}
			
			if (collision(player.getBotRect(), wallList)) {
				player.movePlayer(0, pSpeed);
				shader.moveShade(0, pSpeed);
			}
		}
		
		if (isDown) {
			if(collision(player.getTopRect(), camera.bot())) {
				moveComponents(0, -pSpeed);
			} else {
				player.movePlayer(0, pSpeed);
				shader.moveShade(0, pSpeed);
			}
			
			if(collision(player.getTopRect(), roofList)) {
				player.movePlayer(0, -pSpeed);
				shader.moveShade(0, -pSpeed);
			}
			
			if(!collision(player.getbOutRect(), groundList)) {
				player.movePlayer(0, -pSpeed);
				shader.moveShade(0, -pSpeed);
			}
		}
		
		if (isLeft) {
			if(collision(player, camera.left())) {
				moveComponents(pSpeed , 0);
			} else {
				player.movePlayer(-pSpeed, 0);
				shader.moveShade(-pSpeed, 0);
			}
			
			if (collision(player.getTopRect(), roofList) || collision(player.getBotRect(), wallList)) {
				player.movePlayer(pSpeed, 0);
				shader.moveShade(pSpeed, 0);
			}
			
		}
		
		if (isRight) {
			if(collision(player, camera.right())) {
				moveComponents(-pSpeed , 0);
			} else {
				player.movePlayer(pSpeed, 0);
				shader.moveShade(pSpeed, 0);
			}
			if (collision(player.getTopRect(), roofList) || collision(player.getBotRect(), wallList)) {
				player.movePlayer(-pSpeed, 0);
				shader.moveShade(-pSpeed, 0);
			}
		}
		
		if(collision(player.getBotRect(), groundList.get(0))) {
			isGameOver = true;
		}
		
//		System.out.println("x: " + player.x + " y: " + player.y);
	}

	private void playerMotion() {
//		System.out.println(isUpL + " " + isUpR  + " " +  isDownL  + " " +  isDownR);
		if (isInitMotion) {
			if ((isUpL || isUpR || isDownL || isDownR)) {
				if (isUpL)
					motion = 27;
				if (isUpR)
					motion = 45;
				if (isDownL)
					motion = 9;
				if (isDownR)
					motion = 63;
			} else {
				if (isUp) {
					motion = 36;
				}

				if (isDown) {
					motion = 0;
				}

				if (isLeft) {
					motion = 18;
				}

				if (isRight) {
					motion = 54;
				}
			}
			finalMotion = motion + 8; // 63 + 8 = 71
//			System.out.println("starting from: " + motion + " finalMotion: " + finalMotion);
		} else {
//			System.out.print("previous motion: " + motion);
			motion++;
//			System.out.println(" current motion: " + motion);
			if (motion > finalMotion) {
				motion -= 9;
//				System.out.println("change motion: " + motion);
			}
		}
//		System.out.println("last motion: " + motion);
		player.setMotion(motion);
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
//		System.out.println("Key pressed");
		pressedKeys.add(e.getKeyCode());
	}

	@Override
	public void keyReleased(KeyEvent e) {
//		System.out.println("Key released");
		pressedKeys.remove(e.getKeyCode());
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			isUp = false;
			break;
		case KeyEvent.VK_S:
			isDown = false;
			break;
		case KeyEvent.VK_A:
			isLeft = false;
			break;
		case KeyEvent.VK_D:
			isRight = false;
			break;
		}
		isInitMotion = true;
	}

	/*
	 * * * * * * * * * * * collision detection * * * * * * * * * * *
	 */

	private boolean collision(Rectangle rect, ArrayList<BGcomponent> bgComp) {
		for (Rectangle obstacle : bgComp) {
			if (rect.intersects(obstacle)) {
				return true;
			}
		}
		return false;
	}

	private boolean collision(Rectangle rect, Rectangle obstacle) {
		if (rect.intersects(obstacle)) {
			return true;
		}
		return false;
	}

	/*
	 * * * * * * * * * * * * * set functions * * * * * * * * * * * *
	 */

	private void setKeySets() {
		existKeys.add(KeyEvent.VK_W);
		existKeys.add(KeyEvent.VK_S);
		existKeys.add(KeyEvent.VK_A);
		existKeys.add(KeyEvent.VK_D);
	}

	private void setGUI() {
		this.setTitle("Game Beta1");
		this.setSize(SCRW, SCRH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setBackground(Color.BLACK);
		this.addKeyListener(this);
	}

	private void setMaze() {
		new GameMaze();
	}

	private void setBoard() {
		mainPanel = new DrawingPanel();
		this.add(mainPanel);
		this.setVisible(true);
	}
	
	private void setBG() {
		for (int i = 0; i < MAPSIZE; i++) {
			for (int j = 0; j < MAPSIZE; j++) {
				switch (m[j][i]) {
				case 0:
//					unfilledList.add(new Rectangle(i * BLOCKW, j * BLOCKH, BLOCKW, BLOCKH));
					setGround(i * BLOCKW - camera.getXpos(), j * BLOCKH - camera.getYpos());
					break;
				case 1:
//					filledList.add(new Rectangle(i * BLOCKW, j * BLOCKH, BLOCKW, BLOCKH));
					setRoof(i * BLOCKW - camera.getXpos(), j * BLOCKH - camera.getYpos());
					if (j != MAPSIZE - 1) {
						setWall(i * BLOCKW - camera.getXpos(), j * BLOCKH - camera.getYpos());
					}
					break;
				}
			}
		}
		
		finalRect = new Destination(groundList.get(1).getImage(), groundList.get(1));
	}

	private void setPlayer() {
		player = new PlayerSprite(SCRW / 2 - PLAYERW / 2, SCRH / 2 - PLAYERH / 2);
		pSpriteList = player.getSprite();

	}

	private void setCamera() {
		camera = new CameraAngle(BLOCKW * 14 - player.width, BLOCKH * 17 + player.height / 2);
	}

	private void setRoof(int x, int y) {
		BGcomponent roof = new BGcomponent(x, y, BLOCKW, BLOCKH);
		roofList.add(roof);
	}

	private void setWall(int x, int y) {
		for (int i = 0; i < WALLNUM; i++) {
			BGcomponent wall = new BGcomponent(x + (WALLW) * i, y + BLOCKH, WALLW, WALLH);
			BGcomponent wall2 = new BGcomponent(x + (WALLW) * i, y + BLOCKH + WALLH, WALLW, WALLH);
			wall.setImage(wallImg.getImage());
			wall2.setImage(wallImg.getImage());
			wallList.add(wall);
			wallList.add(wall2);
		}
	}

	private void setGround(int x, int y) {
		BGcomponent ground = new BGcomponent(x, y, BLOCKW, BLOCKH);
		ground.setImage(groundImg.getImage());
		groundList.add(ground);
	}

	private class DrawingPanel extends JPanel {

		/**
		 *
		 */
		private static final long serialVersionUID = 1L;

		DrawingPanel() {
			shader = new ScreenShader(player.x + (player.width/2 - DIAMETER), player.y + (player.height/2 - DIAMETER), DIAMETER);
		}

		@Override
		public void paintComponent(Graphics g) {
			for (BGcomponent ground : groundList) {
				g.drawImage(ground.getImage(), ground.x, ground.y, ground.width, ground.height, null);
			}
			
			
			for (BGcomponent wall : wallList) {
				g.drawImage(wall.getImage(), wall.x, wall.y, wall.width, wall.height, null);
			}
			finalRect.drawRect(g);
			finalRect.drawExitRect(g, groundList.get(0));
			
			g.drawImage(player.getImage(), player.x, player.y, player.width, player.height, null);
			
			eraseRect1 = groundList.get(200);
			g.setColor(Color.BLACK);
			g.fillRect(eraseRect1.x, eraseRect1.y + (BLOCKW - 10), eraseRect1.width, 10);
			
//			player.drawExtraRect(g);
			
			g.setColor(Color.BLACK);
			for (BGcomponent r : roofList) {
				g.fillRect(r.x, r.y, r.width, r.height);
			}
			
			shader.paintShader(g);
			
			if(isGameOver) {
				endPanel.graphicPanel(g, this);
			}
//			camera.drawSubRect(g);
		}
	}
}