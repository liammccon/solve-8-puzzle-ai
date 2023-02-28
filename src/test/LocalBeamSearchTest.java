package test;

import org.junit.jupiter.api.Test;
import puzzle.Heuristic;
import puzzle.LocalBeamSearch;
import puzzle.Move;
import puzzle.Puzzle;

import static org.junit.jupiter.api.Assertions.*;
import static puzzle.Puzzle.SOLVED;

class LocalBeamSearchTest {
    @Test
    public void testLocalBeam(){
        int k = 10000;
        int maxNodes = 100000;

        for (int round = 1; round <= 10; round++){
            int MUST_PASS_ROUNDS = 4;
            String state = SOLVED;
            Puzzle p = new Puzzle();
            for (int i = 1; i <= round; i++){
                p.randomizeState(i, 0);
            }
            System.out.println("Attempting Local Beam Search with " + round + " random moves from start. K = " + k + ", Max = " + maxNodes + ", State = " + p);
            try {
                LocalBeamSearch.solveLocalBeam(p, maxNodes, k);
            } catch (IllegalStateException e){
                System.out.println("Attempt failed. Final state was: " + p + " with a cost of " + Heuristic.calculateHeuristic(p.toString(), Heuristic.H2));
                System.out.println(e.getMessage() + '\n');
                if (round < MUST_PASS_ROUNDS){
                    fail("Should pass " + MUST_PASS_ROUNDS + " rounds");
                }

            }
        }

    }

}