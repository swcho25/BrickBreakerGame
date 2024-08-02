package BBgame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import javax.swing.*;

public class InputPlayerCount extends ImagePanel {
    private JComboBox<Integer> playerNumberBox;
    private JButton confirmButton;
    private MainFrame win;

    public InputPlayerCount(MainFrame win, String imagePath) {
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

            JLabel label = new JLabel("플레이 할 인원을 선택해주세요!", SwingConstants.CENTER);
            label.setFont(customFont);
            label.setBounds(250, 200, 500, 80);  // 좌표 및 크기 설정
            add(label);

            // 콤보 박스 설정
            Integer[] playerNumbers = {1, 2, 3, 4};
            playerNumberBox = new JComboBox<>(playerNumbers);
            playerNumberBox.setBounds(445, 500, 120, 50);  // 좌표 및 크기 설정
            playerNumberBox.setFont(customFont.deriveFont(Font.BOLD, 24f));  // 폰트 크기 조절
            add(playerNumberBox);

            // 콤보 박스 내부의 글자를 가운데 정렬하기 위해 DefaultListCellRenderer 사용
            DefaultListCellRenderer renderer = new DefaultListCellRenderer();
            renderer.setHorizontalAlignment(SwingConstants.CENTER);
            playerNumberBox.setRenderer(renderer);

            // 확인 버튼 설정
            confirmButton = new JButton("확인");
            confirmButton.addActionListener(new MyActionListener());
            confirmButton.setBounds(455, 570, 100, 40);  // 좌표 및 크기 설정
            confirmButton.setFont(customFont.deriveFont(24f));  // 폰트 크기 조절
            add(confirmButton);

        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }

        setVisible(true);  // 컴포넌트 표시
    }

    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int playerCount = (int) playerNumberBox.getSelectedItem();
            win.setCountPlayer(playerCount);
            win.iPanel = new InputPlayerData(win, "./image/bg.png");
            win.change("iPanel");
        }
    }
}