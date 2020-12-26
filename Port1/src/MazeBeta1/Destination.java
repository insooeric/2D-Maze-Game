package MazeBeta1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.awt.image.WritableRaster;
import java.util.ArrayList;

public class Destination implements Variable {
	private BufferedImage originImg;
	private BufferedImage transImg;
	private Rectangle rect;
	private int[][] pixelArray;

	Destination(BufferedImage img, Rectangle rect) {
		System.out.println("scale");
		this.rect = new Rectangle(rect);
		originImg = img;
		transImg = new BufferedImage(BLOCKW, BLOCKH, BufferedImage.TYPE_INT_RGB);

		pixelArray = new int[img.getWidth()][img.getHeight()];
		imgToPixel(originImg);
		translateImg(originImg);
		transImg = getImageFromArray(pixelArray, img.getWidth(), img.getHeight());
	}

	void imgToPixel(BufferedImage img) {
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				/*
				 * a = (0xff000000 & color) >>> 24; r = (0x00ff0000 & color) >> 16; g =
				 * (0x0000ff00 & color) >> 8; b = (0x000000ff & color);
				 */
				pixelArray[i][j] = img.getRGB(j, i);
			}
		}
	}

	BufferedImage getImageFromArray(int[][] pixels, int width, int height) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				image.setRGB(i, j, pixels[j][i]);
			}
		}
		return image;
	}

	void translateImg(BufferedImage img) {
		int a, r, g, b;
		int w = RGB_MAX;
		int color;
		for (int i = 0; i < img.getHeight(); i++) {
			for (int j = 0; j < img.getWidth(); j++) {
				color = pixelArray[i][j];
				a = ((0xff000000 & color) >>> 24);
				r = ((0x00ff0000 & color) >> 16);
				g = ((0x0000ff00 & color) >> 8);
				b = ((0x000000ff & color));
				r += w;
				g += w;
				b += w;
				if(r > 255) {
					r = 255;
				} else {
					r -= w;
				}
				if(g > 255) {
					g = 255;
				} else {
					g -= w;
				}
				if(b > 255) {
					b = 255;
				} else {
					b -= w;
				}
				color = (0 << 24) + (r << 16) + (g << 8) + (b);
				pixelArray[i][j] = color;
			}
			w = RGB_MAX - i;
		}
	}

	void moveRect(int x, int y) {
		rect.x += x;
		rect.y += y;
	}

	void drawRect(Graphics g) {
		g.drawImage(transImg, rect.x, rect.y, rect.width, rect.height, null);
//		g.drawRect(rect.x, rect.y, rect.width, rect.height);
	}
	
	void drawExitRect(Graphics g, BGcomponent rect) {
		g.setColor(Color.WHITE);
		g.fillRect(rect.x, rect.y, rect.width, rect.height);
		g.setColor(Color.BLACK);
		g.fillRect(rect.x, rect.y, rect.width, 10);
	}
}