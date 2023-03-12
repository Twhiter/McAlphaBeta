import info.kwarc.kalah.KalahState;
import info.kwarc.kalah.KalahState.GameResult;
import info.kwarc.kalah.KalahState.Player;

import java.util.Stack;


/*****
 * stack implementation of Alpha Beta pruning
 */
public class
AlphaBeta {

    static MonteAgent agent;

    static int MAX = 1000;

    static int maxDepth = 5;

    static Stack<StackInfo> stack;


    static public Double search(KalahState root) {


        double alpha = -MAX - 1;
        double beta = MAX + 1;
        Util.optim1 = null;
        Util.optim2 = null;
        stack = new Stack<>();
        return doSearch(root, maxDepth, alpha, beta);

    }


    static private Double doSearch(KalahState root, int maxDepth, double alpha, double beta) {


        var top = new StackInfo(root, alpha, beta, maxDepth);
        stack.push(top);


        while (!stack.isEmpty()) {
            if (agent.shouldStopWrapper())
                return null;

            StackInfo node = stack.peek();

            //time to evaluate
            if (node.depth == 0 || node.s.result() != GameResult.UNDECIDED) {
                node.value = evaluate(node.s);

                //force it to update its parent in next if else block
                node.idx = node.s.numberOfMoves();
            }

            //iterate all the children node
            if (node.idx < node.s.numberOfMoves()) {

                //prune and update its value to parent
                if (node.alpha >= node.beta) {
                    var deleted = stack.pop();
                    update(stack.peek(),deleted);

                }
                else {
                    KalahState newState = new KalahState(node.s);
                    newState.doMove(node.s.getMoves().get(node.idx));
                    node.idx++;
                    stack.push(new StackInfo(newState, node.alpha, node.beta, node.depth - 1));
                }
            }
            //iterate over or it has reached the terminal node
            else {

                stack.pop();

                // the root
                if (stack.isEmpty())
                    return node.value;

                //update the parent
                else {
                    var parent = stack.peek();
                    update(parent, node);
                }
            }


        }

        return top.value;
    }


    static private void update(StackInfo parent, StackInfo node) {

        //Max
        if (parent.s.getSideToMove() == Player.SOUTH) {
            parent.value = Math.max(parent.value, node.value);
            parent.alpha = Math.max(parent.alpha, parent.value);

            if (parent.depth == maxDepth)
                Util.collect(parent.value, parent.s.getMoves().get(parent.idx - 1), node.s, true);

        }
        //Min
        else {
            parent.value = Math.min(parent.value, node.value);
            parent.alpha = Math.min(parent.value, node.value);

            if (parent.depth == maxDepth)
                Util.collect(parent.value, parent.s.getMoves().get(parent.idx - 1), node.s, false);

        }
    }


    static private double evaluate(KalahState s) {

        int factor = s.getSideToMove() == Player.SOUTH ? 1 : -1;

        if (s.result() == GameResult.WIN || s.result() == GameResult.KNOWN_WIN)
            return factor * MAX;
        else if (s.result() == GameResult.LOSS || s.result() == GameResult.KNOWN_LOSS)
            return factor * -MAX;
        else if (s.result() == GameResult.DRAW)
            return 0;

        return s.getStoreSouth() - s.getStoreNorth();
    }
}

class Util {

    static Triple optim1, optim2;

    public static void collect(double v, int move, KalahState state, boolean isMax) {
        if (isMax) {

            if (optim1 == null)
                optim1 = new Triple(v, move, state);
            else if (v > optim1.v) {
                optim2 = optim1;
                optim1 = new Triple(v, move, state);
            } else if (optim2 == null)
                optim2 = new Triple(v, move, state);
            else if (v > optim2.v)
                optim2 = new Triple(v, move, state);
        } else {

            if (optim1 == null)
                optim1 = new Triple(v, move, state);
            else if (v < optim1.v) {
                optim2 = optim1;
                optim1 = new Triple(v, move, state);
            } else if (optim2 == null)
                optim2 = new Triple(v, move, state);
            else if (v < optim2.v)
                optim2 = new Triple(v, move, state);
        }
    }
}

class StackInfo {
    public KalahState s;
    public double alpha;
    public double beta;
    public int depth;

    public double value;

    //next to visit
    public int idx = 0;


    public StackInfo(KalahState s, double alpha, double beta, int depth) {
        this.s = s;
        this.alpha = alpha;
        this.beta = beta;
        this.depth = depth;
        this.value = this.s.getSideToMove() == Player.SOUTH ? -AlphaBeta.MAX : AlphaBeta.MAX;
    }
}


class Triple {

    public double v;
    public int move;
    public KalahState state;

    public Triple(double v, int move, KalahState state) {
        this.v = v;
        this.move = move;
        this.state = state;
    }
}