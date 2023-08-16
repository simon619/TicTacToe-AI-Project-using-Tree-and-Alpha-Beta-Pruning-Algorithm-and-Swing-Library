package com.project.tictactoeaiwithjswing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;


public class TextField extends JPanel {

    private JTextArea jTextArea;

    public TextField() {
        jTextArea = new JTextArea();
        setLayout(new BorderLayout());
        add(new JScrollPane(jTextArea), BorderLayout.CENTER);
        Color color=new Color(0,0,0);
        jTextArea.setBackground(color);
        Font font = new Font("Consolas", Font.BOLD, 12);
        jTextArea.setFont(font);
        jTextArea.setForeground(Color.GREEN);
    }

    public void appendString(String st) {
        jTextArea.append(st);
    }
    
    public void deleteText() {
        jTextArea.setText(null);
    }
}