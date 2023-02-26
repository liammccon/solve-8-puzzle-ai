package puzzle;

/**
 * Represents a move of the blank tile in one direction, resulting in the new board state.
 * @param direction the direction in which to move the blank tile. Will be null for the initial move.
 * @param prev the previous move in the current sequence, going back to the start. Will be null for the initial move.
 * @param distFromStart represents g(n), the number of moves taken since the first move in this sequence. Starts as 0
 * @param heuristic represents h(n), the heuristic
 * @param newState the state of the board after this move
 */
public record Move (Direction direction, Move prev, int distFromStart, int heuristic, String newState) implements Comparable<Move> {
    /**
     * Returns the cost, f(n) = g(n) + h(n)
     * @return the cost, f(n) = g(n) + h(n)
     */
    public int cost() { return distFromStart + heuristic;}

    @Override public int compareTo(Move other) {
        return cost() - other.cost();
    }


}
