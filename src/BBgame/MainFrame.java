package BBgame;

import java.awt.*;
import javax.swing.*;
import java.io.*;

public class MainFrame extends JFrame {
	public StartScreen sPanel = null;
	public InputPlayerCount cPanel = null;
	public InputPlayerData iPanel = null;
	public BrickBreakerGame bPanel = null;
	public PrintLank pPanel = null;
	public ExFrame ef = null;
	
	private int countPlayer;
	private String Player[];
	
	public void setPlayer(String name, int num) {
		Player[num] = name;
	}
	
	public String getPlayer(int num) {
		return Player[num];
	}
	
	public MainFrame() {
		 try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt", false))) {
             // false 파라미터는 파일을 새로 작성하도록 합니다.
             // 새로운 게임 시작 시 기존 정보를 삭제하고 새로운 내용을 작성합니다.
         } catch (IOException e) {
             e.printStackTrace();
         }
		Player = new String[4];
		for(int i=0;i<4;i++) {
			Player[i]=null;
		}
		this.setTitle("벽돌깨기 게임");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		sPanel = new StartScreen(this, "./image/bg.png");
		cPanel = new InputPlayerCount(this, "./image/bg.png");
		ef = new ExFrame(this, "./image/bg.png");
		//iPanel = new InputPlayerData(this);
		//bPanel = new BrickBreakerGame(this);
		
		add(this.sPanel);
		setSize(1070, 1000);
		setVisible(true);
	}
	
	public int getCountPlayer() {
        return countPlayer;
    }

    public void setCountPlayer(int count) {
        countPlayer = count;
    }
	
	public void change(String panelName) {
		if(panelName.equals("iPanel")) {
			getContentPane().removeAll();
			getContentPane().add(iPanel);
			revalidate();
			repaint();
		}
		else if(panelName.equals("cPanel")) {
			getContentPane().removeAll();
			getContentPane().add(cPanel);
			revalidate();
			repaint();
		}
		else if(panelName.equals("bPanel")) {
			getContentPane().removeAll();
			getContentPane().add(bPanel);
			revalidate();
			repaint();
		}
		else if(panelName.equals("pPanel")) {
			getContentPane().removeAll();
			getContentPane().add(pPanel);
			revalidate();
			repaint();
		}
	}
	
	public static void main(String[] args) {
	       new MainFrame();
	}  
}