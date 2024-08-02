package BBgame;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.*;

public class StartScreen extends ImagePanel {
    private MainFrame win;

    public StartScreen(MainFrame win, String imagePath) {
        super(imagePath);
        this.win = win;

        setLayout(null);  // null 레이아웃 설정

       
        try {
            // 한글 폰트 파일 경로 설정 (폰트 파일은 프로젝트 폴더에 있어야 함)
            File fontFile = new File("NanumJangMiCe.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 80f);

            // 글꼴 등록
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            JLabel label = new JLabel("벽돌 깨기 게임", SwingConstants.CENTER);
            label.setFont(customFont);
            label.setBounds(250, 200, 500, 80);  // 좌표 및 크기 설정
            add(label);

            JButton startButton = new JButton("게임 시작");
            startButton.setFont(customFont.deriveFont(Font.BOLD,24f));
            startButton.setBounds(400, 700, 200, 50);  // 좌표 및 크기 설정
            startButton.addActionListener(new MyActionListener());
            add(startButton);
            
            JButton ExButton = new JButton("게임 설명");
            ExButton.setFont(customFont.deriveFont(Font.BOLD, 24f));
            ExButton.setBounds(400, 800, 200, 50);  // 좌표 및 크기 설정
            ExButton.addActionListener(new ExActionListener()); // ExButton에 대해 이벤트 리스너 설정
            add(ExButton);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setVisible(true);  // 컴포넌트 표시
    }

    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            win.change("cPanel");
        }
    }
    
    class ExActionListener implements ActionListener {
    	 @Override
    	 public void actionPerformed(ActionEvent e) {
    		 win.ef.setVisible(true);
    	 }
    	
    }
}