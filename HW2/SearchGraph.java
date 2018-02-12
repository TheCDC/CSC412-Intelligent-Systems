//  SearchGraph class    Creed Jones    CBU    CSC512 SP18    Jan 28, 2018
//   this class supports solving the 8-puzzle problem

import java.util.LinkedList;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Iterator;

public class SearchGraph {

    public static final int TESTDATA = 4;
    public static Integer hash(SGState toHash) {
        int[][] board = toHash.board;
        Integer s = 0;
        for (Integer a = 0; a < board.length; a ++) {
            for (Integer b = 0; b < board[0].length; b ++) {
                s *= 27;
                s += board[a][b];
            }
        }
        return s;
    }
    public SGSolution breadthFirstSearch(SGProblem p) {  // as discussed on page 82
        LinkedList<SGNode> frontier = new LinkedList<SGNode>();
        LinkedList<SGState> explored = new LinkedList<SGState>();
        HashSet<Integer> seen = new HashSet<>();
        // {{{ YOUR CODE GOES HERE...}}}
        SGState start = new SGState(0);
        SGNode rootNode = new SGNode(start, null, new SGAction(""), 0);
        System.out.println(rootNode.getState());
        frontier.add(rootNode);
        int i = 0;
        while (true) {
            SGNode curNode = frontier.get(0);
            frontier.remove(0);
            SGState curState = curNode.getState();
            System.out.println(curState + " hashes to " + hash(curState));
            if (i % 10000 == 0) {

                System.out.println("i:" + i);
                System.out.println("frontier: " + frontier.size());
                System.out.println("explored: " + explored.size());
            }
            i++;
            if (curState.checkDone()) {
                return new SGSolution(curNode);
            }
            boolean found = false;

            for (SGState s : explored) {
                if (s.equals(curState)) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                explored.add(curState);
            }
            for (SGAction a : p.allowedActions(curState)) {
                SGNode possible = curNode.childNode(p, a);
                found = false;

                for (SGState s : explored) {
                    if (s.equals(possible.getState())) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    explored.add(possible.getState());
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
        LinkedList<SGState> explored = new LinkedList<SGState>();

        // {{{ YOUR CODE GOES HERE... }}}
        SGState start = new SGState(1);
        SGNode rootNode = new SGNode(start, null, new SGAction(""), 0);
        for (SGAction a : p.allowedActions(start)) {
            frontier.add(rootNode.childNode(p, a));
        }
        return new SGSolution(rootNode);
    }


    public static void main(String[] args) {
        SearchGraph sg = new SearchGraph();

        // SGSolution soln = sg.uniformCostSearch(new SGProblem());
        SGSolution soln = sg.breadthFirstSearch(new SGProblem());

        System.out.println("Solution is " + soln.getLength() + " long;");
        System.out.println(soln.toString());
    }
}