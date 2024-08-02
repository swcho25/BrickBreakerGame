package BBgame;
import java.awt.*;

public class BrickWall {
    private int rowCount;
    private int columnCount;
    private int brickWidth;
    private int brickHeight;
    private int[][] brickXArray;
    private int[][] brickYArray;
    private int[][] blockValues;
    private int[][] brickEvents;
    private String[][] brickStrings;
    private String[] eventStrings;
    
    public BrickWall(int rowCount, int columnCount, int brickWidth, int brickHeight) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.brickWidth = brickWidth;
        this.brickHeight = brickHeight;
        this.brickXArray = new int[rowCount][columnCount];
        this.brickYArray = new int[rowCount][columnCount];
        this.blockValues = new int[rowCount][columnCount];
        this.brickEvents = new int[rowCount][columnCount];
        this.eventStrings = new String[]{"p.size", "b.speed", "p.speed", "b.size"};
        this.brickStrings = new String[rowCount][columnCount];        
        initializeBrickArray();
    }
    
    private void initalizeBrickEvent() {
        int eventsPerType = 2; // 각 이벤트당 2개의 벽돌
        
        for (int k = 0; k < eventsPerType; k++) {
            for (int i = 0; i < 4; i++) {
                int randomRow, randomCol;
                do {
                    randomRow = (int) (Math.random() * rowCount);
                    randomCol = (int) (Math.random() * columnCount);
                } while (blockValues[randomRow][randomCol] != 1 || brickStrings[randomRow][randomCol] != null);
                
                brickStrings[randomRow][randomCol] = eventStrings[i];
                brickEvents[randomRow][randomCol] = i + 1;
            }
        }
    }	

    private void initializeBrickArray() {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                brickXArray[i][j] = j * (brickWidth + 30) + 30; // 벽돌 간 간격은 30
                brickYArray[i][j] = i * (brickHeight + 20) + 100; // 벽돌 간 간격은 20, 시작 Y 위치는 100
                blockValues[i][j] = getRandomValue();
                brickEvents[i][j] = 0;
                brickStrings[i][j] = null;
            }
        }
        initalizeBrickEvent();
    }
    
    private int getRandomValue() {
    	double randomNumber = Math.random() * 100;
        
        if (randomNumber < 55) {
            return 1;
        } else if (randomNumber < 85) {
            return 2;
        } else {
            return 3;
        }
    }

    public void draw(Graphics g) {
        for (int i = 0; i < rowCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                g.setColor(new Color(70, 35, 10));
                g.fillRect(brickXArray[i][j], brickYArray[i][j], brickWidth, brickHeight);

                if (blockValues[i][j] == 1) {
                    if (brickStrings[i][j] != null) {
                        // 가중치가 1이면서 이벤트가 있는 경우 이벤트만 출력
                        g.setColor(Color.WHITE);
                        Font newFont = new Font("Arial", Font.BOLD, 16);
                        g.setFont(newFont);

                        int centerX = brickXArray[i][j] + brickWidth / 2 - g.getFontMetrics().stringWidth(brickStrings[i][j]) / 2;
                        int centerY = brickYArray[i][j] + brickHeight / 2 + g.getFontMetrics().getAscent() / 2;

                        g.drawString(brickStrings[i][j], centerX, centerY);
                    } else {
                        // 가중치가 1이면서 이벤트가 없는 경우 가중치 값을 출력
                        g.setColor(Color.WHITE);
                        Font originalFont = g.getFont();
                        Font newFont = originalFont.deriveFont(Font.BOLD, 16f);
                        g.setFont(newFont);
                        g.drawString(String.valueOf(blockValues[i][j]), brickXArray[i][j] + brickWidth / 2 - 6, brickYArray[i][j] + brickHeight / 2 + 6);
                        g.setFont(originalFont);
                    }
                } else if (blockValues[i][j] > 1) {
                    // 가중치가 1이 아니면 기존의 가중치 값 출력
                    g.setColor(Color.WHITE);
                    Font originalFont = g.getFont();
                    Font newFont = originalFont.deriveFont(Font.BOLD, 16f);
                    g.setFont(newFont);
                    g.drawString(String.valueOf(blockValues[i][j]), brickXArray[i][j] + brickWidth / 2 - 6, brickYArray[i][j] + brickHeight / 2 + 6);
                    g.setFont(originalFont);
                }

                if (brickStrings[i][j] != null && blockValues[i][j] != 1) {
                    // 가중치가 1이 아니면서 이벤트가 있는 경우 이벤트를 위에 추가로 출력
                    g.setColor(Color.WHITE);
                    Font newFont2 = new Font("Arial", Font.BOLD, 16);
                    g.setFont(newFont2);

                    int centerX = brickXArray[i][j] + brickWidth / 2 - g.getFontMetrics().stringWidth(brickStrings[i][j]) / 2;
                    int centerY = brickYArray[i][j] + brickHeight / 2 + g.getFontMetrics().getAscent() / 2;

                    g.drawString(brickStrings[i][j], centerX, centerY);
                }
            }
        }
    }

    public void handleCollision(Ball ball, Player player, Paddle paddle) {
        for (int i = 0; i < brickXArray.length; i++) {
            for (int j = 0; j < brickXArray[i].length; j++) {
                if (brickXArray[i][j] != -100) {
                    
                    int ball_intersects = ball.intersects(brickXArray[i][j], brickYArray[i][j], brickWidth, brickHeight);
                    
                    if (ball_intersects == 1)
                        ball.reverseXDirection();
                    if (ball_intersects == 2)
                        ball.reverseYDirection();
                    if (ball_intersects == 3)
                        continue;

                    // 추가: 블록 값 1 감소
                    blockValues[i][j]--;
                    SoundPlayer.playSound("./Sound/brick.wav");

                    // 추가: 블록 이벤트 처리
                    switch (brickEvents[i][j]) {
                        case 1:
                            // 이벤트 1: 패들의 크기 증가
                            paddle.decreaseSize();
                            break;
                        case 2:
                            // 이벤트 2: 공의 속도 증가
                            ball.increaseSpeed();
                            break;
                        case 3:
                            // 이벤트 3: 패들의 이동 속도 증가
                            paddle.increaseSpeed();
                            break;
                        case 4:
                            // 이벤트 4: 공의 크기 증가
                            ball.increaseSize();
                            break;
                        // 다른 이벤트에 대한 처리도 추가할 수 있음
                    }

                    if (blockValues[i][j] <= 0) {
                        // 블록 숨기기
                        brickXArray[i][j] = -100;
                        player.incrementScore();
                    }
                }
            }
        }
    }
    
    public boolean checkBricksRemaining() {
    	for (int i = 0; i < brickXArray.length; i++) {
            for (int j = 0; j < brickXArray[i].length; j++) {
                if (brickXArray[i][j] != -100) {
                    return true; // 아직 벽돌이 남아있음
                }
            }
        }
        return false; // 벽돌이 하나도 남아있지 않음
    }

}