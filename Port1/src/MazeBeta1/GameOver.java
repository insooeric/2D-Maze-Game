package MazeBeta1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameOver extends JPanel implements Variable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Rectangle gameOverRect = new Rectangle(0, 0, SCRW, SCRH);
	private Color color;
	private String clearFile = "gameclear.PNG";
	private URL path;
	
	private Font font = new Font("Monospaced", Font.BOLD, 30);
	private BufferedImage clearImg;
	
	private int sec = 0, min = 0, hour = 0;
	private int alpha = 0;
	boolean popUp = false;
	
	GameOver() {
		loadImage(clearFile);
	}
	
	void getClearTime(long time) {
		this.sec = (int) (time/SECOND)%60;
		this.min = (int) (time/(SECOND*60))%60;
		this.hour = (int) (time/(SECOND*60*60));
		System.out.println(time/960);
	}
	
	void loadImage(String fileName) {
		System.out.println(path.toString());
		try {
			clearImg = ImageIO.read(path);
		} catch (IOException e) {
			if(path == null) {
				System.out.println("Image Missing");
			}
			e.printStackTrace();
		}
	}
	
	void graphicPanel(Graphics g, JPanel panel) {
		Graphics2D g2d = (Graphics2D) g;
		
		if(alpha >= RGB_MAX) {
			alpha = RGB_MAX;
			popUp = true;
		}
		
		color = new Color(0, 0, 0, alpha);
		g2d.setColor(color);
		g2d.fillRect(gameOverRect.x, gameOverRect.y, gameOverRect.width, gameOverRect.height);
		if(alpha < 255) {
			alpha += 2;
//			System.out.println("alpha: " + alpha);
			panel.repaint();
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		g.drawImage(clearImg, SCRW/2 - clearImg.getWidth()/2, 20, clearImg.getWidth(), clearImg.getHeight(), null);
//		g.drawString(str, x, y);
		g.setFont(font);
		g.setColor(Color.WHITE);
		g.drawString(("Clear Time: " + hour + " h " + min + " m " + sec + " s"), 300, 200);
	}
}
