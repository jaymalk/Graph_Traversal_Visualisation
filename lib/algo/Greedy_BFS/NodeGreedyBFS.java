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
        try {
            assert(!block):"Node is blocked. Can't put it in heap.";
                this.color = StdDraw.YELLOW;
                this.inHeap = true;
                draw();
        }
        catch(AssertionError e) {
            System.out.println(e.getMessage());
        }
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
