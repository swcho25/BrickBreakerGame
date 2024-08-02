package BBgame;

import javax.swing.*;

import BBgame.StartScreen.MyActionListener;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

public class PrintLank extends ImagePanel {
    private MainFrame win;

    public PrintLank(MainFrame win, String imagePath) {
        super(imagePath);
        this.win = win;
        setLayout(null);  // null 레이아웃 설정

        
        try {

            // 한글 폰트 파일 경로 설정 (폰트 파일은 프로젝트 폴더에 있어야 함)
            File fontFile = new File("NanumJangMiCe.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 50f);

            // 글꼴 등록
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            JLabel label = new JLabel("점수 확인", SwingConstants.CENTER);
            label.setFont(customFont);
            label.setBounds(455, 390, 200, 50);  // 좌표 및 크기 설정
            add(label);
            
         // 파일에서 데이터를 읽어와서 출력
            displayScores(480, customFont);

            JButton startButton = new JButton("종료");
            startButton.setFont(customFont.deriveFont(Font.BOLD,30f));
            startButton.setBounds(455, 780, 200, 50);  // 좌표 및 크기 설정
            startButton.addActionListener(new MyActionListener());
            add(startButton);

            setVisible(true);  // 컴포넌트 표시

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
        // 상단 여백
    }

    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    }

    // 파일에서 데이터를 읽어와서 출력하는 메서드
    private void displayScores(int startY, Font customFont) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("scores.txt"));
            String line;
            
            // 파일에서 한 줄씩 읽어오면서 출력
            while ((line = reader.readLine()) != null) {
                JLabel scoreLabel = new JLabel(line, SwingConstants.CENTER);
                scoreLabel.setFont(customFont.deriveFont(Font.BOLD, 35f));  // 한글 폰트 설정
                scoreLabel.setBounds(455, startY, 200, 40);  // 좌표 및 크기 설정
                add(scoreLabel);
                startY += 60;
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}