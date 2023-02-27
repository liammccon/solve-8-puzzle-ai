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
        /*LocalBeamSearch.solveLocalBeam(new Puzzle(SOLVED), maxNodes, k);

        k = 5;
        maxNodes = 100;
        LocalBeamSearch.solveLocalBeam(new Puzzle("1b2 345 678"), maxNodes, k);

        k = 100;
        maxNodes = 1000;
        LocalBeamSearch.solveLocalBeam(new Puzzle("12b 345 678"), maxNodes, k);
*/
        k = 1000000;
        maxNodes = 100000;
        LocalBeamSearch.solveLocalBeam(new Puzzle("125 b34 678"), maxNodes, k);


    }

}