import java.awt.Color;

public class NodeDijkstra extends Node implements Comparable<NodeDijkstra> {
    private boolean inHeap;
    private NodeDijkstra parentInTravel;
    private int heuristicValue;
    private int cost;
    private Color oldColor;

    public NodeDijkstra(int x, int y) {
        super(x, y);
        this.inHeap = false;
        super.color = StdDraw.WHITE;
        this.heuristicValue = 1;
        this.cost = 1;
        this.oldColor = StdDraw.WHITE;
    }

    public void increaseCost() {
        this.cost++;
        super.color = new Color(Math.max(color.getRed()-10, 0), Math.max(color.getGreen()-10, 0), Math.max(color.getBlue()-10, 0));
        draw();
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

    private void setHeuristicValue(NodeDijkstra parent) {
        this.heuristicValue = parent.heuristicValue()+this.cost;
    }

    private int heuristicValue() {
        return this.heuristicValue;
    }

    public NodeDijkstra parent() {
        return this.parentInTravel;
    }

    public void setParent(NodeDijkstra parent) {
        this.parentInTravel = parent;
        if(parent != null)
            setHeuristicValue(parent);
    }

    public boolean betterParent(NodeDijkstra parent) {
        if(parentInTravel == null)
            return true;
        if(parent.heuristicValue() < parentInTravel.heuristicValue())
            return true;
        return false;
    }

    @Override
    public int compareTo(NodeDijkstra other) {
        return Integer.compare(heuristicValue(), other.heuristicValue());
    }

    @Override
    public void draw() {
        super.draw();
        if(super.color.getRed() < 100)
            StdDraw.setPenColor(StdDraw.WHITE);
        if(inHeap)
            StdDraw.text(x+0.5, y+0.5, this.heuristicValue+"");
        else
            StdDraw.text(x+0.5, y+0.5, this.cost+"");
    }

    public void drawOld() {
        StdDraw.setPenColor(oldColor);
        StdDraw.filledSquare(x+0.5, y+0.5, 0.5);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.square(x+0.5, y+0.5, 0.5);
        if(oldColor.getRed() < 100)
            StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(x+0.5, y+0.5, this.cost+"");
    }
}
