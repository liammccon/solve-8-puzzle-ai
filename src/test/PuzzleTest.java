package test;

import org.junit.jupiter.api.Test;
import puzzle.Puzzle;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleTest {

    @Test
    public void testBuildValidPuzzle(){
        Puzzle puzzle1 = new Puzzle("b12 345 678");
        assertEquals(puzzle1.toString(), "b12 345 678");
        int [][] board1 = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        assertTrue(Arrays.deepEquals(puzzle1.new TestHook().getBoard(), board1));

        Puzzle puzzle2 = new Puzzle("367,281,b54");
        assertEquals(puzzle2.toString(), "367 281 b54");
        int [][] board2 = {{3, 6, 7}, {2, 8, 1}, {0, 5, 4}};
        assertTrue(Arrays.deepEquals(puzzle2.new TestHook().getBoard(), board2));
    }


    @Test
    public void testBuildInvalidPuzzle() {
        //Duplicate number
        String puzzle1 = "b12 845 678";
        //Has digit 9
        String puzzle2 = "012345679";
        //Has more than 9 digits
        String puzzle3 = "01234567876";

        String[] invalidPuzzles = {puzzle1, puzzle2, puzzle3};
        for(String s : invalidPuzzles) {
            assertThrows(Exception.class, ()-> new Puzzle(s));
        }
    }
}