package BBgame;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Paddle {
    private int x;
    private int y;
    private int width;
    private int height;
    private int xSpeed;
    private boolean moving;
    
    public int getPaddleX() {
    	return x;
    }
    
    public int getPaddleY() {
    	return y;
    }
    
    public int getPaddleW() {
    	return width;
    }
    public Paddle(int initialX) {
        this.x = initialX;
        this.y = 950;
        this.width = 140;
        this.height = 10;
        this.xSpeed = 20;
        this.moving = true;
    }

    public void moveLeft() {
    	if(moving) {
        	x -= xSpeed;
        	if (x < 0) {
        		x = 0;
        	}
    	}
    }

    public void moveRight(int panelWidth) { 
        if(moving) {
    		x += xSpeed;
        	if (x > panelWidth - width) {
        		x = panelWidth - width;
        	}
        }
    }
    
    public void pause() {
        moving = false;
    }

    public void resume() {
        moving = true;
    }

    public void draw(Graphics g) {
    	g.setColor(new Color(139, 69, 19));
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    public void handleKeyPress(KeyEvent e, int panelWidth) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            moveRight(panelWidth);
        }
    }
    
    public void decreaseSize() {  
    	this.width -= 50; 
    }
    
    public void increaseSpeed() {
    	if(this.xSpeed<50) {
    		this.xSpeed += 10;
    	}
    }

    public void handleKeyRelease(KeyEvent e) {
        // 키 떼어짐 처리 로직
        // ...
    }
}