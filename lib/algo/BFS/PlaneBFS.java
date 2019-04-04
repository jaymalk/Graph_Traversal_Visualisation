import java.awt.*;
import java.awt.event.KeyEvent;

public class PlaneBFS{
    protected int size, totalSteps, finalX, finalY;
    protected NodeBFS sites[][], current;
    protected boolean traveled;
    private MyQueue<NodeBFS> nodesInProcess;

    public PlaneBFS(int size) {
        this.size = size;
        this.totalSteps = 1;
        this.current = null;
        this.traveled = false;
        this.finalX = size-1;
        this.finalY = size-1;
        this.nodesInProcess = new MyQueue<>();
        this.sites = new NodeBFS[size][size];
        BuildGrid(size);
        showGrid();
        startSimulation();
    }

    private void startSimulation() {
        int start = 0;
        int mode = 0;
        while(true) {

            if(start == 0) {

                if(StdDraw.isKeyPressed(KeyEvent.VK_A))
                    mode = 1;
                if(StdDraw.isKeyPressed(KeyEvent.VK_R))
                    mode = 2;

                try {
                    if(StdDraw.isMousePressed()) {
                        int x = (int)StdDraw.mouseX();
                        int y = (int)StdDraw.mouseY();
                        if(mode == 2) {
                            sites[x][y].unblock();
                        }
                        else if(mode == 1) {
                            sites[x][y].block();
                        }
                    }
                }
                catch(Exception e) {}

                if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER))
                    start = 1;


                if(StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                    while(true) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setStartPosition(x, y);
                            break;
                        }
                    }
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                    while(true) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setEndPosition(x, y);
                            break;
                        }
                    }
                }
            }

            else if(start == 1) {
                try {
                    Thread.sleep(1);
                    nextStep();
                }
                catch(Exception e){};

                if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER))
                    start = 2;
            }

            else {
                if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER))
                    start = 1;
            }

            if(StdDraw.isKeyPressed(KeyEvent.VK_X)) {
                return;
            }
        }
    }

    protected void BuildGrid(int size) {
        int height = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.2);
        StdDraw.setCanvasSize(height, height);
        StdDraw.setXscale(-1, size+1);
        StdDraw.setYscale(-1, size+1);
        StdDraw.setPenRadius(0.005);
    }

    public void setStartPosition(int i, int j) {
        try {
            assert(totalSteps == 1):"Walk has already been initiated. Can't set start now.";
            if(i<0 || i>=size || j<0 || j>= size)
                throw new IllegalArgumentException();
            this.current = sites[i][j];
            if(this.current.isBlocked())
                this.current.unblock();
            StdDraw.setPenColor(StdDraw.GREEN);
            StdDraw.filledCircle(i+0.5, j+0.5, 0.5);

        }
        catch(IllegalArgumentException e) {
            System.out.println("Indexes are out of bound.");
        }
        catch(AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    public void setEndPosition(int i, int j) {
        try {
            assert(totalSteps == 1):"Walk has already been initiated. Can't set end now.";
            if(i<0 || i>=size || j<0 || j>= size)
                throw new IllegalArgumentException();
            this.finalX = i;
            this.finalY = j;
            if(this.sites[i][j].isBlocked())
                this.sites[i][j].unblock();
            StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
            StdDraw.filledCircle(finalX+0.5, finalY+0.5, 0.5);
        }
        catch(IllegalArgumentException  e) {
            System.out.println("Indexes are out of bound.");
        }
        catch(AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    protected void showGrid() {
        StdDraw.enableDoubleBuffering();
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++) {
                sites[i][j] = new NodeBFS(i, j);
                sites[i][j].draw();
            }
        this.current = sites[0][0];
        StdDraw.show();
        StdDraw.disableDoubleBuffering();
    }

    public int stepsCovered() {
        return totalSteps;
    }

    public void nextStep() {
        if(totalSteps++ == 1)
            current.setParent(null);
        current.process();
        if(isValid(current.x(), current.y()+1)) {
            sites[current.x()][current.y()+1].setParent(current);
            putInQueue(current.x(), current.y()+1);
        }
        if(isValid(current.x()+1, current.y())) {
            sites[current.x()+1][current.y()].setParent(current);
            putInQueue(current.x()+1, current.y());
        }
        if(isValid(current.x(), current.y()-1)) {
            sites[current.x()][current.y()-1].setParent(current);
            putInQueue(current.x(), current.y()-1);
        }
        if(isValid(current.x()-1, current.y())) {
            sites[current.x()-1][current.y()].setParent(current);
            putInQueue(current.x()-1, current.y());
        }
        if(nodesInProcess.isEmpty())
            if(!traveled)
                throw new IllegalStateException("This maze is not solvable.");
        current = nodesInProcess.dequeue();
        if(current.x() == finalX && current.y() == finalY) {
            traveled = true;
            current.process();
            showPath();
        }
    }

    private boolean isValid(int i, int j) {
        if(i<0 || i>=size || j<0 || j>= size)
            return false;
        if(sites[i][j].processed() || sites[i][j].isBlocked() || sites[i][j].inQueue())
            return false;
        return true;
    }

    private void putInQueue(int i, int j) {
        sites[i][j].queue();
        nodesInProcess.enqueue(sites[i][j]);
    }

    protected void showPath() {
        do {
            current.finalise();
            current = current.parent();
        } while(current!=null);
    }

    public void block(int i, int j) {
        sites[i][j].block();
    }

    public boolean traveled() {
        return traveled;
    }
}
