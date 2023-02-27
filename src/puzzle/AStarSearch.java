package puzzle;

import java.util.*;

public class AStarSearch {

    private static final String SOLVED = "b12 345 678";


    public static Move solveAStar(Puzzle puzzle, Heuristic heuristic, long max_nodes) {
        long nodesGenerated = 1;
        Move move = makeInitialMove(puzzle, heuristic);
        PriorityQueue<Move> frontier = new PriorityQueue<Move>();
        frontier.add(move);
        Hashtable<String, Integer> reachedCost = new Hashtable<>();
        reachedCost.put(move.newState(), move.cost());
        while (!frontier.isEmpty()){
            move = frontier.poll();
            if (move.newState().equals(SOLVED)){
                //Solution!
                printPath(move, nodesGenerated, heuristic);
                return move;
            }
            for (Move next: expand(move, heuristic)){
                nodesGenerated++;
                if (nodesGenerated > max_nodes) throw new IllegalStateException("Exceeded maximum allowed number of generated nodes! (" + nodesGenerated + ")");
                //if (next.state is not in reached OR next.cost is less than the cost for the equivalent state in the table
                if (!reachedCost.containsKey(next.newState()) || next.cost() < reachedCost.get(next.newState())){
                    frontier.add(next);
                    reachedCost.put(next.newState(), next.cost());
                }
            }
        }
        return null;
    }

    public static void printPath(Move move, long generatedNodes, Heuristic heuristic){
        Stack<Move> stack = new Stack<>();
        while(move.prev() != null){
            stack.add(move);
            move = move.prev();
        }
        int moveIndex = 0;
        System.out.println("Starting state: " + move.newState());
        while(!stack.isEmpty()){
            move = stack.pop();
            moveIndex++;
            System.out.println("Move " + moveIndex + ": " + move.direction() + ". State: " + move.newState());
        }
        System.out.println("Solved using heuristic '"+heuristic+"' with " + generatedNodes + " nodes generated!\n");
    }

    private static Move[] expand(Move move, Heuristic heuristic) {
        List<Direction> possibleDirections = Puzzle.getValidDirections(move.newState());
        List<Move> moves = new ArrayList<>();
        possibleDirections.forEach(direction -> {
            String nextState = Puzzle.move(move.newState(), direction);
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



    public class TestHook {
        public static int calculateHeuristic(String state, Heuristic heuristic) { return Heuristic.calculateHeuristic(state, heuristic);}
        public static int tileScoreH1(int[][] board, int row, int col) { return Heuristic.tileScoreH2(board, row, col); }
    }
}
