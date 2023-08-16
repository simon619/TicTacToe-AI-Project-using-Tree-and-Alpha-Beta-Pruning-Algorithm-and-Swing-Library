package com.project.tictactoe;

import java.util.ArrayList;
import java.util.Scanner;


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


public class TicTacToeAI {

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
        return String.format(" %s | %s | %s            0 | 1 | 2 %n-----------          -----------%n %s | %s | %s            3 | 4 | 5 %n-----------          -----------%n %s | %s | %s            6 | 7 | 8 %n",
         str.charAt(0), str.charAt(1), str.charAt(2), str.charAt(3), str.charAt(4),
         str.charAt(5), str.charAt(6), str.charAt(7), str.charAt(8));
    }

    public int generateRandomNumber() {
        return 0 + (int)(Math.random() * 9);
    }

    public boolean status(String curString, char currentChar, boolean ai) {

        if (this.check(curString, currentChar)) {
            if (ai) {
                System.out.println("Ha Ha! I Won and You Lose!");
                System.out.println("One Day AI Will Rule The World");
            }
            else {
                System.out.println("Human! You Wins");
            }
            return true;
        }

        int count_ = this.countUnderScore(curString);
    
        if (count_ == 0) {
            System.out.println("Damn! Draw!");
            System.out.println();
            String res = this.gridToString(curString);
            System.out.println(res);
            return true;
        }

        else {
            return false;
        }
    }

    public void createSpace() {
        System.out.println();
        System.out.println();
    }

    public boolean validity(String str, int pointer) {
        return (str.charAt(pointer) == 'X' || str.charAt(pointer) == 'O') ? true : false;     
    }

    public static void main(String[] args) {
        
        try (Scanner sc = new Scanner(System.in)) {
            TicTacToeAI obj = new TicTacToeAI();
            System.out.println("Do You Want to Start The Game? Press One Number \n 0: Yes \n 1: No \n 3: AI vs AI");
            int start = sc.nextInt();
            System.out.println(" 0 | 1 | 2 \n-----------\n 3 | 4 | 5 \n-----------\n 6 | 7 | 8 ");
            System.out.println();
            String curString = "_________";

            if (start == 0) {
                char human = 'X', ai = 'O'; 
                System.out.println("Player 1: " + human + " and Player 2: " + ai);
                System.out.println("Human: X and AI: O.\nEnter Your Position by Pressing Number: ");
                int pos = sc.nextInt();
                curString = curString.substring(0, pos) + human + curString.substring(pos + 1);   
                boolean run = true, over = false;
                while (run) {
                    System.out.println("My Turn xD");
                    System.out.println("-------------------------------------------");
                    State state = new State(curString);
                    state = obj.buildTree(state, human, ai);
                    PruningNode res = obj.alphabetaPruning(state, -999999, 999999, true);
                    curString = res.pruningString;
                    System.out.println(obj.gridToString(curString)); 
                    over = obj.status(curString, ai, true);
                    if (over) {
                        run = false;
                        break;
                    }
                    obj.createSpace();
                    System.out.println("Your Turn");
                    System.out.println("Human: X and AI: O.\nEnter Your Position by Pressing Number: ");
                    System.out.println("-------------------------------------------");
                    pos = sc.nextInt();
                    if (obj.validity(curString, pos)) {
                        continue;
                    }
                    curString = curString.substring(0, pos) + human + curString.substring(pos + 1);
                    over = obj.status(curString, human, false);
                    if (over) {
                        run = false;
                        break;
                    }
                    obj.createSpace();   
                }
            }

            else if (start == 1) {
                char human = 'O', ai = 'X'; 
                System.out.println("My Turn xD");
                System.out.println("-------------------------------------------");
                State state;
                PruningNode res;
                int randomNumber = obj.generateRandomNumber();
                curString = curString.substring(0, randomNumber) + ai + curString.substring(randomNumber + 1); 
                boolean run = true, over = false;
                while (run) {
                    System.out.println(obj.gridToString(curString));
                    System.out.println("Your Turn");
                    System.out.println("Human: O and AI: X.\nEnter Your Position by Pressing Number: ");
                    System.out.println("-------------------------------------------");
                    int pos = sc.nextInt();
                    if (obj.validity(curString, pos)) {
                        continue;
                    }
                    curString = curString.substring(0, pos) + human + curString.substring(pos + 1);
                    over = obj.status(curString, ai, false);
                    if (over) {
                        run = false;
                        break;
                    }
                    obj.createSpace();
                    System.out.println("My Turn xD");
                    System.out.println("-------------------------------------------");
                    state = new State(curString);
                    state = obj.buildTree(state, human, ai);
                    res = obj.alphabetaPruning(state, -999999, 999999, true);
                    curString = res.pruningString;
                    over = obj.status(curString, ai, true);
                    if (over) {
                        run = false;
                         if (obj.check(curString, ai)) {
                            System.out.println(obj.gridToString(curString));
                        }
                        break;
                    } 
                }        
            }

            else if (start == 3) {
                char ai1 = 'X', ai2 = 'O';
                System.out.println("Player 1: " + ai1 + " and Player 2: " + ai2);
                System.out.println("AI 1: X -> My Turn xD");
                System.out.println("-------------------------------------------");
                State state;
                PruningNode res;
                int randomNumber = obj.generateRandomNumber();
                curString = curString.substring(0, randomNumber) + ai1 + curString.substring(randomNumber + 1);
                System.out.println(obj.gridToString(curString));
                System.out.println(); 
                boolean run = true, over = false;
                while (run) {
                    System.out.println("AI 2: O -> My Turn xD");
                    System.out.println("-------------------------------------------");
                    state = new State(curString);
                    state = obj.buildTree(state, ai1, ai2);
                    res = obj.alphabetaPruning(state, -999999, 999999, true);
                    curString = res.pruningString;
                    System.out.println(obj.gridToString(curString)); 
                    over = obj.status(curString, ai2, true);
                    if (over) {
                        run = false;
                        break;
                    }              
                    obj.createSpace();
                    System.out.println("AI 1: X -> My Turn xD");
                    System.out.println("-------------------------------------------");
                    state = new State(curString);
                    state = obj.buildTree(state, ai2, ai1);
                    res = obj.alphabetaPruning(state, -999999, 999999, true);
                    curString = res.pruningString;
                    System.out.println(obj.gridToString(curString)); 
                    over = obj.status(curString, ai2, true);
                    if (over) {
                        run = false;
                        break;
                    }
                    obj.createSpace();
                }               
            }

            else {
                System.out.println("Wrong Input");
            }
        }        
    }
}


