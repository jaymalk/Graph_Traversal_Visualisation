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
        try {
            assert(!block):"Node is blocked. Can't put it in queue.";
                this.color = StdDraw.YELLOW;
                this.inQueue = true;
                draw();
        }
        catch(AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    public NodeBFS parent() {
        return this.parentInTravel;
    }

    public void setParent(NodeBFS parent) {
        this.parentInTravel = parent;
    }
}
