package com.project.tictactoeaiwithjswing;

import java.util.ArrayList;
import java.awt.BorderLayout;

import javax.swing.JFrame;


class PruningNode {

    String pruningString;
    Integer pruningInt;

    public PruningNode(String pruningString, Integer pruningInt) {
        this.pruningString = pruningString;
        this.pruningInt = pruningInt;
    }
}


class State {

    String currentState;
    Integer factor = null;
    ArrayList<State> childStates = new ArrayList<State>(0);
    
    public State(String currentState) {
        this.currentState = currentState;
    }
}


class StatusReport {

    String status;
    boolean done;

    public StatusReport(String status, boolean done) {
        this.status = status;
        this.done = done;
    }
}


class TicTacToeAI {

    boolean isEqual(int pointer, char currentChar, String st) {
        return (st.charAt(pointer) == currentChar);
    }

    public boolean check(String st, char currentChar) {
        
        if (this.isEqual(0, currentChar, st) && 
        this.isEqual(1, currentChar, st) && 
        this.isEqual(2, currentChar, st)) {
            return true;
        }

        else if (this.isEqual(0, currentChar, st) &&
         this.isEqual(3, currentChar, st) && 
         this.isEqual(6, currentChar, st)) {
            return true;
        }

        else if (this.isEqual(2, currentChar, st) &&
         this.isEqual(5, currentChar, st) && 
         this.isEqual(8, currentChar, st)) {
            return true;
        }
        
        else if (this.isEqual(6, currentChar, st) && 
        this.isEqual(7, currentChar, st) && 
        this.isEqual(8, currentChar, st)) {
            return true;
        }

        else if (this.isEqual(3, currentChar, st) && 
        this.isEqual(4, currentChar, st) && 
        this.isEqual(5, currentChar, st)) {
            return true;
        }

        else if (this.isEqual(1, currentChar, st) && 
        this.isEqual(4, currentChar, st) && 
        this.isEqual(7, currentChar, st)) {
            return true;
        }

        else if (this.isEqual(2, currentChar, st) && 
        this.isEqual(4, currentChar, st) && 
        this.isEqual(6, currentChar, st)) {
            return true;
        }

        else if (this.isEqual(0, currentChar, st) && 
        this.isEqual(4, currentChar, st) && 
        this.isEqual(8, currentChar, st)) {
            return true;
        }

        else {
            return false;
        }
    }

    public int countUnderScore(String str) {
        
        int count_ = 0;
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '_') {
                count_++;
            }
        }
        return count_;
    }

    public State buildTree(State currentNode, char currentChar, char maximizingValue) {
        
        String curString = currentNode.currentState;
        int count_ = this.countUnderScore(curString);
        
        if (this.check(curString, currentChar)) {
            currentNode.factor = (currentChar == maximizingValue) ? 1 * (count_ + 1) : -1 * (count_ + 1); 
            return currentNode;
        }

        else if (count_ == 0) {
            currentNode.factor = 0;
            return currentNode;
        }

        else {
            for (int i = 0; i < curString.length(); i++) {    
                char nextChar = (currentChar == 'X') ? 'O' : 'X';
                if (curString.charAt(i) == '_') {
                    String nextString = curString.substring(0, i) + nextChar + curString.substring(i + 1);
                    State nexState = new State(nextString);
                    currentNode.childStates.add(this.buildTree(nexState, nextChar, maximizingValue));
                }
            }
            return currentNode;
        }
    }

    public PruningNode alphabetaPruning(State pruningState, int alpha, int beta, boolean maximizing) {
        
        if (pruningState.childStates.size() == 0) {
            return new PruningNode(pruningState.currentState, pruningState.factor);
        }

        if (maximizing) {
            PruningNode maxNode = new PruningNode("", -999999);
            for (int i = 0; i < pruningState.childStates.size(); i++) {
                PruningNode currentPuringNode = this.alphabetaPruning(pruningState.childStates.get(i), alpha, beta, false);
                if (currentPuringNode.pruningInt > maxNode.pruningInt) {
                    maxNode.pruningInt = currentPuringNode.pruningInt;
                    maxNode.pruningString = pruningState.childStates.get(i).currentState;
                }
                alpha = Math.max(alpha, maxNode.pruningInt);
                if (beta <= alpha) {
                    break;
                }
            }
            return maxNode;
         }

         else {
            PruningNode minNode = new PruningNode("", 999999);
            for (int i = 0; i < pruningState.childStates.size(); i++) {
                PruningNode currentPuringNode = this.alphabetaPruning(pruningState.childStates.get(i), alpha, beta, true);
                if (currentPuringNode.pruningInt < minNode.pruningInt) {
                    minNode.pruningInt = currentPuringNode.pruningInt;
                    minNode.pruningString = pruningState.childStates.get(i).currentState;
                }
                beta = Math.min(beta, minNode.pruningInt);
                if (beta <= alpha) {
                    break;
                }
            }
            return minNode;
         }
    }

    public String gridToString(String str) {
       
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == 'X' || str.charAt(i) == 'O') {
                continue;
            }
            if (str.charAt(i) == '_') {
                str = str.substring(0, i) + " " + str.substring(i + 1);
            }
        }
        return String.format(" %s | %s | %s %n-----------%n %s | %s | %s %n-----------%n %s | %s | %s %n",
         str.charAt(0), str.charAt(1), str.charAt(2), str.charAt(3), str.charAt(4),
         str.charAt(5), str.charAt(6), str.charAt(7), str.charAt(8));
    }

    public int generateRandomNumber() {
        return 0 + (int)(Math.random() * 9);
    }

    public StatusReport status(String curString, char currentChar, boolean ai) {

        StatusReport sr;

        if (this.check(curString, currentChar)) {
            if (ai) {
                sr = new StatusReport("Ha Ha! I Won and You Lose! \n One Day AI Will Rule The World\n \n", true);
            }
            else {
                sr = new StatusReport("Human! You Wins\n \n", true);
            }
            return sr;
        }

        int count_ = this.countUnderScore(curString);
        
        if (count_ == 0) {
            return new StatusReport("Damn! Draw!\n \n", true);
        }

        else {
            return new StatusReport("", false);
        }
    }


    public boolean validity(String str, int pointer) {
        return (str.charAt(pointer) == 'X' || str.charAt(pointer) == 'O') ? true : false;     
    }
}


