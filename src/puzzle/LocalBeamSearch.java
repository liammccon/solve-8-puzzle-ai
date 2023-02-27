package puzzle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

public class LocalBeamSearch {

    private static final Heuristic heuristic = Heuristic.H1;

    public static Move solveLocalBeam(Puzzle puzzle, long max_nodes, int k) {
        int generatedNodes = 0;
        Move firstMove = makeInitialMove(puzzle);
        PriorityQueue<Move> nextMoves = new PriorityQueue<>();
        nextMoves.add(firstMove);
        for(Move move: nextMoves) {
            //Check for going over the max allowed generated nodes
            generatedNodes++;
            if (generatedNodes > max_nodes) throw new IllegalStateException("Exceeded maximum allowed number of generated nodes! (" + generatedNodes + ")");

            if (move.state().equals(Puzzle.SOLVED)){
                //Solution found!
                Move.printSolution(move, generatedNodes, heuristic);
                return move;
            }

            nextMoves.addAll(expand(move));
            nextMoves = removePastK(nextMoves, k);
        }
        return null;
    }

    private static Collection<Move> expand(Move move) {
        List<Direction> possibleDirections = Puzzle.getValidDirections(move.state());
        List<Move> moves = new ArrayList<>();
        possibleDirections.forEach(direction -> {
            String nextState = Puzzle.move(move.state(), direction);
            Move nextMove = new Move (
                    direction,
                    move,
                    0,
                    Heuristic.calculateHeuristic(nextState, heuristic),
                    nextState
            );
            moves.add(nextMove);
        });
        return moves;
    }

    private static PriorityQueue<Move> removePastK(PriorityQueue<Move> nextMoves, int k) {
        PriorityQueue<Move> newQ = new PriorityQueue<>();
        while (newQ.size() < k){
            newQ.add(nextMoves.remove());
        }
        return newQ;
    }



    private static Move makeInitialMove(Puzzle puzzle) {
        String state = puzzle.toString();
        int calculatedHeuristic = Heuristic.calculateHeuristic(state, heuristic);
        return new Move(null, null, 0, calculatedHeuristic, state);
    }
}
