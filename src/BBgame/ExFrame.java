package BBgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class ExFrame extends JFrame {
    MainFrame mf;

    public ExFrame(MainFrame mf, String imagePath) {
        this.mf = mf;
        ImagePanel ePanel = new ImagePanel(imagePath);  // ImagePanel로 변경
        setContentPane(ePanel);
        File fontFile = new File("NanumJangMiCe.ttf");
              
        try {
        	
        	Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile);

            // 다음은 폰트 설정 예시입니다. 원하는 대로 수정하세요.
            Font boldCustomFont = customFont.deriveFont(Font.BOLD, 24f);
            
            Font boldCustomFont1 = customFont.deriveFont(Font.BOLD, 30f);
        	JLabel label1 = new JLabel("게임 설명", SwingConstants.CENTER);
        	label1.setFont(boldCustomFont1);
        	label1.setBounds(220, 50, 500, 80);  // 좌표 및 크기 설정
        	add(label1);
        
        	JLabel label2 = new JLabel("- 1~4명이서 함께 즐길 수 있는 벽돌 깨기 게임입니다", SwingConstants.CENTER);
        	label2.setFont(boldCustomFont);
        	label2.setBounds(220, 150, 500, 80);  // 좌표 및 크기 설정
        	add(label2);
        
        	JLabel label3 = new JLabel("- 벽돌을 깰 때마다 점수가 올라갑니다. 서로 경쟁하세요!", SwingConstants.CENTER);
        	label3.setFont(boldCustomFont);
        	label3.setBounds(220, 210, 500, 80);  // 좌표 및 크기 설정
        	add(label3);
        
        	JLabel label4 = new JLabel("- 좌우 방향키를 통해 패들을 이동할 수 있습니다", SwingConstants.CENTER);
        	label4.setFont(boldCustomFont);
        	label4.setBounds(220, 270, 500, 80);  // 좌표 및 크기 설정
        	add(label4);
        
        	JLabel label5 = new JLabel("- 우측 상단의 버튼을 통해 게임을 일시중지/재개할 수 있습니다", SwingConstants.CENTER);
        	label5.setFont(boldCustomFont);
        	label5.setBounds(220, 330, 500, 80);  // 좌표 및 크기 설정
        	add(label5);
        
        	JLabel label6 = new JLabel("- 벽돌 중 일부는 특별한 이벤트를 가집니다!", SwingConstants.CENTER);
        	label6.setFont(boldCustomFont);
        	label6.setBounds(220, 390, 500, 80);  // 좌표 및 크기 설정
        	add(label6);
        
        	JLabel label7 = new JLabel("- b.size: 공의 크기가 커집니다", SwingConstants.CENTER);
        	label7.setFont(boldCustomFont);
        	label7.setBounds(220, 450, 500, 80);  // 좌표 및 크기 설정
        	add(label7);
        
        	JLabel label8 = new JLabel("- b.speed: 공의 속도가 빨라집니다", SwingConstants.CENTER);
        	label8.setFont(boldCustomFont);
        	label8.setBounds(220, 510, 500, 80);  // 좌표 및 크기 설정
        	add(label8);
        
        	JLabel label9 = new JLabel("- p.size: 패들의 크기가 작아집니다", SwingConstants.CENTER);
        	label9.setFont(boldCustomFont);
        	label9.setBounds(220, 570, 500, 80);  // 좌표 및 크기 설정
        	add(label9);
        
        	JLabel label10 = new JLabel("- p.speed: 패들의 속도가 빨라집니다", SwingConstants.CENTER);
        	label10.setFont(boldCustomFont);
        	label10.setBounds(220, 630, 500, 80);  // 좌표 및 크기 설정
        	add(label10);
        
        	JLabel label11 = new JLabel("이제 게임을 시작해보세요!", SwingConstants.CENTER);
        	label11.setFont(boldCustomFont1);
        	label11.setBounds(220, 720, 500, 80);  // 좌표 및 크기 설정
        	add(label11);
        
        	JButton btn = new JButton("닫기");
        	btn.setFont(boldCustomFont);
        	btn.setBounds(390, 850, 150, 40);
        	add(btn);
        

        	btn.addActionListener(new ActionListener() {
        		public void actionPerformed(ActionEvent e) {
        			setVisible(false);
        		}
        	});
        }catch (FontFormatException | IOException e) {
            e.printStackTrace();
        }
        setSize(1000, 1000);
        setTitle("게임 설명");
        setLocation(300, 300);
        setLayout(null);  // null 레이아웃 설정
    }

    
}