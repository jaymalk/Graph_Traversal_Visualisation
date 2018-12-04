import edu.princeton.cs.algs4.*;
import java.awt.Color;

public class NodeGreedyBFS extends Node implements Comparable<NodeGreedyBFS> {
    private boolean inHeap;
    private NodeGreedyBFS parentInTravel;
    private double heuristicValue;

    public NodeGreedyBFS(int x, int y) {
        super(x, y);
        this.inHeap = false;
        this.heuristicValue = Math.sqrt(x*x+y*y);
    }
    public boolean inHeap() {
        return inHeap;
    }

    public void putInHeap() {
        this.inHeap = true;
    }

    public void setHeuristicValue(int xf, int yf) {
        this.heuristicValue = Math.sqrt((x-xf)*(x-xf)+(y-yf)*(y-yf));
    }

    public double heuristicValue() {
        return this.heuristicValue;
    }

    public NodeGreedyBFS parent() {
        return this.parentInTravel;
    }

    public void setParent(NodeGreedyBFS parent) {
        this.parentInTravel = parent;
    }

    @Override
    public int compareTo(NodeGreedyBFS other) {
        return Double.compare(heuristicValue(), other.heuristicValue());
    }
}
