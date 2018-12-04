import edu.princeton.cs.algs4.*;
import java.awt.Color;

public class NodeA_Star extends Node implements Comparable<NodeA_Star> {
    private boolean inHeap;
    private NodeA_Star parentInTravel;
    private double greedyValue, dijkstraValue;
    private Color oldColor;

    public NodeA_Star(int x, int y) {
        super(x, y);
        this.inHeap = false;
        super.color = StdDraw.WHITE;
        this.dijkstraValue = 0;
        this.oldColor = StdDraw.WHITE;
        this.greedyValue = -x*x-y*y;
    }


    public boolean inHeap() {
        return inHeap;
    }

    public void putInHeap() {
        try {
            assert(!block):"Node is blocked. Can't put it in heap.";
            this.oldColor = new Color(super.color.getRed(), super.color.getGreen(), super.color.getBlue());
            super.color = StdDraw.YELLOW;
            this.inHeap = true;
            draw();
        }
        catch(AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    public void setGreedyValue(int i, int j) {
        this.greedyValue = Math.sqrt((x-i)*(x-i)+(y-j)*(y-j));
    }

    public double greedyValue() {
        return this.greedyValue;
    }

    private void setDijkstraValue(NodeA_Star parent) {
        this.dijkstraValue = parent.dijkstraValue()+0.55;   // 0.55 gives closest to correct result
    }

    private double dijkstraValue() {
        return this.dijkstraValue;
    }

    public NodeA_Star parent() {
        return this.parentInTravel;
    }

    public void setParent(NodeA_Star parent) {
        this.parentInTravel = parent;
        if(parent != null)
            setDijkstraValue(parent);
    }

    public boolean betterParent(NodeA_Star parent) {
        if(parentInTravel == null)
            return true;
        if(parent.dijkstraValue() < parentInTravel.dijkstraValue())
            return true;
        return false;
    }

    @Override
    public int compareTo(NodeA_Star other) {
        return Double.compare(dijkstraValue()+greedyValue(), other.dijkstraValue()+other.greedyValue());
    }

    @Override
    public void draw() {
        super.draw();
        if(super.color.getRed() < 100)
            StdDraw.setPenColor(StdDraw.WHITE);
        if(!block && inHeap) {
            // if(inHeap)
                StdDraw.text(x+0.5, y+0.5, (int)(this.dijkstraValue+this.greedyValue)+"");
            // else
            //     StdDraw.text(x+0.5, y+0.5, (int)greedyValue+"");
        }
    }

    public void drawOld() {
        if(!block) {
            StdDraw.setPenColor(oldColor);
            StdDraw.filledSquare(x+0.5, y+0.5, 0.5);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.square(x+0.5, y+0.5, 0.5);
            if(oldColor.getRed() < 100)
                StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(x+0.5, y+0.5, "1");
        }
    }
}
