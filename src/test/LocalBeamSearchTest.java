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

        LocalBeamSearch.solveLocalBeam(new Puzzle("1b2 345 678"), maxNodes, k);

        k = 100;
        maxNodes = 1000;
        LocalBeamSearch.solveLocalBeam(new Puzzle("12b 345 678"), maxNodes, k);

        k = 1000;
        maxNodes = 1000000;
        LocalBeamSearch.solveLocalBeam(new Puzzle("125 b34 678"), maxNodes, k);
        LocalBeamSearch.solveLocalBeam(new Puzzle("54b 621 873"), maxNodes, k);
        LocalBeamSearch.solveLocalBeam(new Puzzle("836 b14 275"), maxNodes, k);


    }

}