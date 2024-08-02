package BBgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;

// BrickBreakerGame 클래스 정의
public class BrickBreakerGame extends JPanel implements ActionListener, KeyListener, Runnable{

	// 플레이어 배열 선언
	private Player[] players;
	// 현재 차례의 플레이어를 나타내는 인덱스
	private int currentPlayerIndex;
	// Ball, BrickWall, Paddle 객체 선언
	private Ball ball;
	private BrickWall brickWall;
	private Paddle paddle;
	// 총 플레이어 수를 저장하는 변수
	private int totalPlayers;
	// 게임 시작 여부를 확인하는 변수
	private boolean gameStarted;
	// 게임 종료 여부를 확인하는 변수
	private boolean gameOver;
	// 현재 게임 진행 횟수를 확인하는 변수
	private static int countgameplay = 0;
	private Image BgImage;
	private MainFrame win;

	private JButton pauseButton;  // 일시정지 버튼
    private boolean paused = false;  // 일시정지 상태를 나타내는 변수
    private boolean pauseButtonState = false;// 버튼의 현재 이미지 나타내는 변수
    private boolean gameEnded = false;
    
	// BrickBreakerGame 생성자
	public BrickBreakerGame(MainFrame win) {
		super();
		this.win = win;
		pauseButton = new JButton(new ImageIcon(new ImageIcon("./image/ssbg.png").getImage()
			    .getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
			pauseButton.setBounds(1000, 10, 50, 50);
	     pauseButton.addActionListener(new ActionListener() {
	     @Override
	     public void actionPerformed(ActionEvent e) {
	         // 일시정지 버튼 클릭 시 동작
	         togglePause();
	         updatePauseButtonImage(); // 일시정지 버튼 이미지 업데이트
	         }
	     });
	     
	     // 프레임에 버튼 추가
	     this.setLayout(null);  
	     this.add(pauseButton);
	        
		ImageIcon icon= new ImageIcon("./image/bg.gif");
		BgImage = icon.getImage();
		// 전체 플레이어 수 초기화
		this.totalPlayers = win.getCountPlayer();
		//플레이어 배열 초기화
		players = new Player[4];
		for(int i=0;i<4;i++) {
			players[i]=null;
		}
		//각 플레이어에 대해 이름을 입력받아 Player 객체 생성 및 배열에 저장
		for (int i = 0; i < totalPlayers; i++) {
			String playerName = win.getPlayer(i);
			players[i] = new Player(playerName);
		}
		// 게임 시작 여부 초기화
		gameStarted = false;
		gameOver = false;

		// 현재 차례의 플레이어 인덱스 초기화
		currentPlayerIndex = 0;
		// Ball, BrickWall, Paddle 객체 생성
		ball = new Ball();
		brickWall = new BrickWall(5, 8, 100, 30);
		paddle = new Paddle(450);

		// 타이머 생성 및 5밀리초 간격으로 actionPerformed 메서드 호출 설정
		Timer timer = new Timer(5, this);
		timer.start();

		// 키 입력 처리를 위한 KeyListener 추가 및 포커스 설정
		addKeyListener(this);
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);
		showGameStartDialog();	
		
	    }	
	private void updatePauseButtonImage() {
		if (pauseButtonState) {
		    pauseButton.setIcon(new ImageIcon(new ImageIcon("./image/ssbg.png").getImage()
		            .getScaledInstance(50, 50, Image.SCALE_SMOOTH))); // 사진1
		} else {
		    pauseButton.setIcon(new ImageIcon(new ImageIcon("./image/DGGupssbg.png").getImage()
		            .getScaledInstance(50, 50, Image.SCALE_SMOOTH))); // 사진2
		}
        pauseButtonState = !pauseButtonState; // 상태 변경
    }
	
	 private void togglePause() {
		 paused = !paused;  // 일시정지 상태 변경

		    if (paused) {
		        // 일시정지 시에 수행할 로직 추가
		        // 공과 패들의 움직임을 멈추는 등의 작업이 들어갑니다.
		        ball.pause();
		        paddle.pause();
		    } else {
		        // 재개 시에 수행할 로직 추가
		        // 멈춘 공과 패들의 움직임을 다시 시작하는 등의 작업이 들어갑니다.
		        ball.resume();
		        paddle.resume();
		    }
		    setFocusable(true);
		    requestFocusInWindow();
	    }
	 
	 public void run() {
		 while (true) {
		        if (!paused) {
		            updateGame();  // 게임 상태 업데이트
		            repaint();     // 화면 다시 그리기
		        }

		        try {
		            Thread.sleep(5);  // 적절한 시간 간격을 두어 게임 루프를 조절
		        } catch (InterruptedException e) {
		            e.printStackTrace();
		        }
		    }
	    }
	 
	 private void updateGame() {
		    // 게임 상태를 업데이트하는 작업 수행
		    int paddleX = paddle.getPaddleX();
		    int paddleY = paddle.getPaddleY();
		    int paddleW = paddle.getPaddleW();
		    ball.move();
		    boolean anyBrickRemaining = brickWall.checkBricksRemaining();

		    if (!anyBrickRemaining) {
		        handleGameEnd();
		    }

		    // Ball과 BrickWall 충돌 시, 충돌 처리 및 플레이어 점수 증가
		    brickWall.handleCollision(ball, players[currentPlayerIndex], paddle);

		    int underpanel = 0;
		    underpanel = ball.move(paddleX, paddleY, paddleW);
		    if (underpanel == 1)
		        handleGameEnd();
		}	

	 private void showGameStartDialog() {
		    // 현재 차례의 플레이어의 이름을 가져와서 메시지 생성
		    while (players[currentPlayerIndex] == null) {
		        currentPlayerIndex++; // 다음 플레이어로 이동
		        if (currentPlayerIndex >= totalPlayers) {
		            currentPlayerIndex = 0; // 인덱스가 배열을 넘어갈 경우 처음 플레이어로 돌아감
		        }
		    }

		    String currentPlayerName = players[currentPlayerIndex].getPlayerName();
		    String message = currentPlayerName + " 게임을 시작하겠습니다.";
		    JOptionPane.showMessageDialog(this, message, "게임 시작", JOptionPane.INFORMATION_MESSAGE);

		    gameStarted = true;
		    countgameplay++;
		}

	// JPanel의 paintComponent 메서드 오버라이드
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		 g.drawImage(BgImage, 0, 0, getWidth(), getHeight(), this);
		// 현재 차례의 플레이어, Ball, BrickWall, Paddle을 그림
		players[currentPlayerIndex].draw(g);
		ball.draw(g);
		brickWall.draw(g);
		paddle.draw(g);
	}

