package com.hrabrov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    Random random = new Random();
    Board board = new Board();
    Apple apple = new Apple();
    boolean running = false;
    Timer timer;
    final int DELAY = 100;

    private final int gameUnits = (board.getScreenWidthSize() * board.getScreenHeightSize()) /
            ((int) Math.pow(board.getUnitSize(), 2));

    Snake snake = new Snake(gameUnits);

    GamePanel() {
        setPreferredSize(new Dimension(board.getScreenWidthSize(), board.getScreenHeightSize()));
        setBackground(Color.gray);
        setFocusable(true);
        startGame();
        this.addKeyListener(new MyKeyAdapter());
    }

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

            for (int i = 0; i < snake.getCurrentSizeOfSnake(); i++) {
                if (i == 0) {
                    g.setColor(Color.green);
                } else {
                    g.setColor(new Color(54, 182, 5));
                }
                g.fillRect(positionXOfPartsSnake[i], positionYOfPartsSnake[i], board.getUnitSize(), board.getUnitSize());
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
        if ((positionXOfPartsSnake[0] == apple.getApplePositionX())
                && positionYOfPartsSnake[0] == apple.getApplePositionY()) {
            snake.setCurrentSizeOfSnake(snake.getCurrentSizeOfSnake() + 1);
            snake.applesEaten++;
            newApplePosition();
        }
    }

    public void checkCollisions() {
        //check if head collides with body
        for (int i = snake.getCurrentSizeOfSnake(); i > 0; i--) {
            if ((positionXOfPartsSnake[0] == positionXOfPartsSnake[i])
                    && (positionYOfPartsSnake[0] == positionYOfPartsSnake[i])) {
                running = false;
            }
        }

        //check if head touches left border
        if (positionXOfPartsSnake[0] < 0) {
            running = false;
        }

        //check if head touches right border
        if (positionXOfPartsSnake[0] > board.getScreenWidthSize()) {
            running = false;
        }

        //check if head touches top border
        if (positionYOfPartsSnake[0] < 0) {
            running = false;
        }

        //check if head touches bottom border
        if (positionYOfPartsSnake[0] > board.getScreenHeightSize()) {
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

    class MyKeyAdapter extends KeyAdapter {
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



    private final int[] positionXOfPartsSnake = new int[gameUnits];
    private final int[] positionYOfPartsSnake = new int[gameUnits];

    public void move() {
        for (int i = snake.getCurrentSizeOfSnake(); i > 0; i--) {
            positionXOfPartsSnake[i] = positionXOfPartsSnake[i - 1];
            positionYOfPartsSnake[i] = positionYOfPartsSnake[i - 1];
        }

        switch (snake.getDirection()) {
            case 'U':
                positionYOfPartsSnake[0] = positionYOfPartsSnake[0] - board.getUnitSize();
                break;
            case 'D':
                positionYOfPartsSnake[0] = positionYOfPartsSnake[0] + board.getUnitSize();
                break;
            case 'L':
                positionXOfPartsSnake[0] = positionXOfPartsSnake[0] - board.getUnitSize();
                break;
            case 'R':
                positionXOfPartsSnake[0] = positionXOfPartsSnake[0] + board.getUnitSize();
                break;
        }
    }
}
