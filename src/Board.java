/**
 * Created by Fantomasa on 19.4.2016 г..
 */
//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.JPanel;

public class Board extends JPanel implements Variables {
    private Timer timer;
    private String message = "Game Over";
    private Ball ball;
    private Paddle paddle;
    private Brick[] bricks;
    private boolean ingame = true;

    public Board() {
        this.initBoard();
    }

    private void initBoard() {
        this.addKeyListener(new Board.TAdapter());
        this.setFocusable(true);
        this.bricks = new Brick[40];
        this.setDoubleBuffered(true);
        this.timer = new Timer();
        this.timer.scheduleAtFixedRate(new Board.ScheduleTask(), 1000L, 4L);
    }

    public void addNotify() {
        super.addNotify();
        this.gameInit();
    }

    private void gameInit() {
        this.ball = new Ball();
        this.paddle = new Paddle();
        int k = 0;

        for(int i = 0; i < 5; ++i) {
            for(int j = 0; j < 8; ++j) {
                this.bricks[k] = new Brick(j * 40 + 30, i * 10 + 50);
                ++k;
            }
        }

    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        if(this.ingame) {
            this.drawObjects(g2d);
        } else {
            this.gameFinished(g2d);
        }

        Toolkit.getDefaultToolkit().sync();
    }

    private void drawObjects(Graphics2D g2d) {
        g2d.drawImage(this.ball.getImage(), this.ball.getX(), this.ball.getY(), this.ball.getWidth(), this.ball.getHeight(), this);
        g2d.drawImage(this.paddle.getImage(), this.paddle.getX(), this.paddle.getY(), this.paddle.getWidth(), this.paddle.getHeight(), this);

        for(int i = 0; i < 40; ++i) {
            if(!this.bricks[i].isDestroyed()) {
                g2d.drawImage(this.bricks[i].getImage(), this.bricks[i].getX(), this.bricks[i].getY(), this.bricks[i].getWidth(), this.bricks[i].getHeight(), this);
            }
        }

    }

    private void gameFinished(Graphics2D g2d) {
        Font font = new Font("Verdana", 1, 18);
        FontMetrics metr = this.getFontMetrics(font);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);
        g2d.drawString(this.message, (380 - metr.stringWidth(this.message)) / 2, 190);
    }

    private void stopGame() {
        this.ingame = false;
        this.timer.cancel();
    }

    private void checkCollision() {
        if(this.ball.getRect().getMaxY() > 390.0D) {
            this.stopGame();
        }

        int i = 0;

        int ballLeft;
        for(ballLeft = 0; i < 40; ++i) {
            if(this.bricks[i].isDestroyed()) {
                ++ballLeft;
            }

            if(ballLeft == 40) {
                this.message = "Victory";
                this.stopGame();
            }
        }

        int ballHeight;
        int ballWidth;
        int ballTop;
        if(this.ball.getRect().intersects(this.paddle.getRect())) {
            i = (int)this.paddle.getRect().getMinX();
            ballLeft = (int)this.ball.getRect().getMinX();
            ballHeight = i + 8;
            ballWidth = i + 16;
            ballTop = i + 24;
            int pointRight = i + 32;
            if(ballLeft < ballHeight) {
                this.ball.setXDir(-1);
                this.ball.setYDir(-1);
            }

            if(ballLeft >= ballHeight && ballLeft < ballWidth) {
                this.ball.setXDir(-1);
                this.ball.setYDir(-1 * this.ball.getYDir());
            }

            if(ballLeft >= ballWidth && ballLeft < ballTop) {
                this.ball.setXDir(0);
                this.ball.setYDir(-1);
            }

            if(ballLeft >= ballTop && ballLeft < pointRight) {
                this.ball.setXDir(1);
                this.ball.setYDir(-1 * this.ball.getYDir());
            }

            if(ballLeft > pointRight) {
                this.ball.setXDir(1);
                this.ball.setYDir(-1);
            }
        }

        for(i = 0; i < 40; ++i) {
            if(this.ball.getRect().intersects(this.bricks[i].getRect())) {
                ballLeft = (int)this.ball.getRect().getMinX();
                ballHeight = (int)this.ball.getRect().getHeight();
                ballWidth = (int)this.ball.getRect().getWidth();
                ballTop = (int)this.ball.getRect().getMinY();
                Point var10 = new Point(ballLeft + ballWidth + 1, ballTop);
                Point pointLeft = new Point(ballLeft - 1, ballTop);
                Point pointTop = new Point(ballLeft, ballTop - 1);
                Point pointBottom = new Point(ballLeft, ballTop + ballHeight + 1);
                if(!this.bricks[i].isDestroyed()) {
                    if(this.bricks[i].getRect().contains(var10)) {
                        this.ball.setXDir(-1);
                    } else if(this.bricks[i].getRect().contains(pointLeft)) {
                        this.ball.setXDir(1);
                    }

                    if(this.bricks[i].getRect().contains(pointTop)) {
                        this.ball.setYDir(1);
                    } else if(this.bricks[i].getRect().contains(pointBottom)) {
                        this.ball.setYDir(-1);
                    }

                    this.bricks[i].setDestroyed(true);
                }
            }
        }

    }

    private class ScheduleTask extends TimerTask {
        private ScheduleTask() {
        }

        public void run() {
            Board.this.ball.move();
            Board.this.paddle.move();
            Board.this.checkCollision();
            Board.this.repaint();
        }
    }

    private class TAdapter extends KeyAdapter {
        private TAdapter() {
        }

        public void keyReleased(KeyEvent e) {
            Board.this.paddle.keyReleased(e);
        }

        public void keyPressed(KeyEvent e) {
            Board.this.paddle.keyPressed(e);
        }
    }
}
