package puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an 8-Puzzle and the actions it can perform
 * <p>An 8 puzzle is a 3 x 3 board with 8 number tiles and one blank space. You solve the puzzle
 * by moving the tiles (or moving the blank "tile") until the numbers are in increasing order and the blank
 * tile is in the upper left hand corner.</p>
 * <p>The solved board looks like this:</p>
 * <code>
 * ------------- <br>
 * | b | 1 | 2 | <br>
 * | 3 | 4 | 5 | <br>
 * | 6 | 7 | 8 | <br>
 * ------------- <br>
 * </code>
 */
public class Puzzle {

    /**
     * The 3 x 3 puzzle board. A '0' represents the blank tile.
     */
    private final int [][] board;
    private long maxNodes;
    private final long DEFAULT_MAX_NODES = 500;

    /**
     * Constructor for the Puzzle. Sets the initial state of the board.
     * @param initialState - The starting state for the puzzle.
     */
    public Puzzle(String initialState) {
        this.board = buildBoard(initialState);
        this.maxNodes = DEFAULT_MAX_NODES;
    }

    /**
     * Default constructor for Puzzle. Sets the initial position to the solved state, 'b12 345 678'
     */
    public Puzzle() {
        this.board = buildBoard("b12 345 678");
        this.maxNodes = DEFAULT_MAX_NODES;
    }

    /**
     * Creates a board from the given state. The state should contain the symbols "b12 345 678" in the desired order.
     * It should contain each number 1 - 8 and the blank space 'b' exactly once.
     * @param state - The state to verify and build a board from
     * @throws IllegalArgumentException if the state is not valid.
     * @return The valid board
     */
    private int [][] buildBoard(String state) {
        Objects.requireNonNull(state);
        List<Integer> stateAsInts = new ArrayList<Integer>();
        char[] stateCharArray = state.toCharArray();
        for (char c : stateCharArray){
            //Convert the letter 'b' to '0'
            if (c == 'b')
                c = '0';
            if (Character.isDigit(c)) {
                int i = Character.getNumericValue(c);
                if (stateAsInts.contains(i)){
                    throw new IllegalArgumentException("State [" + state + "] should not contain duplicate tiles");
                } else if (i >= 0 && i <= 8){
                    stateAsInts.add(i);
                } else throw new IllegalArgumentException("State [" + state + "] should only contain the digits 1-8");
            }
        }
        if (stateAsInts.size() != 9) throw new IllegalArgumentException("State [" + state + "] should contain 8 digits and one blank ('b') ");

        return intArrayToBoard(stateAsInts);
    }

    /**Create a 2D 3x3 int array from the list of Integers */
    private int[][] intArrayToBoard(List<Integer> stateAsInts) {
        assert(stateAsInts.size() == 9);
        int [][] board = new int[3][3];
        int count = 0;
        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 3; col++){
                board[row][col] = stateAsInts.get(count);
                count++;
            }
        }
        return board;
    }

    /**
     * Set the maximum number of nodes the search methods are allowed to generate in one search
     * @param maxNodes - the maximum number of nodes a search method can generate
     */
    public void setMaxNodes(long maxNodes){
        this.maxNodes = maxNodes;
    }

    /**
     * Creates a string representation of the current state in the format "b12 345 678"
     * @return The string representation of the current state
     */
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 3; col++){
                char c = (char) (board[row][col] + '0'); //Convert the digit to char
                if (c == '0')
                    c = 'b';
                sb.append(c);
            }
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * Move the blank tile in the given direction
     * @param direction the direction in which to move the blank tile
     */
    public void move(Direction direction) {

    }

    /**
     * Returns whether or the blank tile can be moved in the given direction
     * @param direction The direction to test for validity
     * @return True if the direction is a valid move or false if not
     */
    public boolean isValidMove(Direction direction){
        switch (direction) {
            case UP -> {return blankRowCol().row() != 0; }
            case DOWN -> {return blankRowCol().row() != 2;}
            case LEFT -> {return blankRowCol().col() != 0;}
            case RIGHT -> {return blankRowCol().col() != 2;}
            default -> {return false;} //Should not have to run
        }
    }

    /**
     * Gets a {@link RowCol} record containing the row and column of the blank tile
     * @return a {@link RowCol} record containing the row and column of the blank tile
     */
    private RowCol blankRowCol() {
        for (int row = 0; row < 3; row++){
            for (int col = 0; col < 3; col++){
                if (board[row][col] == 0) return new RowCol(row, col);
            }
        }
        throw new IllegalStateException("Board does not contain a blank tile or is not 3 x 3");
    }

    /**
     * A record containing the row and column indexes for a tile
     */
    protected static record RowCol(int row, int col){}

    public class TestHook {
        public int [][] getBoard(){
            return Puzzle.this.board;
        }
        public int getBlankRow() {return Puzzle.this.blankRowCol().row();}
        public int getBlankCol() {return Puzzle.this.blankRowCol().col();}

    }


}
