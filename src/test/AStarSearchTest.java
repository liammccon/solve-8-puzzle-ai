package test;

import org.junit.jupiter.api.Test;
import puzzle.AStarSearch;
import puzzle.*;

import static org.junit.jupiter.api.Assertions.*;

class AStarSearchTest {

    private static final String SOLVED = "b12 345 678";
    private static final String p381b14576 = "382 b14 576";

    @Test
    public void testTileScoreH1(){
        int [][] solvedBoard = new Puzzle(SOLVED).getBoard();
        for(int i = 0; i<3; i++){
            for (int j=0; j<3; j++){
                int scoreSolved = AStarSearch.TestHook.tileScoreH1(solvedBoard, i, j);
                assertEquals(0, scoreSolved);
            }
        }
    }

    @Test
    public void testCalculateH2(){
        int solvedH2 = AStarSearch.TestHook.calculateHeuristic(SOLVED, Heuristic.H2);
        assertEquals(0, solvedH2);

        int solvedH2B = AStarSearch.TestHook.calculateHeuristic(p381b14576, Heuristic.H2);
        assertEquals(12, solvedH2B);
    }

    @Test
    public void testCalculateH1(){
        int solvedH1 = AStarSearch.TestHook.calculateHeuristic(SOLVED, Heuristic.H1);
        assertEquals(0, solvedH1);

        int h1ShouldBe6 = AStarSearch.TestHook.calculateHeuristic(p381b14576, Heuristic.H1);
        assertEquals(6, h1ShouldBe6);
    }

    @Test
    public void testSolveAStar(){
        long max_nodes = 100000;
        Move solved = AStarSearch.solveAStar(new Puzzle(SOLVED), Heuristic.H1);

        Move solvedOneMove = AStarSearch.solveAStar(new Puzzle("1b2 345 678"), Heuristic.H1, max_nodes);


        Move solvedHard = AStarSearch.solveAStar(new Puzzle(p381b14576), Heuristic.H2, max_nodes);


        Move solvedMine = AStarSearch.solveAStar(new Puzzle("b56 312 784"), Heuristic.H1, max_nodes);
    }
}