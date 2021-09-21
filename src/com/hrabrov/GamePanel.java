package com.hrabrov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class GamePanel extends JPanel implements ActionListener {
    private final int SCREEN_WIDTH_SIZE = 600;
    private final int SCREEN_HEIGHT_SIZE = 600;
    private final int UNIT_SIZE = 25;

    final int GAME_UNITS = (SCREEN_WIDTH_SIZE * SCREEN_HEIGHT_SIZE) / ((int) Math.pow(UNIT_SIZE, 2));
    final int DELAY = 100;

    char direction = 'R';
    boolean running = false;
    Timer timer;

    Snake snake = new Snake();
    Apple apple = new Apple(SCREEN_WIDTH_SIZE, SCREEN_HEIGHT_SIZE, UNIT_SIZE);

    GamePanel() {
        this.setBackground(Color.gray);
        this.setPreferredSize(new Dimension(SCREEN_WIDTH_SIZE, SCREEN_HEIGHT_SIZE));
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.lightGray);
        for (int i = 0; i < SCREEN_HEIGHT_SIZE / UNIT_SIZE; i++) {
            g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT_SIZE);
            g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH_SIZE, i * UNIT_SIZE);
        }

        if (running) {
            g.setColor(Color.red);
            g.fillOval(apple.getApplePositionX(), apple.getAppleY(), UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < snake.getBodyParts(); i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(54, 182, 5));
                }
                g.fillRect(positionXPartsBody[i], positionYPartsBody[i], UNIT_SIZE, UNIT_SIZE);
            }
        } else {
            gameOver(g);
        }

        drawScore(g);
    }

    public void drawScore(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + snake.applesEaten,
                (SCREEN_WIDTH_SIZE - metrics.stringWidth("Score: " + snake.applesEaten)) / 2,
                g.getFont().getSize());
    }

    public void startGame() {
        apple.getPosition();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }


    public void checkApple() {
        if ((positionXPartsBody[0] == apple.getApplePositionX()) && positionYPartsBody[0] == apple.getAppleY()) {
            snake.setBodyParts(snake.getBodyParts() + 1);
            snake.applesEaten++;
            apple.getPosition();
        }
    }

    public void checkCollisions() {
        //check if head collides with body
        for (int i = snake.getBodyParts(); i > 0; i--) {
            if ((positionXPartsBody[0] == positionXPartsBody[i])
                    && (positionYPartsBody[0] == positionYPartsBody[i])) {
                running = false;
            }
        }

        //check if head touches left border
        if (positionXPartsBody[0] < 0) {
            running = false;
        }

        //check if head touches right border
        if (positionXPartsBody[0] > SCREEN_WIDTH_SIZE) {
            running = false;
        }

        //check if head touches top border
        if (positionYPartsBody[0] < 0) {
            running = false;
        }

        //check if head touches bottom border
        if (positionYPartsBody[0] > SCREEN_HEIGHT_SIZE) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        g.setColor(Color.red);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metricsOfGameOver = getFontMetrics(g.getFont());
        g.drawString("Game Over",
                (SCREEN_WIDTH_SIZE - metricsOfGameOver.stringWidth("Game Over")) / 2, SCREEN_HEIGHT_SIZE / 2);

        drawScore(g);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }

        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }

    final private int[] positionXPartsBody = new int[GAME_UNITS];
    final private int[] positionYPartsBody = new int[GAME_UNITS];

    public void move() {
        for (int i = snake.getBodyParts(); i > 0; i--) {
            positionXPartsBody[i] = positionXPartsBody[i - 1];
            positionYPartsBody[i] = positionYPartsBody[i - 1];
        }

        switch (direction) {
            case 'U':
                positionYPartsBody[0] = positionYPartsBody[0] - UNIT_SIZE;
                break;
            case 'D':
                positionYPartsBody[0] = positionYPartsBody[0] + UNIT_SIZE;
                break;
            case 'L':
                positionXPartsBody[0] = positionXPartsBody[0] - UNIT_SIZE;
                break;
            case 'R':
                positionXPartsBody[0] = positionXPartsBody[0] + UNIT_SIZE;
                break;
        }
    }
}
