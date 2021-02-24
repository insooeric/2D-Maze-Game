package MazeBeta1;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;

public class BGcomponent extends Rectangle{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private URL path;
	private String wallFile = "wallSprite.png";
	private String groundFile = "Ground.png";
	private BufferedImage img;
	private BufferedImage subImg;
	
	BGcomponent(int id){
		switch(id) {
		case 1:
			loadImage(wallFile);
			subImg = cropImage(img, 144, 128);
			break;
		case 2:
			loadImage(groundFile);
			subImg = img;
			break;
		}
	}
	
	BGcomponent(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	void moveLocation(int x, int y) {
		this.x += x;
		this.y += y;
	}
	
	void loadImage(String fileName) {
		this.path = this.getClass().getResource("/resources/" + fileName);
		try {
			img = ImageIO.read(path);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	BufferedImage cropImage(BufferedImage src, int xcord, int ycord) {
		BufferedImage subImage = src.getSubimage(xcord, ycord, 32, 32);
		return subImage;
	}
	
	BufferedImage getImage() { return subImg; }
	void setImage(BufferedImage img) { this.subImg = img; }
}
