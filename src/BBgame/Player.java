package BBgame;

import java.awt.*;

public class Player {
    private String playerName;
    private int score;
    private int rank;
    
    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }

    public Player(String playerName) {
        this.playerName = playerName;
        this.score = 0;
    }

    public String getPlayerName() {
        return playerName;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }
    
    public void setScore(int score) {
        this.score = score;
    }
    
    public void resetScore() {
    	score = 0;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Score: " + score, 500, 30);
        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("Player: " + playerName, 10, 30);    }
}