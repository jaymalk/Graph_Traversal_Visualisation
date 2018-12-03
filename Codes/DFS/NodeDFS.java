import edu.princeton.cs.algs4.*;
import java.awt.Color;

public class NodeDFS extends Node {
    private boolean inStack;
    private NodeDFS parentInTravel;

    public NodeDFS(int x, int y) {
        super(x, y);
        this.inStack = false;
    }
    public boolean inStack() {
        return inStack;
    }

    public void stack() {
        this.inStack = true;
    }

    public NodeDFS parent() {
        return this.parentInTravel;
    }

    public void setParent(NodeDFS parent) {
        this.parentInTravel = parent;
    }
}
