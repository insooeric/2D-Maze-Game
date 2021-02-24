package MazeBeta1;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class BGcomponent extends Rectangle{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String path;
	private String wallFile = "wallSprite.png";
	private String groundFile = "Ground.png";
	private BufferedImage img;
	private BufferedImage subImg;
	
	BGcomponent(int id){
		this.path = setLocation();
		
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
	
	String setLocation() {
		//Fixed
		String path = new File("").getAbsolutePath();
		path += "\\Port1\\src\\MazeBeta1\\";
		return path;
	}
	
	void loadImage(String fileName) {
		try {
			img = ImageIO.read(new File(path + fileName));
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