public class MainFrame {

    private JFrame jFrame;
    private TextField jTextField;
    private FormPanel jFormPanel;
    private TicTacToeAI obj;
    String whoStarted;
    StatusReport over;
    String curString = "_________";
    char human, ai, ai1, ai2;

    public MainFrame() {
        setUp();
    }

    public static void freeze(int speed) {
        try {
            Thread.sleep(speed);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumeric(String strNum) {
        
        if (strNum == null) {
            return false;
        }

        try {
            double num = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public void setUp() {
        
        jFrame = new JFrame("TicTacToeAI");
        jFrame.setSize(500, 300);
        jFrame.setVisible(true);

        jTextField = new TextField();
        jFormPanel = new FormPanel();
        obj = new TicTacToeAI();
        over = new StatusReport("", false);

        jFormPanel.setStringLister(new StringListener() {

            @Override
            public void textEmitter(String str) {
                
               if (str == "stop") {
                jTextField.deleteText();
                curString = "_________";
               }
               
                if (str == "start") {
                    whoStarted = "human";
                    human = (whoStarted == "human") ? 'X' : 'O';
                    ai = (whoStarted == "human") ? 'O' : 'X';
                    jTextField.appendString(" 0 | 1 | 2 \n-----------\n 3 | 4 | 5 \n-----------\n 6 | 7 | 8 \n \nPlayer 1: " + human + " and Player 2: " + ai + "\nEnter Your Position by Pressing Number: \n");
                    jTextField.appendString("----------------------------------------------------\n");
                }

                if (str == "aiStarts") {
                    whoStarted = "ai";
                    ai = (whoStarted == "ai") ? 'X' : 'O';
                    human = (whoStarted == "ai") ? 'O' : 'X';
                    jTextField.appendString(" 0 | 1 | 2 \n-----------\n 3 | 4 | 5 \n-----------\n 6 | 7 | 8 \n \nPlayer 1: " + ai + " and Player 2: " + human + "\n \nMy Turn: \n");
                    jTextField.appendString("----------------------------------------------------\n");
                    int randomNumber = obj.generateRandomNumber();
                    curString = curString.substring(0, randomNumber) + ai + curString.substring(randomNumber + 1);
                    jTextField.appendString(obj.gridToString(curString));
                    jTextField.appendString("                                  \n");
                    jTextField.appendString("                                  \n");
                    jTextField.appendString("Enter Your Position by Pressing Number: \n");
                    jTextField.appendString("----------------------------------------------------\n");
                }

                if (str == "ais") {
                    whoStarted = "ais";
                    ai1 = 'X'; ai2 = 'O';
                    jTextField.appendString(" 0 | 1 | 2 \n-----------\n 3 | 4 | 5 \n-----------\n 6 | 7 | 8 \n \nPlayer 1: " + ai1 + " and Player 2: " + ai2 + "\n \nAI 1: X -> My Turn xD: \n");
                    jTextField.appendString("----------------------------------------------------\n");
                    int randomNumber = obj.generateRandomNumber();
                    curString = curString.substring(0, randomNumber) + ai1 + curString.substring(randomNumber + 1);
                    jTextField.appendString(obj.gridToString(curString));
                    jTextField.appendString("                                  \n");
                    jTextField.appendString("                                  \n");
                }

                
                if (whoStarted == "human") {
                    if (isNumeric(str)) {
                        
                        Integer pos = Integer.parseInt(str);             
                        
                        if (!obj.validity(curString, pos)) {
                            curString = curString.substring(0, pos) + human + curString.substring(pos + 1); 
                            jTextField.appendString(obj.gridToString(curString));
                            jTextField.appendString("                                  \n");
                            jTextField.appendString("                                  \n");
                            over = obj.status(curString, human, false);
                            
                            if (over.done) {
                                jTextField.appendString(over.status);
                                curString = "_________";
                                freeze(500);
                                textEmitter("start");
                            }
                            
                            else {
                                freeze(500);
                                jTextField.appendString("My Turn\n");
                                jTextField.appendString("----------------------------------------------------\n");
                                State state = new State(curString);
                                state = obj.buildTree(state, human, ai);
                                PruningNode res = obj.alphabetaPruning(state, -999999, 999999, true);
                                curString = res.pruningString;
                                jTextField.appendString(obj.gridToString(curString));
                                jTextField.appendString("                                  \n");
                                jTextField.appendString("                                  \n"); 
                                over = obj.status(curString, ai, true);
                                
                                if (over.done) {
                                    jTextField.appendString(over.status);
                                    curString = "_________";
                                    freeze(500);
                                    textEmitter("start");
                                }
                                
                                else {
                                    jTextField.appendString("Enter Your Position by Pressing Number: \n");
                                    jTextField.appendString("----------------------------------------------------\n");
                                }
                            }
                        }                    
                    }
                }
                
                if (whoStarted == "ai") {

                    if (isNumeric(str)) {

                        Integer pos = Integer.parseInt(str);

                        if (!obj.validity(curString, pos)) {
                            curString = curString.substring(0, pos) + human + curString.substring(pos + 1); 
                            jTextField.appendString(obj.gridToString(curString));
                            jTextField.appendString("                                  \n");
                            jTextField.appendString("                                  \n");
                            over = obj.status(curString, human, false);
                            
                            if (over.done) {
                                jTextField.appendString(over.status);
                                curString = "_________";
                                freeze(500);
                                textEmitter("aiStarts");
                            }

                            else {
                                freeze(500);
                                jTextField.appendString("My Turn\n");
                                jTextField.appendString("----------------------------------------------------\n");
                                State state = new State(curString);
                                state = obj.buildTree(state, human, ai);
                                PruningNode res = obj.alphabetaPruning(state, -999999, 999999, true);
                                curString = res.pruningString;
                                jTextField.appendString(obj.gridToString(curString));
                                jTextField.appendString("                                  \n");
                                jTextField.appendString("                                  \n");
                                over = obj.status(curString, ai, true);
                                if (over.done) {
                                    jTextField.appendString(over.status);
                                    curString = "_________";
                                    freeze(500);
                                    textEmitter("aiStarts");
                                }
                                else {
                                    jTextField.appendString("Enter Your Position by Pressing Number: \n");
                                    jTextField.appendString("----------------------------------------------------\n");
                                }
                            }
                        }
                    }
                }

                if (whoStarted == "ais") {

                    boolean going = true;

                    while (going) {
                        jTextField.appendString("AI 2: O -> My Turn xD: \n");
                        jTextField.appendString("----------------------------------------------------\n");
                        State state = new State(curString);
                        state = obj.buildTree(state, ai1, ai2);
                        PruningNode res = obj.alphabetaPruning(state, -999999, 999999, true);
                        curString = res.pruningString;
                        freeze(400);
                        jTextField.appendString(obj.gridToString(curString));
                        jTextField.appendString("                                  \n");
                        jTextField.appendString("                                  \n");
                        over = obj.status(curString, ai2, true);
                        
                        if (over.done) {
                            jTextField.appendString(over.status);
                            curString = "_________";
                            going = false;
                            break;          
                        }

                        else {
                            jTextField.appendString("AI 1: X -> My Turn xD: \n");
                            jTextField.appendString("----------------------------------------------------\n");
                            state = new State(curString);
                            state = obj.buildTree(state, ai2, ai1);
                            res = obj.alphabetaPruning(state, -999999, 999999, true);
                            curString = res.pruningString;
                            freeze(400);
                            jTextField.appendString(obj.gridToString(curString));
                            jTextField.appendString("                                  \n");
                            jTextField.appendString("                                  \n");
                            over = obj.status(curString, ai1, true);
                            
                            if (over.done) {
                                jTextField.appendString(over.status);
                                curString = "_________";
                                going = false;
                                break;            
                            }
                        }
                    }
                }
            }
        });

        jFrame.setLayout(new BorderLayout());      
        jFrame.add(jFormPanel, BorderLayout.WEST);
        jFrame.add(jTextField, BorderLayout.CENTER);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    }   
}

