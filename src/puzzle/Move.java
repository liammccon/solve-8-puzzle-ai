package puzzle;

import java.util.Stack;

/**
 * Represents a move of the blank tile in one direction, resulting in the new board state.
 * @param direction the direction in which to move the blank tile. Will be null for the initial move.
 * @param prev the previous move in the current sequence, going back to the start. Will be null for the initial move.
 * @param distFromStart represents g(n), the number of moves taken since the first move in this sequence. Starts as 0
 * @param heuristic represents h(n), the heuristic
 * @param state the resulting state of the board after this move is completed
 */
public record Move (Direction direction, Move prev, int distFromStart, int heuristic, String state) implements Comparable<Move> {

    /**
     * Returns the cost, f(n) = g(n) + h(n)
     * @return the cost, f(n) = g(n) + h(n)
     */
    public int cost() { return distFromStart + heuristic;}

    @Override public int compareTo(Move other) {
        return cost() - other.cost();
    }

    /** Runs the solution path on the given puzzle and prints the steps taken
     */
    public static void runAndPrintSolution(Puzzle puzzle, Move move, long generatedNodes, Heuristic heuristic, String searchAlgorithm){
        Stack<Move> stack = new Stack<>();
        while(move.prev() != null){
            stack.add(move);
            move = move.prev();
        }
        int moveIndex = 0;
        System.out.println("Starting state: " + puzzle);
        while(!stack.isEmpty()){
            move = stack.pop();
            puzzle.move(move.direction);
            moveIndex++;
            System.out.println("Move " + moveIndex + ": " + move.direction() + ". State: " + puzzle);
        }
        System.out.println("Solved using " + searchAlgorithm + " with heuristic '"+heuristic+"' with " + generatedNodes + " nodes generated!");
    }

}
