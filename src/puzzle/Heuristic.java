package puzzle;

import java.util.HashMap;
import java.util.Map;

public enum Heuristic {
    H1,
    H2;
    // Maps a tile number to its goal coordinates
    static final Map<Integer, Coordinate> goalCoordinate;
    // Instantiating the static goalCoordinate
    static {
        goalCoordinate = new HashMap<>();
        goalCoordinate.put(0, rowCol(0,0));
        goalCoordinate.put(1, rowCol(0,1));
        goalCoordinate.put(2, rowCol(0,2));
        goalCoordinate.put(3, rowCol(1,0));
        goalCoordinate.put(4, rowCol(1,1));
        goalCoordinate.put(5, rowCol(1,2));
        goalCoordinate.put(6, rowCol(2,0));
        goalCoordinate.put(7, rowCol(2,1));
        goalCoordinate.put(8, rowCol(2,2));
    }

    record Coordinate(int row, int col){}

    private static Coordinate rowCol(int r, int c) {return new Coordinate(r, c);}

    public static int calculateHeuristic(String state, Heuristic heuristic) {
        if(heuristic == H1) return calculateH1(state);
        if (heuristic == H2) return calculateH2(state);
        else throw new IllegalArgumentException("Uknown Heuristic given: " + heuristic);
    }

    private static int calculateH2(String state) {
        Puzzle puzzle = new Puzzle(state);
        int [][] board = puzzle.getBoard();
        int cost = 0; //heuristic
        for (int row = 0; row < 3; row ++){
            for (int col=0; col < 3; col++){
                cost += tileScoreH2(board, row, col);
            }
        }
        return cost;
    }

    /**
     * Gets the heuristic value for a single tile in the board
     */
    static int tileScoreH2(int[][] board, int row, int col) {
        int tile = board[row][col];
        //Do not count the blank tile
        if (tile == 0) return 0;
        int goalRow = goalCoordinate.get(tile).row();
        int goalCol = goalCoordinate.get(tile).col();
        int score = 0;
        score += Math.abs(goalRow - row);
        score += Math.abs(goalCol - col);
        return score;
    }

    private static int calculateH1(String state){
        int index = 0;
        int heuristic = 0;
        state = state.replace('b','0');
        for (char c: state.toCharArray()){
            if(c >= '0' && c <='8'){
                int digit = c - '0';
                //H1 does not count the blank tile's misplacement
                if(c != '0' && digit != index) heuristic ++;
                index ++;
            }
        }
        return heuristic;
    }

}
