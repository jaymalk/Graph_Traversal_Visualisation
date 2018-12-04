import edu.princeton.cs.algs4.*;
import java.awt.Color;

public class Node {
    protected boolean block, processed, inQueue;
    protected Color color;
    protected int x, y;
    protected Node parentInTravel;

    public Node(int x, int y) {
        this.block = false;
        this.processed = false;
        this.color = Color.LIGHT_GRAY;
        this.inQueue = false;
        this.x = x;
        this.y = y;
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public void block() {
        this.block = true;
        this.color = StdDraw.BLACK;
        draw();
    }

    public void setParent(Node parent) {
        this.parentInTravel = parent;
    }

    public Node parent() {
        return this.parentInTravel;
    }


    public boolean processed() {
        return this.processed;
    }

    public boolean isBlocked() {
        return this.block;
    }

    public void process() {
        try {
            assert(!block):"Node is blocked. Can't traverse it.";
                this.color = StdDraw.BLUE;
                this.processed = true;
                draw();
        }
        catch(AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    public void finalise() {
        try {
            assert(processed):"Node is not yet processed. Can't finalise it.";
                this.color = StdDraw.GREEN;
                draw();
        }
        catch(AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledSquare(x+0.5, y+0.5, 0.5);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.0005);
        StdDraw.square(x+0.5, y+0.5, 0.5);
    }
}
