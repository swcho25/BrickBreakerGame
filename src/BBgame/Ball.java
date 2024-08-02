package BBgame;

import java.awt.*;

import javax.swing.ImageIcon;

public class Ball {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;
    private int diameter;
    private Image image;
    private boolean moving;

    public Ball() {
        this.x = (int)(Math.random()*(870)+100);
        this.y = 450;
        this.xSpeed = 5;
        this.ySpeed = 5;
        this.diameter = 20;
       
        ImageIcon icon = new ImageIcon("./image/ssbg.png");
        this.image = icon.getImage();
        this.moving = true;
    }
    
    public int getX() {
    	return x;
    }
    public int getY() {
    	return y;
    }
    public int getXSpeed() {
    	return xSpeed;
    }
    public int getYSpeed() {
    	return ySpeed;
    }
    public int getDiameter() {
    	return diameter;
    }

    public void move() {
    	if(moving) {
    		x += xSpeed;
    		y += ySpeed;
    	}
    }
    
    public void pause() {
        moving = false;
        ImageIcon icon = new ImageIcon("./image/DGGupssbg.png");
        this.image = icon.getImage();
    }

    public void resume() {
        moving = true;
        ImageIcon icon = new ImageIcon("./image/ssbg.png");
        this.image = icon.getImage();
    }	
    
    public int move(int paddleX, int paddleY, int paddleW) {
        	if (x <= 0 || x >= 1070-diameter-(diameter/2)) { // 화면 크기 수정
        	
            	xSpeed = -xSpeed;
        	}
        	if (y <= 0) {
        		ySpeed = -ySpeed;
        	}
        	if (y >= 950-diameter && x >= paddleX && x <= paddleX + paddleW) {
        		ySpeed = -ySpeed;
        	}
        	if(y>990) {
        		return 1;
        	}
        return 0;
    }
    
    public void increaseSpeed() {
    	this.xSpeed++;
    	this.ySpeed++;
    }

    public void increaseSize() {
    	this.diameter += 10; 
    }
    public void draw(Graphics g) {
    	g.drawImage(image, x, y, diameter, diameter, null);
    }
    
    public void reverseXDirection() {
    	xSpeed = -xSpeed;
    }
    
    public void reverseYDirection() {
    	ySpeed = -ySpeed;
    }
    
    public int intersects(int rx, int ry, int rWidth, int rHeight) {
        // 원의 중심 좌표와 반지름 계산
        int circleX = x + diameter / 2;
        int circleY = y + diameter / 2;
        int circleRadius = diameter / 2;

        // 사각형의 중심 좌표 계산
        int rectCenterX = rx + rWidth / 2;
        int rectCenterY = ry + rHeight / 2;

        // 원과 사각형 간의 거리 계산
        int distanceX = Math.abs(circleX - rectCenterX);
        int distanceY = Math.abs(circleY - rectCenterY);

        // 사각형의 반너비와 반높이 계산
        int halfRectWidth = rWidth / 2;
        int halfRectHeight = rHeight / 2;

        // 상하측면 충돌 판정
        if (distanceX <= halfRectWidth + circleRadius) {
            if (distanceY <= halfRectHeight) {
                return 1;  // 상하측면 충돌
            }
        }

        // 좌우측면 충돌 판정
        if (distanceY <= halfRectHeight + circleRadius) {
            if (distanceX <= halfRectWidth) {
            	return 2;  // 좌우측면 충돌
            }
        }       
        return 3;
    }
    
}