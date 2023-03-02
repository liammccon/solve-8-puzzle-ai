package puzzle;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;

public class LocalBeamSearch {

    private static final Heuristic heuristic = Heuristic.H2;

    public static Move solveLocalBeam(Puzzle puzzle, long max_nodes, int k) {
        int generatedNodes = 0;
        Move move = makeInitialMove(puzzle);
        PriorityQueue<Move> nextMoves = new PriorityQueue<>();
        nextMoves.add(move);

        while (true) {

            PriorityQueue<Move> currentMoves = new PriorityQueue<>(nextMoves);
            nextMoves.clear();

            //generate next states for all K current states
            for (Move m : currentMoves){
                //Check for going over the max allowed generated nodes
                generatedNodes++;
                if (generatedNodes > max_nodes) {
                    System.out.print("Failed at cost " + currentMoves.remove().cost() + ". ");
                    throw new IllegalStateException("Exceeded maximum allowed number of generated nodes (" + generatedNodes + ")");
                }

                if (m.state().equals(Puzzle.SOLVED)){
                    //Solution found!
                    Move.runAndPrintSolution(puzzle, m, generatedNodes, heuristic, "Local Beam Search");
                    return m;
                }

                //Generate the states
                nextMoves.addAll(expand(m));
            }

            //select best K from the generated states AND current states and discard all el
            nextMoves.addAll(currentMoves);
            nextMoves = removePastK(nextMoves, k);
        }
    }

    private static Collection<Move> expand(Move move) {
        List<Direction> possibleDirections = Puzzle.getValidDirections(move.state());
        List<Move> moves = new ArrayList<>();
        possibleDirections.forEach(direction -> {
            String nextState = Puzzle.move(move.state(), direction);
            int distFromStart = move.distFromStart() + 1;
            Move nextMove = new Move (
                    direction,
                    move,
                    distFromStart,
                    Heuristic.calculateHeuristic(nextState, heuristic),
                    nextState
            );
            moves.add(nextMove);
        });
        return moves;
    }

    private static PriorityQueue<Move> removePastK(PriorityQueue<Move> nextMoves, int k) {
        PriorityQueue<Move> newQ = new PriorityQueue<>();
        if (nextMoves.size() > k){
            while (newQ.size() < k){
                newQ.add(nextMoves.remove());
            }
            return newQ;
        } else {
            return nextMoves;
        }
    }



    private static Move makeInitialMove(Puzzle puzzle) {
        String state = puzzle.toString();
        int calculatedHeuristic = Heuristic.calculateHeuristic(state, heuristic);
        return new Move(null, null, 0, calculatedHeuristic, state);
    }
}
