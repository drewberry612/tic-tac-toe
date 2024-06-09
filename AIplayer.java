// RegNo. 2000878

import java.util.*;

class AIplayer { 
    List<Point> availablePoints;
    List<PointsAndScores> rootsChildrenScores;
    Board b = new Board();
    
    public AIplayer() {
    }
    
    public Point returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScores.size(); ++i) { 
            if (MAX < rootsChildrenScores.get(i).score) {
                MAX = rootsChildrenScores.get(i).score;
                best = i;
            }
        }
        return rootsChildrenScores.get(best).point;
    }

    public int returnMin(List<Integer> list) {
        int min = Integer.MAX_VALUE;
        int index = -1;
        
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) < min) {
                min = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }

    public int returnMax(List<Integer> list) {
        int max = Integer.MIN_VALUE;
        int index = -1;
        
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) > max) {
                max = list.get(i);
                index = i;
            }
        }
        return list.get(index);
    }
 
    public void callMinimax(int depth, int turn, Board b){
        rootsChildrenScores = new ArrayList<>();
        minimax(depth, turn, Integer.MIN_VALUE, Integer.MAX_VALUE, b);
    }
    
    public int minimax(int depth, int turn, int alpha, int beta, Board b) {
        if (b.hasXWon()) return 10000; // Max value of gamestate
        if (b.hasOWon()) return -10000; // Min value of gamestate
        List<Point> pointsAvailable = b.getAvailablePoints();
        if (pointsAvailable.isEmpty()) return 0; 

        List<Integer> scores = new ArrayList<>();

        int temp;
        if (turn ==1) temp = Integer.MIN_VALUE; //added for alpha-beta pruning
        else temp = Integer.MAX_VALUE; //added for alpha-beta pruning

        for (int i = 0; i < pointsAvailable.size(); ++i) {
            Point point = pointsAvailable.get(i);  

            if (depth == 6) { // When at the max depth run the heuristic function
                b.placeAMove(point, turn);
                int currentScore = heuristic(b);
                scores.add(currentScore);
                temp = Math.max(temp, currentScore); //added for alpha-beta pruning
                alpha = Math.max(alpha, temp); //added for alpha-beta pruning
            }
            else {
                if (turn == 1) {
                    b.placeAMove(point, 1);
                    int currentScore = minimax(depth + 1, 2, alpha, beta, b);
                    scores.add(currentScore);
                    temp = Math.max(temp, currentScore); //added for alpha-beta pruning
                    alpha = Math.max(alpha, temp); //added for alpha-beta pruning
                    if (depth == 0)
                        rootsChildrenScores.add(new PointsAndScores(currentScore, point));

                } else if (turn == 2) {
                    b.placeAMove(point, 2);
                    int currentScore = minimax(depth + 1, 1, alpha, beta, b);
                    scores.add(currentScore);
                    temp = Math.min(temp, currentScore); //added for alpha-beta pruning
                    beta = Math.min(beta, temp); //added for alpha-beta pruning
                }
            }
            b.placeAMove(point, 0);
            if (alpha >= beta) { //Check whether alpha is equal to or greater than beta. If so, there is no need to valuate the remaining game positions at this depth.
                temp = pointsAvailable.size()-i-1;
                break;
            }
        }
        return turn == 1 ? returnMax(scores) : returnMin(scores);
    }

    // The heuristic function here checks all lines in the board and gives them a value
    // If a line has both X's and O's in it, the value that line has is 0
    // If a line has just X's in it, the value that line has is 10^n, where n is number of X's
    // If a line has just O's in it, the value that line has is negative 10^n, where n is number of O's
    // Then the function returns the sum of all these values, which is the value given to the gamestate
    public int heuristic(Board b) {
        int currentScore = 0;

        for (int i=0; i<5; i++) { // Checks all columns
            // Variables for holding number of X's and O's
            int XNo = 0;
            int ONo = 0;

            for (int j=0; j<5; j++) {
                int state = b.board[i][j];
                if (state == 1) {
                    XNo++;
                }
                else if (state == 2) {
                    ONo++;
                }
            }
            // Score calculations
            if (XNo == 0 && ONo > 0) {
                currentScore -= Math.pow(10, ONo);
            }
            else if (ONo == 0 && XNo > 0) {
                currentScore += Math.pow(10, XNo);
            }
        }

        for (int i=0; i<5; i++) { // Checks all rows
            // Variables for holding number of X's and O's
            int XNo = 0;
            int ONo = 0;

            for (int j=0; j<5; j++) {
                int state = b.board[j][i];
                if (state == 1) {
                    XNo++;
                }
                else if (state == 2) {
                    ONo++;
                }
            }
            // Score calculations
            if (XNo == 0 && ONo > 0) {
                currentScore -= Math.pow(10, ONo);
            }
            else if (ONo == 0 && XNo > 0) {
                currentScore += Math.pow(10, XNo);
            }
        }

        // Checks the top left to bottom right diagonal
        int XNo = 0;
        int ONo = 0;
        for (int i=0; i<5; i++) {
            int state = b.board[i][i];
            if (state == 1) {
                XNo++;
            }
            else if (state == 2) {
                ONo++;
            }
        }
        // Score calculations
        if (XNo == 0 && ONo > 0) {
            currentScore -= Math.pow(10, ONo);
        }
        else if (ONo == 0 && XNo > 0) {
            currentScore += Math.pow(10, XNo);
        }

        // Checks the top right to bottom left diagonal
        XNo = 0;
        ONo = 0;
        for (int i=0; i<5; i++) {
            int state = b.board[4-i][i];
            if (state == 1) {
                XNo++;
            }
            else if (state == 2) {
                ONo++;
            }
        }
        // Score calculations
        if (XNo == 0 && ONo > 0) {
            currentScore -= Math.pow(10, ONo);
        }
        else if (ONo == 0 && XNo > 0) {
            currentScore += Math.pow(10, XNo);
        }

        return currentScore;
    }
}
