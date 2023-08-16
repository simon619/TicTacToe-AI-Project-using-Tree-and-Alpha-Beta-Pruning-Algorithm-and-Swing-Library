package com.project.tictactoeaiwithjswing;

import javax.swing.SwingUtilities;


public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                MainFrame main = new MainFrame();
            }

        });
    }
}
