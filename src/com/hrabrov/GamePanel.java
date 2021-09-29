package com.hrabrov;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    Random random = new Random();
    Board board = new Board();
    Apple apple = new Apple();
    Snake snake = new Snake();
    boolean running = false;
    Timer timer;
    final int DELAY = 100;
    private final int maxAmountOfGameUnit = calculateAmountOfGameUnit(board.getWidthSize(),
            board.getHeightSize(), board.getUnitSize());
    private final int[] positionXOfPartsSnake = new int[maxAmountOfGameUnit];
    private final int[] positionYOfPartsSnake = new int[maxAmountOfGameUnit];

    GamePanel() {
        setPreferredSize(new Dimension(board.getWidthSize(), board.getHeightSize()));
        setBackground(Color.gray);
        setFocusable(true);
        startGame();
        this.addKeyListener(new MyKeyAdapter());
    }

    private int calculateAmountOfGameUnit(int widthSize, int heightSize, int unitSize) {
        return widthSize * heightSize / ((int) Math.pow(unitSize, 2));
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        g.setColor(Color.lightGray);
        for (int i = 0; i < board.getHeightSize() / board.getUnitSize(); i++) {
            g.drawLine(i * board.getUnitSize(), 0, i * board.getUnitSize(), board.getHeightSize());
            g.drawLine(0, i * board.getUnitSize(), board.getWidthSize(), i * board.getUnitSize());
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
                (board.getWidthSize() - metrics.stringWidth("Score: " + snake.applesEaten)) / 2,
                g.getFont().getSize());
    }

    public void startGame() {
        newApplePosition();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void newApplePosition() {
        apple.setApplePositionX(random.nextInt((int) (board.getWidthSize() /
                board.getUnitSize())) * board.getUnitSize());
        apple.setApplePositionY(random.nextInt((int) (board.getHeightSize() /
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
        for (int i = snake.getCurrentSizeOfSnake(); i > 0; i--) {
            if ((positionXOfPartsSnake[0] == positionXOfPartsSnake[i])
                    && (positionYOfPartsSnake[0] == positionYOfPartsSnake[i])) {
                running = false;
            }
        }

        if (positionXOfPartsSnake[0] < 0 || positionYOfPartsSnake[0] < 0 ||
                positionXOfPartsSnake[0] > board.getWidthSize() ||
                positionYOfPartsSnake[0] > board.getHeightSize()
        ) {
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
                (board.getWidthSize() - metricsOfGameOver.stringWidth("Game Over")) / 2,
                board.getHeightSize() / 2);

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
                    if (snake.getDirection() != Direction.RIGHT) {
                        snake.setDirection(Direction.LEFT);
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if (snake.getDirection() != Direction.LEFT) {
                        snake.setDirection(Direction.RIGHT);
                    }
                    break;
                case KeyEvent.VK_UP:
                    if (snake.getDirection() != Direction.DOWN) {
                        snake.setDirection(Direction.UP);
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if (snake.getDirection() != Direction.UP) {
                        snake.setDirection(Direction.DOWN);
                    }
                    break;
            }
        }
    }

    public void move() {
        for (int i = snake.getCurrentSizeOfSnake(); i > 0; i--) {
            positionXOfPartsSnake[i] = positionXOfPartsSnake[i - 1];
            positionYOfPartsSnake[i] = positionYOfPartsSnake[i - 1];
        }

        switch (snake.getDirection()) {
            case UP:
                positionYOfPartsSnake[0] = positionYOfPartsSnake[0] - board.getUnitSize();
                break;
            case DOWN:
                positionYOfPartsSnake[0] = positionYOfPartsSnake[0] + board.getUnitSize();
                break;
            case LEFT:
                positionXOfPartsSnake[0] = positionXOfPartsSnake[0] - board.getUnitSize();
                break;
            case RIGHT:
                positionXOfPartsSnake[0] = positionXOfPartsSnake[0] + board.getUnitSize();
                break;
        }
    }
}
