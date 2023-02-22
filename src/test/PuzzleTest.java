package test;

import org.junit.jupiter.api.Test;
import puzzle.Puzzle;

import static org.junit.jupiter.api.Assertions.*;

class PuzzleTest {

    @Test
    public void testBuildPuzzle(){
        Puzzle puzzle = new Puzzle("b12 345 678");
    }
}