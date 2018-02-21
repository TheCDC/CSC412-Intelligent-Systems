//  SearchGraph class    Creed Jones    CBU    CSC512 SP18    Jan 28, 2018
//   this class supports solving the 8-puzzle problem

import java.util.LinkedList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Iterator;

public class SearchGraph {

    public static final int TESTDATA = 4;
    public static Integer hash(SGState toHash) {
        // Hash a board to speed up lookups
        int[][] board = toHash.board;
        Integer s = 0;
        for (Integer a = 0; a < board.length; a ++) {
            for (Integer b = 0; b < board[0].length; b ++) {
                // a small prime works here because each
                // element used in the hash is within [0,9]
                s *= 23;
                s += board[a][b];
            }
        }
        return s;
    }
    public SGSolution breadthFirstSearch(SGProblem problem) {  // as discussed on page 82
        LinkedList<SGNode> frontier = new LinkedList<SGNode>();
        HashSet<Integer> explored = new HashSet<Integer>();
        SGState start = new SGState(0);
        // construct the root node
        SGNode rootNode = new SGNode(start, null, new SGAction(""), 0);
        System.out.println(rootNode.getState());
        frontier.add(rootNode);
        int i = 0;
        while (true) {
            SGNode curNode = frontier.get(0);
            frontier.remove(0);
            SGState curState = curNode.getState();
            Integer curHash = hash(curState);

            i++;
            // if we found a complete solution...
            if (curState.checkDone()) {
                return new SGSolution(curNode);
            }
            // check whether we've been here before
            boolean found = false;
            found = explored.contains(curHash);
            // if not, record that we have now been here
            if (!found) {
                explored.add(curHash);
            }
            // for each possible next action
            for (SGAction a : problem.allowedActions(curState)) {
                SGNode possible = curNode.childNode(problem, a);
                found = false;
                SGState pboard = possible.getState();
                Integer pboardHash = hash(pboard);
                found = explored.contains(pboardHash);
                if (!found) {
                    // only add it if we haven't been there already
                    explored.add(pboardHash);
                    frontier.add(possible);
                }
            }
        }
        // return new SGSolution(rootNode);
    }

    public SGSolution uniformCostSearch(SGProblem p) {     // as discussed on page 84
        final int INITIALSIZE = 1000;       // arbitrary - will resize if needed

        PriorityQueue<SGNode> frontier =
            new PriorityQueue<SGNode>(INITIALSIZE, new SGNode.SortByCost());
        // almost all of the following code is copied from breadthFirstSearch
        HashSet<Integer> explored = new HashSet<Integer>();
        SGState start = new SGState(0);
        SGNode rootNode = new SGNode(start, null, new SGAction(""), 0);
        System.out.println(rootNode.getState());
        frontier.add(rootNode);
        int i = 0;
        while (true) {
            // only difference between this method and the other is API for frontier object
            SGNode curNode = frontier.poll();
            frontier.remove(0);
            SGState curState = curNode.getState();
            Integer curHash = hash(curState);

            i++;
            if (curState.checkDone()) {
                return new SGSolution(curNode);
            }
            boolean found = false;
            found = explored.contains(curHash);

            if (!found) {
                explored.add(curHash);
            }
            for (SGAction a : p.allowedActions(curState)) {
                SGNode possible = curNode.childNode(p, a);
                found = false;
                SGState pboard = possible.getState();
                Integer pboardHash = hash(pboard);
                found = explored.contains(pboardHash);
                if (!found) {
                    explored.add(pboardHash);
                    frontier.add(possible);
                }
            }
        }
    }


    public static void main(String[] args) {
        SearchGraph sg = new SearchGraph();
        try {

            // SGSolution soln = sg.uniformCostSearch(new SGProblem());
            SGSolution soln = sg.breadthFirstSearch(new SGProblem());
            System.out.println("Solution is " + soln.getLength() + " long;");
            System.out.println(soln.toString());
        } catch (java.lang.IndexOutOfBoundsException | NullPointerException e) {

            System.out.println("Puzzle has no solutions!");
        }

    }
}