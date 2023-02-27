package test;

import org.junit.jupiter.api.Test;
import puzzle.LocalBeamSearch;
import puzzle.Puzzle;

import static org.junit.jupiter.api.Assertions.*;
import static puzzle.Puzzle.SOLVED;

class LocalBeamSearchTest {
    @Test
    public void testLocalBeam(){
        int k = 5;
        int maxNodes = 100;
        LocalBeamSearch.solveLocalBeam(new Puzzle(SOLVED), maxNodes, k);
    }

}