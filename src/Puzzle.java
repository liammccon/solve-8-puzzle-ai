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
 * - - - - - - - <br>
 * | b | 1 | 2 | <br>
 * | 3 | 4 | 5 | <br>
 * | 6 | 7 | 8 | <br>
 * - - - - - - - <br>
 * </code>
 */
public class Puzzle {

    /**
     * The 3 x 3 puzzle board. A '0' represents the blank tile.
     */
    private int [][] board;

    /**
     * Constructor for the Puzzle. Sets the initial state of the board.
     * @param initialState - The starting state for the puzzle.
     */
    public Puzzle(String initialState) {
        this.board = buildBoard(initialState);
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

}
