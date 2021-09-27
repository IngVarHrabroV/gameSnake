package com.hrabrov;

import javax.swing.*;

public class GameFrame extends JFrame {
    GameFrame () {
        setTitle("Snake Game");
        add(new GamePanel());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        pack();
        setVisible(true);
        setLocationRelativeTo(null);
    }
}
