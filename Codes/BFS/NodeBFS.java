import edu.princeton.cs.algs4.*;
import java.awt.Color;

public class NodeBFS extends Node {
    private boolean inQueue;
    private NodeBFS parentInTravel;

    public NodeBFS(int x, int y) {
        super(x, y);
        this.inQueue = false;
    }
    public boolean inQueue() {
        return inQueue;
    }

    public void queue() {
        this.inQueue = true;
    }

    public NodeBFS parent() {
        return this.parentInTravel;
    }

    public void setParent(NodeBFS parent) {
        this.parentInTravel = parent;
    }
}
