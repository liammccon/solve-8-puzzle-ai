package puzzle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.function.BiConsumer;

public class Main {
    public static void main(String[] args) {
        String fileName;
        if (args.length == 0) {
            System.out.println("Please specify a file to read from (absolute path)");
            return;
        } else {
            fileName = args[0];
        }

        File file = new File(fileName);
        try {
            Scanner scan = new Scanner(file);
            Puzzle puzzle = new Puzzle();
            String line = "";

            while (scan.hasNext()){
                line = scan.nextLine();
                //Print empty lines
                if (line.length() == 0) {
                    System.out.println();
                    continue;
                }
                //Print comments to the terminal
                if (line.charAt(0) == '#') {
                    System.out.println(line);
                    continue;
                }
                String method = line.split(" ")[0];
                if (runMethodWithArg.containsKey(method)){

                    String parameter = "";
                    boolean containsParameter = line.split(" ").length > 1;
                    if (containsParameter){
                        parameter = line.split(" ", 2)[1];
                    }

                    runMethodWithArg.get(method).accept(puzzle, parameter);
                } else {
                    System.out.println("Argument " + method + " not recognized.");
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    static final Map<String, BiConsumer<Puzzle, String>> runMethodWithArg;

    static {
        runMethodWithArg = new HashMap<>();
        runMethodWithArg.put("setState", Main::setState);
        runMethodWithArg.put("printState", Main::printState);
        runMethodWithArg.put("randomizeState", Main::randomizeState);
        runMethodWithArg.put("maxNodes", Main::maxNodes);
        runMethodWithArg.put("solveAStar", Main::solveAStar);
        runMethodWithArg.put("solveBeam", Main::solveBeam);

    }

    private static void setState(Puzzle puzzle, String state) {
        try {
            puzzle.setState(state);
        } catch(Exception e) {
            System.out.println("Error with setting state [ " + state + "]. State will stay as: " + puzzle + ". Error message: ");
            System.out.println(e.getMessage() + "\n");
        }
    }

    private static void randomizeState(Puzzle puzzle, String stringNumber) {
        try {
            int n = Integer.parseInt(stringNumber);
            puzzle.resetSeed();
            puzzle.randomizeState(n);
        } catch (Exception e){
            System.out.println("Error in randomizeState. Could not parse integer from " + stringNumber + ". State remains: " + puzzle);
        }
    }

    private static void printState(Puzzle puzzle, String paramNotNeeded) {
        puzzle.printState();
    }

    private static void maxNodes(Puzzle puzzle, String stringNumber) {
        try {
            int n = Integer.parseInt(stringNumber);
            puzzle.setMaxNodes(n);
        } catch (Exception e){
            System.out.println("Error in method maxNodes. Could not parse integer from " + stringNumber);
        }
    }

    private static void solveAStar(Puzzle puzzle, String heuristicString) {
        heuristicString = heuristicString.toLowerCase();
        Heuristic h;
        if (heuristicString.equals("h1")){
            h = Heuristic.H1;
        } else if (heuristicString.equals("h2")) {
            h = Heuristic.H2;
        } else {
            System.out.println("Error in solveAStar. Could not recognize heuristic '" + heuristicString);
            return;
        }
        System.out.println("Solving puzzle " + puzzle + " with AStarSearch and " + puzzle.getMaxNodes() + " allowed nodes.");
        AStarSearch.solveAStar(puzzle, h);
    }

    private static void solveBeam(Puzzle puzzle, String stringK) {
        try {
            int k = Integer.parseInt(stringK);
            System.out.println("Solving puzzle " + puzzle + " with Local Beam Search using k = " + k + " and " + puzzle.getMaxNodes() + " allowed nodes.");

            LocalBeamSearch.solveLocalBeam(puzzle, puzzle.getMaxNodes(), k);
        } catch (NumberFormatException e){
            System.out.println("Error in method maxNodes. Could not parse integer from " + stringK);
        } catch (IllegalStateException e) {
            System.out.println("Local Beam exceeded maximum allowed nodes.");
        }
    }



}
