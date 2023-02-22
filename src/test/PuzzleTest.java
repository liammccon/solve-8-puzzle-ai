package test;

import org.junit.jupiter.api.Test;
import puzzle.Puzzle;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleTest {

    @Test
    public void testBuildPuzzle(){
        int [][] i = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        Puzzle puzzle = new Puzzle("b12 345 678");
        assertEquals(puzzle.toString(), "b12 345 678");
    }
}