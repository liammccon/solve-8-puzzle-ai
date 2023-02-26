package puzzle;

import java.util.*;

public class AStarSearch {

    private static final String SOLVED = "b12 345 678";


    public static Move solveAStar(Puzzle puzzle, Heuristic heuristic) {
        Move move = makeInitialMove(puzzle, heuristic);
        PriorityQueue<Move> frontier = new PriorityQueue<Move>();
        frontier.add(move);
        Hashtable<String, Integer> reachedCost = new Hashtable<>();
        reachedCost.put(move.newState(), move.cost());
        while (!frontier.isEmpty()){
            move = frontier.poll();
            if (move.newState().equals(SOLVED)) return move;
            for (Move next: expand(move, heuristic)){
                //if (next.state is not in reached OR next.cost is less than the cost for the equivalent state in the table
                if (!reachedCost.containsKey(next.newState()) || next.cost() < reachedCost.get(next.newState())){
                    frontier.add(next);
                    reachedCost.put(next.newState(), next.cost());
                }
            }
        }
        return null;
    }

    private static Move[] expand(Move move, Heuristic heuristic) {
        List<Direction> possibleDirections = Puzzle.getValidDirections(move.newState());
        List<Move> moves = new ArrayList<>();
        possibleDirections.forEach(direction -> {
            String nextState = Puzzle.move(move.newState(), direction);
            int newDistFromStart = move.distFromStart() + 1;
            Move nextMove = new Move (
                    direction,
                    move,
                    newDistFromStart,
                    calculateHeuristic(nextState, heuristic),
                    nextState
            );
            moves.add(nextMove);
        });
        Move [] moveArray = new Move[moves.size()];
        moves.toArray(moveArray);
        return moveArray;
    }


    private static Move makeInitialMove(Puzzle puzzle, Heuristic heuristic) {
        String state = puzzle.toString();
        int calculatedHeuristic = calculateHeuristic(state, heuristic);
        Move move = new Move(null, null, 0, calculatedHeuristic, state);
        return move;
    }

    private static int calculateHeuristic(String state, Heuristic heuristic) {
        if(heuristic == Heuristic.H1) return calculateH1(state);
        if (heuristic == Heuristic.H2) return calculateH2(state);
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

    record Coordinate(int row, int col){}

    // Maps a tile number to its goal coordinates
    private static final Map<Integer, Coordinate> goalCoordinate;
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

    private static Coordinate rowCol(int r, int c) {return new Coordinate(r, c);}

    /**
     * Gets the heuristic value for a single tile in the board
     */
    private static int tileScoreH2(int[][] board, int row, int col) {
        int tile = board[row][col];
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

    public class TestHook {
        public static int calculateHeuristic(String state, Heuristic heuristic) { return AStarSearch.calculateHeuristic(state, heuristic);}
        public static int tileScoreH1(int[][] board, int row, int col) { return AStarSearch.tileScoreH2(board, row, col); }
    }
}
