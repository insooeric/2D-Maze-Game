package MazeBeta1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class PlayerSprite extends Rectangle implements Variable{
	
	private String path;
	private String playerFile = "charecter.png";
	private BufferedImage img;
	private BufferedImage subImg;
	
	private Rectangle topRect = new Rectangle();
	private Rectangle botRect = new Rectangle();
	private Rectangle bOutRect = new Rectangle();
	
	private ArrayList<BufferedImage> playerImg = new ArrayList<BufferedImage>();
	
	PlayerSprite(int x, int y){
		this.path = setLocation();
		this.x = x;
		this.y = y;
		this.width = PLAYERW;
		this.height = PLAYERH;
		
		setSecRect();
		loadImage(playerFile);
		
		for(int j = 0; j < 8; j++) {
			for(int i = 0; i < 9; i++) {
				subImg = new BufferedImage(PLAYERW_CROP, PLAYERH_CROP, 2);
				subImg = cropImage(img, i * PLAYERW_CROP, j * PLAYERH_CROP);
				playerImg.add(subImg);
			}
		}
	}
	
	void setSecRect() {
		
		this.topRect.x = this.x;
		this.topRect.y = this.y;
		this.topRect.width = this.width;
		this.topRect.height = (this.height/4) * 3;
		
		this.botRect.x = this.x;
		this.botRect.y = this.y + topRect.height;
		this.botRect.width = this.width;
		this.botRect.height = (this.height/4);
		
		this.bOutRect.x = this.x;
		this.bOutRect.y = this.y + PLAYERH;
		this.bOutRect.width = this.width;
		this.bOutRect.height = 10;
	}
	
	String setLocation() {
		String path = this.getClass().getResource("").getPath();
		path = path.replaceAll("%20", " ");
		path = path.replaceFirst(("/bin/" + this.getClass().getPackageName()), "/src/" + this.getClass().getPackageName());
		path = path.substring(1);
		return path;
	}
	
	BufferedImage cropImage(BufferedImage src, int xcord, int ycord) {
		BufferedImage subImage = src.getSubimage(xcord, ycord, PLAYERW_CROP, PLAYERH_CROP);
		return subImage;
	}
	
	void loadImage(String fileName) {
		try {
			img = ImageIO.read(new File(path + fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	void movePlayer(int xpos, int ypos) {
		this.x += xpos;
		this.y += ypos;
		
		this.topRect.x += xpos;
		this.topRect.y += ypos;
		
		this.botRect.x += xpos;
		this.botRect.y += ypos;
		
		this.bOutRect.x += xpos;
		this.bOutRect.y += ypos;
	}
	
	Rectangle getTopRect() {
		return topRect;
	}
	
	Rectangle getBotRect() {
		return botRect;
	}
	
	Rectangle getbOutRect() {
		return bOutRect;
	}
	
	
	void drawExtraRect(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect(topRect.x, topRect.y, topRect.width, topRect.height);
		g.setColor(Color.BLUE);
		g.drawRect(botRect.x, botRect.y, botRect.width, botRect.height);
		g.setColor(Color.RED);
		g.drawRect(bOutRect.x, bOutRect.y, bOutRect.width, bOutRect.height);
	}
	
	BufferedImage getImage() {
		return subImg;
	}
	
	void setMotion(int n) {
		subImg = playerImg.get(n);
	}
	
	ArrayList<BufferedImage> getSprite() {
		return playerImg;
	}
}
