package com.project.tictactoeaiwithjswing;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;


public class FormPanel extends JPanel implements ActionListener {

    private JButton b0, b1, b2, b3, b4, b5, b6, b7, b8, aiVSai, aiStarts, startButton, stopButton;
    private StringListener stringListener;
    
    public FormPanel() {
        
        Dimension dim = getPreferredSize();
        dim.width = 200;
        setPreferredSize(dim);
        Border innerBorder = BorderFactory.createTitledBorder("th351M0N's TicTacToe AI");
        Border outerBorder = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        setBorder(BorderFactory.createCompoundBorder(innerBorder, outerBorder));
        b0 = new JButton("0");
        b1 = new JButton("1");
        b2 = new JButton("2");
        b3 = new JButton("3");
        b4 = new JButton("4");
        b5 = new JButton("5");
        b6 = new JButton("6");
        b7 = new JButton("7");
        b8 = new JButton("8");
        aiVSai = new JButton("AI versus AI");
        aiStarts = new JButton(" AI Starts First ");
        startButton = new JButton("Start");
        stopButton = new JButton("Clear");
        
        JButton [] lists = {b0, b1, b2, b3, b4, b5, b6, b7, b8, startButton, stopButton, aiStarts, aiVSai};

        for (int i = 0; i < lists.length; i++) {
            lists[i].addActionListener(this);
        }

        setLayout(new GridLayout(3,3));
        add(b0);add(b1);add(b2);add(b3);add(b4);
        add(b5);add(b6);add(b7);add(b8);
        setLayout(new FlowLayout(FlowLayout.CENTER));
        add(aiVSai);add(aiStarts);add(startButton);add(stopButton);
    }

    public void setStringLister(StringListener stringListener) {
        this.stringListener = stringListener;
    }
 
    @Override
    public void actionPerformed(ActionEvent e) {
        
        JButton clicked = (JButton) e.getSource();

        if (clicked == startButton) {
            if (stringListener != null) {
                stringListener.textEmitter("start");
            }
        }

        if (clicked == aiStarts) {
            if (stringListener != null) {
                stringListener.textEmitter("aiStarts");
            }
        }

        if (clicked == aiVSai) {
            if (stringListener != null) {
                stringListener.textEmitter("ais");
            }
        }

        if (clicked == stopButton) {
            if (stringListener != null) {
                stringListener.textEmitter("stop");
            }
        }

        if (clicked == b0) {
            if (stringListener != null) {
                stringListener.textEmitter("0");
            }
        }

        if (clicked == b1) {
            if (stringListener != null) {
                stringListener.textEmitter("1");
            }
        }

        if (clicked == b2) {
            if (stringListener != null) {
                stringListener.textEmitter("2");
            }
        }

        if (clicked == b3) {
            if (stringListener != null) {
                stringListener.textEmitter("3");
            }
        }

        if (clicked == b4) {
            if (stringListener != null) {
                stringListener.textEmitter("4");
            }
        }

        if (clicked == b5) {
            if (stringListener != null) {
                stringListener.textEmitter("5");
            }
        }

        if (clicked == b6) {
            if (stringListener != null) {
                stringListener.textEmitter("6");
            }
        }

        if (clicked == b7) {
            if (stringListener != null) {
                stringListener.textEmitter("7");
            }
        }

        if (clicked == b8) {
            if (stringListener != null) {
                stringListener.textEmitter("8");
            }
        }
    }      
}
