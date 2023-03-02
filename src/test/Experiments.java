package test;

import org.junit.jupiter.api.Test;
import puzzle.AStarSearch;
import puzzle.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class Experiments {

    /**
     * Experiment 1: How does fraction of solvable puzzles from random initial states vary with the maxNodes limit? <br>
     * Starting states - fully randomized puzzles <br>
     * Gradually increase the number of max nodes allowed (exponentially?)
     * Test 20 puzzles for all three
     */
    @Test
    public void experiment1(){

        Map<Integer, Double> maxNodesToFractionSolvedAH1 = new HashMap<>();
        Map<Integer, Double> maxNodesToFractionSolvedAH2 = new HashMap<>();
        Map<Integer, Double> maxNodesToFractionSolvedBeam = new HashMap<>();

        //Exponentially increase the maxNodes allowed
        for (int maxNodes = 100; maxNodes < 5000001; maxNodes *=10){
            int attempts = 0;
            int solvedAStarH1 = 0;
            int solvedAStarH2 = 0;
            int solvedBeam= 0;

            //Run the experiment 10 times for each puzzle
            for (int run = 1; run<=10; run++){

                Puzzle p = new Puzzle();
                p.setMaxNodes(maxNodes);


                try {
                    fullyRandomize(p);
                    AStarSearch.solveAStarNoPrint(p, Heuristic.H1);
                    solvedAStarH1++;
                } catch (Exception ignored){}
                try {
                    fullyRandomize(p);
                    AStarSearch.solveAStarNoPrint(p, Heuristic.H2);
                    solvedAStarH2++;
                } catch (Exception ignored){}
                try {
                    fullyRandomize(p);
                    LocalBeamSearch.solveLocalBeamNoPrint(p, p.getMaxNodes(), 50000);
                    solvedBeam++;
                } catch (Exception ignored){}

                attempts++;
            }
            maxNodesToFractionSolvedAH1.put(maxNodes, (double) solvedAStarH1/attempts);
            maxNodesToFractionSolvedAH2.put(maxNodes, (double) solvedAStarH2/attempts);
            maxNodesToFractionSolvedBeam.put(maxNodes, (double) solvedBeam/attempts);

        }
        System.out.println("Results - Shows {maxNodes = fractionSolved }");
        System.out.println("A* H1: " + maxNodesToFractionSolvedAH1);
        System.out.println("A* H2: " + maxNodesToFractionSolvedAH2);
        System.out.println("Local Beam: " + maxNodesToFractionSolvedBeam);


    }

    /**
     *
     */
    @Test
    public void experiment2(){
        System.out.println("H1 nodes generated: ");
        for (int i = 1; i <= 50; i++){
            Puzzle p = new Puzzle();
            p.setMaxNodes(500000);
            fullyRandomize(p);
            AStarSearch.solveAStarNoPrint(p, Heuristic.H1);
            System.out.print(", ");
        }
        System.out.println("\nH2 nodes generated: ");
        for (int i = 1; i <= 50; i++){
            Puzzle p = new Puzzle();
            p.setMaxNodes(500000);
            fullyRandomize(p);
            AStarSearch.solveAStarNoPrint(p, Heuristic.H2);
            System.out.print(", ");
        }
    }

    /**
     * How does the solution length (number of moves needed to reach the goal state) vary
     * across the three search methods?
     */
    @Test
    public void experiment3(){
        //Collect the solution length for all methods in increasingly randomized puzzles

        int [][] randMovesToSolutionSteps = new int[4][50];
        int RAND_MOVES = 0;
        int H1_LENGTH = 1;
        int H2_LENGTH = 2;
        int BEAM_LENGTH = 3;

        int i = 0;
        for (int randMoves = 10; randMoves <= 500; randMoves+=10){
            System.out.println("Attempting " + randMoves + " random moves.");
            randMovesToSolutionSteps[RAND_MOVES][i] = randMoves;

            int seed = randMoves;
            Puzzle p = new Puzzle(seed);
            p.setMaxNodes(100000);
            p.randomizeState(randMoves);

            //H1
            Move h1Sol = AStarSearch.solveAStarNoPrint(p, Heuristic.H1);
            int h1Length = -1;
            if (h1Sol != null){
                h1Length = movesFromStart(h1Sol);
            }
            randMovesToSolutionSteps[H1_LENGTH][i] = h1Length;

            //H2
            Move h2Sol = AStarSearch.solveAStarNoPrint(p, Heuristic.H2);
            int h2Length = -1;
            if (h2Sol != null){
                h2Length = movesFromStart(h2Sol);
            }
            randMovesToSolutionSteps[H2_LENGTH][i] = h2Length;

            //Beam
            int k = 10000;
            Move beamSol = LocalBeamSearch.solveLocalBeamNoPrint(p, p.getMaxNodes(), k);
            int beamLength = -1;
            if (beamSol != null){
                beamLength = movesFromStart(beamSol);
            }
            randMovesToSolutionSteps[BEAM_LENGTH][i] = beamLength;

            i++;
        }
        System.out.println("Random steps: " + Arrays.toString(randMovesToSolutionSteps[RAND_MOVES]));
        System.out.println("H1 Solution length: " + Arrays.toString(randMovesToSolutionSteps[H1_LENGTH]));
        System.out.println("H2 Solution length: " + Arrays.toString(randMovesToSolutionSteps[H2_LENGTH]));
        System.out.println("Bm Solution length: " + Arrays.toString(randMovesToSolutionSteps[BEAM_LENGTH]));
    }


    @Test
    public void experiment3B(){
        ArrayList<Boolean> sameValueForSolvedPuzzles = new ArrayList<>();

        int beamFailures = 0;
        int MAX_RUNS = 7000;
        for (int run = 1; run <= MAX_RUNS; run++) {
            int seed = run;
            Puzzle p = new Puzzle(seed);
            int RANDOM_STEPS = 30;
            p.randomizeState(RANDOM_STEPS);

            //H1
            Move h1Sol = AStarSearch.solveAStarNoPrint(p, Heuristic.H1);
            int h1Length = -1;
            if (h1Sol != null) {
                h1Length = movesFromStart(h1Sol);
            }

            //H2
            Move h2Sol = AStarSearch.solveAStarNoPrint(p, Heuristic.H2);
            int h2Length = -1;
            if (h2Sol != null) {
                h2Length = movesFromStart(h2Sol);
            }

            //Beam
            int k = 10000;
            Move beamSol = LocalBeamSearch.solveLocalBeamNoPrint(p, p.getMaxNodes(), k);
            int beamLength = -1;
            if (beamSol != null) {
                beamLength = movesFromStart(beamSol);
            } else {
                beamFailures++;
            }

            if (h1Length!=-1 && h2Length!=-1 && beamLength!=-1) {
                boolean allSameLength = h1Length == h2Length && h2Length == beamLength;
                sameValueForSolvedPuzzles.add(allSameLength);
            }
        }
        System.out.println(sameValueForSolvedPuzzles);
        System.out.println("Beam failures: " + beamFailures + "/ " + MAX_RUNS);
        System.out.println("All solved puzzles match length: " + sameValueForSolvedPuzzles.stream().allMatch(b -> b));
        System.out.println("Number of puzzles where all solutions were found: " + sameValueForSolvedPuzzles.size());
    }

    @Test
    public void experiment4(){
        //Question - can Local Beam find a solution given enough nodes?
        Puzzle p = new Puzzle();
        fullyRandomize(p);
        p.setMaxNodes(10000000);

        LocalBeamSearch.solveLocalBeam(p, p.getMaxNodes(), 10000);
    }

    private void fullyRandomize(Puzzle p) {
        int RANDOM_MOVES = 500;
        p.randomizeStateNoSeed(RANDOM_MOVES);
    }

    private int movesFromStart(Move move){
        Stack<Move> stack = new Stack<>();
        while(move.prev() != null){
            stack.add(move);
            move = move.prev();
        }
        return stack.size();
    }
}
