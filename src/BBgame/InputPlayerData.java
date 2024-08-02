package BBgame;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;

class InputPlayerData extends ImagePanel {

    private MainFrame win;
    private JTextField[] nameFields;

    public InputPlayerData(MainFrame win, String imagePath) {
        super(imagePath); // 이미지 파일 경로를 적절하게 수정하세요
        this.win = win;
        setLayout(new GridBagLayout());
        int num = win.getCountPlayer();
        nameFields = new JTextField[num]; // 최대 4명의 플레이어를 위한 텍스트 필드 배열
        GridBagConstraints c = new GridBagConstraints();

        try {
            // 한글 폰트 파일 경로 설정 (폰트 파일은 프로젝트 폴더에 있어야 함)
            File fontFile = new File("NanumJangMiCe.ttf");
            Font customFont = Font.createFont(Font.TRUETYPE_FONT, fontFile).deriveFont(Font.BOLD, 30f);

            // 글꼴 등록
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(customFont);

            // "플레이어 이름을 입력하세요!" 문구 추가
            JLabel label_intro = new JLabel("플레이어 이름을 입력하세요: ");
            label_intro.setFont(customFont);
            c.gridx = 1; // x 좌표 설정
            c.gridy = 0;
            c.insets = new Insets(10, 0, 10, 0); // 여백 설정
            add(label_intro, c);

            for (int i = 0; i < num; i++) {
                nameFields[i] = new JTextField(15); // 텍스트 필드의 길이를 15으로 설정
                nameFields[i].setFont(customFont); // 폰트 설정
                c.gridx = 1; // x 좌표 설정
                c.gridy = i + 1;
                c.insets = new Insets(10, 0, 10, 0); // 여백 설정
                add(nameFields[i], c);
            }

            JButton confirmButton = new JButton("확인");
            confirmButton.setFont(customFont); // 폰트 설정
            c.gridx = 1; // x 좌표 설정
            c.gridy = 5;
            c.insets = new Insets(10, 0, 10, 0); // 여백 설정
            add(confirmButton, c);

            confirmButton.addActionListener(new MyActionListener());
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    class MyActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 중복 체크를 위한 Set 생성
            Set<String> playerNameSet = new HashSet<>();

            for (int i = 0; i < nameFields.length; i++) {
                String playerName = nameFields[i].getText().trim(); // 입력값 앞뒤 공백 제거

                // 빈 텍스트 필드 체크
                if (playerName.isEmpty()) {
                    // 빈 텍스트 필드가 있을 경우 경고 메시지 출력
                    JOptionPane.showMessageDialog(win, "플레이어 이름을 모두 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
                    return; // 중복이 발생하면 메서드 종료
                }

                // 중복 체크
                if (!playerNameSet.add(playerName)) {
                    // 중복된 이름이 있다면 경고 메시지 출력
                    int option = JOptionPane.showOptionDialog(win,
                            "중복된 플레이어 이름이 있습니다. 다시 입력하시겠습니까?",
                            "경고", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);

                    if (option == JOptionPane.YES_OPTION) {
                        // 사용자가 다시 입력하겠다고 선택한 경우, 현재 텍스트 필드를 초기화
                        for (JTextField textField : nameFields) {
                            textField.setText("");
                        }
                        return; // 중복이 발생하면 메서드 종료
                    }
                }

                win.setPlayer(playerName, i);
            }
            win.bPanel = new BrickBreakerGame(win);
            win.change("bPanel");
        }
    }
}