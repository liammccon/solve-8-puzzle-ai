package test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import puzzle.Puzzle;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static puzzle.Direction.*;

class PuzzleTest {
    private Puzzle puzzleSolved;
    private Puzzle.TestHook testHookSolved;
    private Puzzle puzzle36728154b;
    private Puzzle.TestHook testHook36728154b;

    @BeforeEach
    public void init(){
        puzzleSolved = new Puzzle("b12 345 678");
        testHookSolved = puzzleSolved.new TestHook();
        puzzle36728154b = new Puzzle("367 281 54b");
        testHook36728154b = puzzle36728154b.new TestHook();
    }

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

    @Test
    public void testGetBlankRowCol(){
        int blankRow = testHook36728154b.getBlankRow();
        int blankCol = testHook36728154b.getBlankCol();
        assertEquals(blankRow, 2);
        assertEquals(blankCol, 2);

        int blankRow2 = testHookSolved.getBlankRow();
        int blankCol2 = testHookSolved.getBlankCol();
        assertEquals(blankRow2, 0);
        assertEquals(blankCol2, 0);
    }

    @Test
    public void testIsValidMove(){
        assertTrue(puzzleSolved.isValidMove(DOWN));
        assertTrue(puzzleSolved.isValidMove(RIGHT));
        assertFalse(puzzleSolved.isValidMove(LEFT));
        assertFalse(puzzleSolved.isValidMove(UP));

        assertFalse(puzzle36728154b.isValidMove(DOWN));
        assertFalse(puzzle36728154b.isValidMove(RIGHT));
        assertTrue(puzzle36728154b.isValidMove(LEFT));
        assertTrue(puzzle36728154b.isValidMove(UP));
    }

    @Test
    public void testMove(){
        puzzleSolved.move(RIGHT);
        assertEquals(puzzleSolved.toString(), "1b2 345 678");

        puzzleSolved.move(DOWN);
        assertEquals(puzzleSolved.toString(), "142 3b5 678");

        assertThrows(Exception.class, ()-> puzzle36728154b.move(RIGHT));
    }

    @Test
    public void testRandomizeState(){
        System.out.println("State before random move " + puzzleSolved);
        puzzleSolved.randomizeState(1);
        System.out.println("State after 1 random move " + puzzleSolved);
        puzzleSolved.randomizeState(1);
        System.out.println("State after 1 more random move " + puzzleSolved);
        puzzleSolved.randomizeState(100);
        System.out.println("State after 100 random moves " + puzzleSolved);

    }
}