	// ActionListener의 actionPerformed 메서드 구현
	public void actionPerformed(ActionEvent e) {
		// Ball과 Paddle 이동
		if (gameStarted && !gameOver) {
			int paddleX = paddle.getPaddleX();
			int paddleY = paddle.getPaddleY();
			int paddleW = paddle.getPaddleW();
			ball.move();
			boolean anyBrickRemaining = brickWall.checkBricksRemaining();
			//paddle.move();

			if (!anyBrickRemaining) {
				paused = !paused;
				handleGameEnd();
			}

			// Ball과 BrickWall 충돌 시, 충돌 처리 및 플레이어 점수 증가
			brickWall.handleCollision(ball, players[currentPlayerIndex], paddle);

			int underpanel = 0;

			underpanel = ball.move(paddleX, paddleY, paddleW);
			if (underpanel == 1) {
		        paused = !paused;
				handleGameEnd();
			}
			// 화면 갱신
			repaint();
		}
	}

	// KeyListener의 keyPressed 메서드 구현
	public void keyPressed(KeyEvent e) {
		// Paddle 의 키 입력 처리
		paddle.handleKeyPress(e, 1070);
	}
	
	// KeyListener의 keyTyped 메서드 구현
	public void keyTyped(KeyEvent e) {
	}

	// KeyListener의 keyReleased 메서드 구현
	public void keyReleased(KeyEvent e) {
	}

	// 게임 종료 여부를 묻고 처리하는 메서드
	public void handleGameEnd() {
	    String currentPlayerName = players[currentPlayerIndex].getPlayerName();

	    if (!gameEnded && countgameplay == totalPlayers) {
	        // 마지막 플레이어까지 게임이 끝나면 배열을 정렬하고 파일에 저장
	        players[currentPlayerIndex].setScore(players[currentPlayerIndex].getScore());
	        players = Arrays.stream(players)
	                .filter(Objects::nonNull)
	                .toArray(Player[]::new);
	Arrays.sort(players, Comparator.comparingInt(Player::getScore).reversed());
	        // 배열의 내용을 파일에 저장
	        savePlayerInfoArrayToFile(players);

	        gameEnded = true;

	        SwingUtilities.invokeLater(() -> {
	            JOptionPane.showMessageDialog(this, "모든 게임이 종료되었습니다. 결과를 확인해주세요!", "게임 종료",
	                    JOptionPane.INFORMATION_MESSAGE);
	            win.pPanel = new PrintLank(win, "./image/result.gif");
	            win.change("pPanel");
	        });
	    } else if (!gameEnded && countgameplay != totalPlayers) {
	        String message = currentPlayerName + " 게임이 종료되었습니다.";
	        JOptionPane.showMessageDialog(this, message, "게임 종료", JOptionPane.INFORMATION_MESSAGE);
	        currentPlayerIndex++;
	        gameStarted = true;
	        gameOver = false;
	        showGameStartDialog();
	        resetGame();
	    }
	}
	
	private void savePlayerInfoArrayToFile(Player[] players) {
	    try (BufferedWriter writer = new BufferedWriter(new FileWriter("scores.txt", true))) {
	        // 점수가 같은 플레이어에 대해 등수 부여
	        Arrays.sort(players, Comparator.comparingInt(Player::getScore).reversed()
	                                      .thenComparingInt(Player::hashCode));

	        int currentRank = 1;
	        int currentScore = players[0].getScore();

	        for (Player player : players) {
	            if (player != null) {
	                if (player.getScore() != currentScore) {
	                    // 점수가 바뀌면 등수 갱신
	                    currentRank++;
	                    currentScore = player.getScore();
	                }
	                player.setRank(currentRank);
	                writer.write(player.getRank() + ". " + player.getPlayerName() + ": " + player.getScore());
	                writer.newLine();
	            }
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

	// 게임을 초기화하는 메서드
	private void resetGame() {
		// 각 플레이어의 점수 초기화
		gameOver = false;

		// BrickWall을 새로 생성하여 게임을 다시 시작
		brickWall = new BrickWall(5, 8, 100, 30);
		ball = new Ball();
		paddle = new Paddle(450);

	}
}
