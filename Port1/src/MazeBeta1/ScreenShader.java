package MazeBeta1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList; 

public class ScreenShader implements Variable{
	private Area circle;
	private Area shadeRect;
	private AffineTransform trans = new AffineTransform();
	private int circum;
	
	ArrayList<Rectangle> shadList = new ArrayList<Rectangle>();
	
	ScreenShader(int x, int y, int r){
		circle = new Area( new Ellipse2D.Double(x, y, r*2, r*2));
		shadeRect = new Area(new Rectangle(0, 0, SCRW, SCRH));
		shadeRect.subtract(circle);
	}
	
	void moveShade(int x, int y) {
		shadeRect = new Area(new Rectangle(0, 0, SCRW, SCRH));
		trans.translate(x, y);
		circle.transform(trans);
		shadeRect.subtract(circle);
		trans.translate(-x, -y);
	}
	
	void paintShader(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.fill(shadeRect);
	}
}
