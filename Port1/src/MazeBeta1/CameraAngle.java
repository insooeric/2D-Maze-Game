package MazeBeta1;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class CameraAngle implements Variable{
	private int init_x;
	private int init_y;
	
	private Rectangle topRect;
	private Rectangle botRect;
	private Rectangle rightRect;
	private Rectangle leftRect;
	
	
	CameraAngle(int x, int y){
		
		this.init_x = x;
		this.init_y = y;
		
		topRect = new Rectangle(0, 0, SCRW, SUB_SCRH);
		botRect = new Rectangle(0, SCRH - SUB_SCRH, SCRW, SUB_SCRH);
		rightRect = new Rectangle(SCRW - SUB_SCRW, 0, SUB_SCRW, SCRH);
		leftRect = new Rectangle(0, 0, SUB_SCRW, SCRH);
	}
	
	int getXpos() {
		return init_x;
	}
	
	int getYpos() {
		return init_y;
	}
	
	void drawSubRect(Graphics g) {
		g.setColor(Color.RED);
		g.drawRect(topRect.x, topRect.y, topRect.width, topRect.height);
		
		g.setColor(Color.BLUE);
		g.drawRect(botRect.x, botRect.y, botRect.width, botRect.height);
		
		g.setColor(Color.WHITE);
		g.drawRect(rightRect.x, rightRect.y, rightRect.width, rightRect.height);
		
		g.setColor(Color.YELLOW);
		g.drawRect(leftRect.x, leftRect.y, leftRect.width, leftRect.height);
	}
	
	Rectangle top() {
		return topRect;
	}
	
	Rectangle bot() {
		return botRect;
	}
	
	Rectangle right() {
		return rightRect;
	}
	
	Rectangle left() {
		return leftRect;
	}
}
