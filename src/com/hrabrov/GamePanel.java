package com.hrabrov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    GamePanel() {
        setPreferredSize(new Dimension(board.getScreenWidthSize(), board.getScreenHeightSize()));
        setBackground(Color.gray);
        setFocusable(true);
        addKeyListener(new MyKeyAdapter());
        startGame();
    }

    Random random = new Random();
    Snake snake = new Snake();
    Board board = new Board();
    Apple apple = new Apple();
    boolean running = false;
    Timer timer;
    final int DELAY = 100;


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.lightGray);
        for (int i = 0; i < board.getScreenHeightSize() / board.getUnitSize(); i++) {
            g.drawLine(i * board.getUnitSize(), 0, i * board.getUnitSize(), board.getScreenHeightSize());
            g.drawLine(0, i * board.getUnitSize(), board.getScreenWidthSize(), i * board.getUnitSize());
        }

        if (running) {
            g.setColor(Color.red);
            g.fillOval(apple.getApplePositionX(), apple.getApplePositionY(), board.getUnitSize(), board.getUnitSize());

            for (int i = 0; i < snake.getBodyParts(); i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(54, 182, 5));
                }
                g.fillRect(positionXPartsBody[i], positionYPartsBody[i], board.getUnitSize(), board.getUnitSize());
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
                (board.getScreenWidthSize() - metrics.stringWidth("Score: " + snake.applesEaten)) / 2,
                g.getFont().getSize());
    }

    public void startGame() {
        newApplePosition();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void newApplePosition() {
        apple.setApplePositionX(random.nextInt((int) (board.getScreenWidthSize() /
                board.getUnitSize())) * board.getUnitSize());
        apple.setApplePositionY(random.nextInt((int) (board.getScreenHeightSize() /
                board.getUnitSize())) * board.getUnitSize());
    }

    public void checkApple() {
        if ((positionXPartsBody[0] == apple.getApplePositionX()) && positionYPartsBody[0] == apple.getApplePositionY()) {
            snake.setBodyParts(snake.getBodyParts() + 1);
            snake.applesEaten++;
            newApplePosition();
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
        if (positionXPartsBody[0] > board.getScreenWidthSize()) {
            running = false;
        }

        //check if head touches top border
        if (positionYPartsBody[0] < 0) {
            running = false;
        }

        //check if head touches bottom border
        if (positionYPartsBody[0] > board.getScreenHeightSize()) {
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
                (board.getScreenWidthSize() - metricsOfGameOver.stringWidth("Game Over")) / 2,
                board.getScreenHeightSize() / 2);

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
                    if (snake.getDirection() != 'R') {
                        snake.setDirection('L');
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != 'L') {
                        snake.setDirection('R');
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != 'D') {
                        snake.setDirection('U');
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != 'U') {
                        snake.setDirection('D');
                    }
                    break;
            }
        }
    }


    final private int[] positionXPartsBody = new int[board.getGameUnits()];
    final private int[] positionYPartsBody = new int[board.getGameUnits()];

    public void move() {
        for (int i = snake.getBodyParts(); i > 0; i--) {
            positionXPartsBody[i] = positionXPartsBody[i - 1];
            positionYPartsBody[i] = positionYPartsBody[i - 1];
        }

        switch (snake.getDirection()) {
            case 'U':
                positionYPartsBody[0] = positionYPartsBody[0] - board.getUnitSize();
                break;
            case 'D':
                positionYPartsBody[0] = positionYPartsBody[0] + board.getUnitSize();
                break;
            case 'L':
                positionXPartsBody[0] = positionXPartsBody[0] - board.getUnitSize();
                break;
            case 'R':
                positionXPartsBody[0] = positionXPartsBody[0] + board.getUnitSize();
                break;
        }
    }
}
