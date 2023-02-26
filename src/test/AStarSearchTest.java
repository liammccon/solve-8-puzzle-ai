package test;

import org.junit.jupiter.api.Test;
import puzzle.AStarSearch;
import puzzle.*;

import static org.junit.jupiter.api.Assertions.*;

class AStarSearchTest {

    private static final String SOLVED = "b12 345 678";
    private static final String h2Is12 = "382 b14 567";

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

        int solvedH2B = AStarSearch.TestHook.calculateHeuristic(h2Is12, Heuristic.H2);
        assertEquals(12, solvedH2B);
    }

    @Test
    public void testCalculateH1(){
        int solvedH2 = AStarSearch.TestHook.calculateHeuristic(SOLVED, Heuristic.H1);
        assertEquals(0, solvedH2);
    }
}