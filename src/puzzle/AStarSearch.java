package puzzle;

import java.util.*;

public class AStarSearch {

    private static final String SOLVED = "b12 345 678";


    public static Move solveAStar(Puzzle puzzle, Heuristic heuristic) {
        long nodesGenerated = 1;

        //Create Frontier and add initial node
        Move move = makeInitialMove(puzzle, heuristic);
        PriorityQueue<Move> frontier = new PriorityQueue<Move>();
        frontier.add(move);

        //Create Reached and add initial node
        Hashtable<String, Integer> reachedCost = new Hashtable<>();
        reachedCost.put(move.state(), move.cost());

        long maxNodes = puzzle.getMaxNodes();

        //Main loop
        while (!frontier.isEmpty()){
            //Pop from the frontier
            move = frontier.poll();

            //Solution was found
            if (move.state().equals(SOLVED)){
                Move.runAndPrintSolution(puzzle, move, nodesGenerated, heuristic, "A Star Search");
                return move;
            }

            //Generate all successive nodes (next) with helper method expand()
            for (Move next: expand(move, heuristic)){
                nodesGenerated++;
                if (nodesGenerated > maxNodes) throw new IllegalStateException("Exceeded maximum allowed number of generated nodes! (" + nodesGenerated + ")");

                //if (next is not in reached OR next.cost is less than the cost for the equivalent state in reached
                if (!reachedCost.containsKey(next.state()) || next.cost() < reachedCost.get(next.state())){
                    frontier.add(next);
                    reachedCost.put(next.state(), next.cost());
                }
            }
        }
        return null; //Does not run under correct circumstances
    }

    public static Move solveAStar(Puzzle puzzle, Heuristic heuristic, long maxNodes) {
        puzzle.setMaxNodes(maxNodes);
        return solveAStar(puzzle, heuristic);
    }


    private static Move[] expand(Move move, Heuristic heuristic) {
        //List all possible directions the blank tile can move in (successive states)
        List<Direction> possibleDirections = Puzzle.getValidDirections(move.state());

        //Create a new node (move) for each possible direction
        List<Move> moves = new ArrayList<>();
        possibleDirections.forEach(direction -> {
            String nextState = Puzzle.move(move.state(), direction);
            int newDistFromStart = move.distFromStart() + 1;
            Move nextMove = new Move (
                    direction,
                    move,
                    newDistFromStart,
                    Heuristic.calculateHeuristic(nextState, heuristic),
                    nextState
            );
            moves.add(nextMove);
        });

        //Return the generated nodes
        Move [] moveArray = new Move[moves.size()];
        moves.toArray(moveArray);
        return moveArray;
    }


    private static Move makeInitialMove(Puzzle puzzle, Heuristic heuristic) {
        String state = puzzle.toString();
        int calculatedHeuristic = Heuristic.calculateHeuristic(state, heuristic);
        Move move = new Move(null, null, 0, calculatedHeuristic, state);
        return move;
    }


    /**
     * Used for experiments. No print statements.
     */
    public static Move solveAStarNoPrint(Puzzle puzzle, Heuristic heuristic) {
        long nodesGenerated = 1;
        Move move = makeInitialMove(puzzle, heuristic);
        PriorityQueue<Move> frontier = new PriorityQueue<Move>();
        frontier.add(move);
        Hashtable<String, Integer> reachedCost = new Hashtable<>();
        reachedCost.put(move.state(), move.cost());

        long maxNodes = puzzle.getMaxNodes();

        while (!frontier.isEmpty()){
            move = frontier.poll();
            if (move.state().equals(SOLVED)){
                //Solution!
                //Move.runAndPrintSolution(puzzle, move, nodesGenerated, heuristic, "A Star Search");
                return move;
            }
            for (Move next: expand(move, heuristic)){
                nodesGenerated++;
                if (nodesGenerated > maxNodes) {
                    //throw new IllegalStateException("Exceeded maximum allowed number of generated nodes! (" + nodesGenerated + ")");
                    return null; //Needed for Experiment 3
                }
                //if (next.state is not in reached OR next.cost is less than the cost for the equivalent state in the table
                if (!reachedCost.containsKey(next.state()) || next.cost() < reachedCost.get(next.state())){
                    frontier.add(next);
                    reachedCost.put(next.state(), next.cost());
                }
            }
        }
        return null;
    }


    public class TestHook {
        public static int calculateHeuristic(String state, Heuristic heuristic) { return Heuristic.calculateHeuristic(state, heuristic);}
        public static int tileScoreH1(int[][] board, int row, int col) { return Heuristic.tileScoreH2(board, row, col); }
    }
}
