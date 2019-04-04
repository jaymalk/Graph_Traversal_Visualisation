import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Arrays;

/****************************************************

GUI

****************************************************/

public class GraphTraversal {
    public static void main(String[] args) {
        while(true) {
            mediumInterface.main("".split(""));
        }
    }
}

class mediumInterface {
    public static void main(String[] args) {
        StartScreen msn = new StartScreen();
        while(!msn.flag)
            try{Thread.sleep(10);}
            catch(Exception e) {}

        String algo = msn.algo;
        int size = msn.size;

        startVisualisation(algo, size);

        StdDraw.hideFrame();

        EndScreen esn = new EndScreen();
        while(!esn.flag)
            try{Thread.sleep(10);}
            catch(Exception e) {}

        if(esn.exit)
            System.exit(0);
        else
            return;
    }

    private static void startVisualisation(String algo, int size) {
        if(algo.equals("BFS")) {
            new PlaneBFS(size);
        }
        else if(algo.equals("DFS")) {
            new PlaneDFS(size);
        }
        else if(algo.equals("Greedy BFS")) {
            new PlaneGreedyBFS(size);
        }
        else if(algo.equals("Dijkstra")) {
            new PlaneDijkstra(size);
        }
        else if(algo.equals("A*")) {
            new PlaneA_Star(size);
        }
        else {
            System.exit(0);
        }
    }
}

class EndScreen implements ActionListener {
    private JFrame endFrame;
    public boolean flag = false, exit = false;

    public EndScreen() {
        endGame();
    }

    private void endGame() {
        endFrame = new JFrame("Exit");
        endFrame.setSize(200, 100);
        endFrame.setLayout(new GridLayout(1, 2));

        endFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        JButton restart = new JButton("Restart");
        restart.addActionListener(this);
        restart.setActionCommand("restart");
        JButton end = new JButton("Exit");
        end.setActionCommand("end");
        end.addActionListener(this);

        endFrame.add(restart);
        endFrame.add(end);

        endFrame.setLocationRelativeTo(null);
        endFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if(ae.getActionCommand().equals("restart")) {
            endFrame.dispose();
        }
        else if(ae.getActionCommand().equals("end")) {
            exit = true;
        }
        flag = true;
    }
}

class StartScreen implements ItemListener, ActionListener {

    private JFrame mainFrame;
    private JLabel headerLabel, footerLabel;
    private JPanel controlPanel;
    String algo;
    JTextField jtf;
    int size;
    boolean flag;
    private JPanel panel;
    JCheckBox Dijkstra, DFS, BFS, GreedyBFS, A_Star;

    public StartScreen() {
        flag = false;
        prepareGUI();
        showBorderLayoutDemo();
    }

    private void prepareGUI() {
        mainFrame = new JFrame("Graph Traversal");
        int height = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.4);
        int width = (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1.4);
        mainFrame.setSize(width,height);
        mainFrame.setResizable(false);
        mainFrame.setLayout(new GridLayout(3, 1));

        headerLabel = new JLabel("",JLabel.CENTER );
        footerLabel = new JLabel("",JLabel.CENTER );

        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent windowEvent){
                System.exit(0);
            }
        });

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        mainFrame.add(headerLabel);
        mainFrame.add(controlPanel);
        mainFrame.add(footerLabel);

        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void showBorderLayoutDemo(){
        headerLabel.setText("Graph Traversal Visualisation");

        panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        // Make check boxes.
        JLabel jlab = new JLabel("Select the algorithm.");
        panel.add(jlab);
        BFS = new JCheckBox("BFS");
        BFS.addItemListener(this);
        panel.add(BFS);
        DFS = new JCheckBox("DFS");
        DFS.addItemListener(this);
        panel.add(DFS);
        GreedyBFS = new JCheckBox("Greedy BFS");
        GreedyBFS.addItemListener(this);
        panel.add(GreedyBFS);
        Dijkstra = new JCheckBox("Dijkstra");
        Dijkstra.addItemListener(this);
        panel.add(Dijkstra);
        A_Star = new JCheckBox("A*");
        A_Star.addItemListener(this);
        panel.add(A_Star);

        controlPanel.add(panel);
        mainFrame.setVisible(true);
    }

    private void Start(String s) {
        this.algo = s;
        panel.setVisible(false);
        panel.removeAll();
        panel.setLayout(new GridLayout(3, 1));
        jtf = new JTextField("");
        jtf.addActionListener(this);
        JLabel jlab = new JLabel("Enter Size");
        jlab.setHorizontalAlignment(JLabel.CENTER);
        footerLabel.setText("Note: Only integer values will be accpeted, all others shall be ignored and reset to -1. (Preffered range: 10-80)");
        JButton jbtn = new JButton("Create Maze!");
        jbtn.addActionListener(this);
        panel.add(jlab);
        panel.add(jtf);
        panel.add(jbtn);

        panel.setVisible(true);
    }

    private void startInfoScreen() {
        panel.setVisible(false);
        mainFrame.remove(headerLabel);
        mainFrame.remove(footerLabel);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 1));

        JPanel modePanel = new JPanel(new GridLayout(4, 1));
        modePanel.add(new JLabel("Algorithm: "+algo, JLabel.CENTER));
        modePanel.add(new JLabel("MODES", JLabel.CENTER));
        modePanel.add(new JLabel("PLANEMODE : This is the running phase of the simulation. (No changes are allowed)", JLabel.CENTER));
        modePanel.add(new JLabel("ADDMODE : This mode allows you to add/remove blocks/weights.", JLabel.CENTER));
        modePanel.setVisible(true);
        headerPanel.add(modePanel);
        headerPanel.setVisible(true);
        mainFrame.add(headerPanel, 0);


        panel.removeAll();
        panel.setLayout(new GridLayout(2, 1));
        panel.add(new JLabel("<html><font face=\"Arial\"><b>Commands</b></font></html>"));
        panel.add(new JLabel("<html><pre><font size=\"4\">Run            : R        (ADDMODE Decrease/Unblock) <br>" +
                             "Set            : A        (ADDMODE Increase/Block) <br>" +
                             "Terminal       : S/E      (Set Start/End) <br>" +
                             "Start          : Enter    (Change to PLANEMODE) <br>" +
                             "Exit           : X        (Exit a Simulation) </font></pre></html>"));
        panel.setVisible(true);

        JPanel footerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 2));
        JButton restart = new JButton("Restart");
        restart.addActionListener(this);
        restart.setActionCommand("restart");
        JButton next = new JButton("Understood, let's start!");
        next.setActionCommand("next");
        next.addActionListener(this);
        footerPanel.add(restart);
        footerPanel.add(next);
        mainFrame.add(footerPanel);
        footerPanel.setVisible(true);
    }

    public void itemStateChanged(ItemEvent ie) {
        JCheckBox cb = (JCheckBox) ie.getItem();
        if(cb.isSelected()) {
            Start(cb.getText());
        }
    }

    public void actionPerformed(ActionEvent ae) {
        try {
            if(ae.getActionCommand().equals("restart")) {
                mainFrame.dispose();
                prepareGUI();
                showBorderLayoutDemo();
            }
            else if(ae.getActionCommand().equals("next")) {
                flag = true;
                mainFrame.dispose();
            }
            else {
                size = Integer.parseInt(jtf.getText());
                if(size>0)
                    startInfoScreen();
            }
        }
        catch(Exception e) {
            jtf.setText("-1");
        }
    }
}

/****************************************************
*****************************************************
DATA STRUCTURES CLASSES
*****************************************************
****************************************************/

// HEAP
@SuppressWarnings("unchecked")
class Heap<X extends Comparable<? super X>> {
    private int lastAdd;
    private X[] heapArray;

    public Heap(X first) {
        heapArray = (X[])new Comparable[3];
        heapArray[0] = first;
        lastAdd = 0;
    }

    public Heap() {
        heapArray = (X[])new Comparable[1];
        heapArray[0] = null;
        lastAdd = -1;
    }

    public boolean isEmpty() {
        return lastAdd == -1;
    }

    public void Insert(X a) {
        if(lastAdd+1 == heapArray.length)
            heapArray = increaseheight(heapArray);
        lastAdd+=1;
        heapArray[lastAdd] = a;
        bubbleUpHeapify(lastAdd);
    }

    public void updateOnItem(X a) {
        int i = getItemIndex(a);
        if(i == -1) {
            System.out.println("Item not present in the heap.");
            return;
        }
        X item = removeAtIndex(i);
        Insert(item);
    }

    public int getItemIndex(X a) {
        for(int i=0; i<heapArray.length; i++)
            if(heapArray[i] == null)
                return -1;
            else if(heapArray[i].equals(a))
                return i;
        return -1;
    }

    private void bubbleUpHeapify(int index) {
        if(index<=0)
            return;
        X child = heapArray[index];
        X parent = heapArray[(index-1)/2];
        if(child.compareTo(parent)>=0) // child>=parent
            return;
        else {
            swapData(index, (index-1)/2);
            bubbleUpHeapify((index-1)/2);
        }
    }

    public X removeMin() {
        X removed = heapArray[0];
        heapArray[0] = heapArray[lastAdd];
        heapArray[lastAdd] = null;
        lastAdd -= 1;
        bubbleDownHeapify(0);
        return removed;
    }

    private X removeLast() {
        X last = heapArray[lastAdd];
        heapArray[lastAdd] = null;
        lastAdd -= 1;
        return last;
    }

    private X removeAtIndex(int i) {
        if(i == 0) {
            return removeMin();
        }
        if(i == lastAdd) {
            return removeLast();
        }
        try {
            X removed = heapArray[i];
            X temp = removeLast();
            heapArray[i] = temp;
            bubbleUpHeapify(i);
            bubbleDownHeapify(i);
            return removed;
        }
        catch(ArrayIndexOutOfBoundsException e) {
            System.out.println("Index is out of bound.");
            return null;
        }
    }

    private void bubbleDownHeapify(int index) {
        if(isExternal(index))
            return;
        if(right(index) == -1) {
            if(heapArray[left(index)].compareTo(heapArray[index]) < 0)
                swapData(left(index), index);
        }
        else {
            if(heapArray[left(index)].compareTo(heapArray[right(index)]) < 0) {
                if(heapArray[left(index)].compareTo(heapArray[index]) < 0) {
                    swapData(index, left(index));
                    bubbleDownHeapify(left(index));
                }
            }
            else {
                if(heapArray[right(index)].compareTo(heapArray[index]) < 0) {
                    swapData(index, right(index));
                    bubbleDownHeapify(right(index));
                }
            }
        }
    }

    private int left(int index) {
        if(2*index+1 >= heapArray.length)
            return -1;
        if(heapArray[2*index+1] == null)
            return -1;
        return 2*index+1;
    }

    private int right(int index) {
        if(2*index+2 >= heapArray.length)
            return -1;
        if(heapArray[2*index+2] == null)
            return -1;
        return 2*index+2;
    }

    private boolean isExternal(int index) {
        boolean is = false;
        if(left(index)!=-1)
            is = is || (heapArray[left(index)] != null);
        if(right(index)!=-1)
            is = is || (heapArray[right(index)] != null);
        return !is;
    }

    private void swapData(int index1, int index2) {
        X temp = heapArray[index1];
        heapArray[index1] = heapArray[index2];
        heapArray[index2] = temp;
    }

    private X[] increaseheight(X[] arr) {
        X[] newArr = (X[])new Comparable[arr.length*2+1];
        for(int i=0; i<arr.length; i++)
            newArr[i] = arr[i];
        return newArr;
    }

    public void printHeap(int... values) {
        int i=0, space=0;
        if(values.length == 2){
            i = values[0];
            space = values[1];
        }
        if(i>=heapArray.length)
            return;
        if(heapArray[i] == null)
            return;
        printHeap(2*i+1, space+2);
        for(int k = 0; k<space; k++)
            System.out.print("   ");
        System.out.println("  > "+heapArray[i]);
        printHeap(2*i+2, space+2);
    }
}

// STACK
class MyStack<Item> {
  private Node first = null;

  private class Node {
    private Item item;
    private Node next;
  }

  public Item pop() {
    if(isEmpty())
      throw new NullPointerException("Stack is Empty!");
    Item item = first.item;
    first = first.next;
    return item;
  }

  public void push(Item item) {
    Node oldfirst = first;
    first = new Node();
    first.next = oldfirst;
    first.item = item;
  }

  public boolean isEmpty() {
    return first == null;
  }

  public Item peek() {
    return first.item;
  }
}

// QUEUE
class MyQueue <Item> {
  private Node last, first;

  private class Node {
    Item item;
    Node next;
  }

  public boolean isEmpty() {
    return first == null;
  }

  public void enqueue(Item item) {
    Node oldlast = last;
    last = new Node();
    last.item = item;
    last.next = null;
    if(isEmpty()) first = last;
    else          oldlast.next = last;
  }

  public Item dequeue() {
    if(isEmpty())
      throw new NullPointerException("The Queue is Empty...");
    Item item = first.item;
    first = first.next;
    if(isEmpty()) last = null;
    return item;
  }
}

/****************************************************
*****************************************************
NODE SUPERCLASS
*****************************************************
****************************************************/

class Node {
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
        StdDraw.setFont(new Font("Cordia", Font.PLAIN, 10));
    }

    public int x() {
        return this.x;
    }

    public int y() {
        return this.y;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void block() {
        this.block = true;
        this.color = StdDraw.BLACK;
        draw();
    }

    public void unblock() {
        this.block = false;
        this.color = StdDraw.LIGHT_GRAY;
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
                this.color = new Color(250, 0, 100);
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
                this.color = new Color(100, 0, 250);
                draw();
        }
        catch(AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    public void draw() {
        StdDraw.setPenColor(color);
        StdDraw.filledCircle(x+0.5, y+0.5, 0.5);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.0005);
        StdDraw.circle(x+0.5, y+0.5, 0.5);
    }
}

/****************************************************
*****************************************************
ALGORITHMS
*****************************************************
****************************************************/


/*---------------------------------------
-------------------------
BFS (Breadth First Search)
-------------------------
---------------------------------------*/

class NodeBFS extends Node {
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

class PlaneBFS{
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
        boolean sSet = false, eSet = false;
        int startSet=0, endSet=0;
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
                    while(!sSet && startSet==0) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setStartPosition(x, y);
                            sSet = true;
                            startSet = 1;
                            break;
                        }
                    }
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                    while(!eSet && endSet==0) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setEndPosition(x, y);
                            eSet = true;
                            endSet = 1;
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

                if(StdDraw.isKeyPressed(KeyEvent.VK_P))
                    start = 2;
            }

            else {
                if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER))
                    start = 1;
            }

            if(StdDraw.isKeyPressed(KeyEvent.VK_X)) {
                StdDraw.keyRemove(KeyEvent.VK_X);
                return;
            }
        }
    }

    protected void BuildGrid(int size) {
        int height = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.2);
        StdDraw.setCanvasSize(height, height);
        StdDraw.frame.setTitle("BFS");
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

/*---------------------------------------
-------------------------
DFS (Depth First Search)
-------------------------
---------------------------------------*/

class NodeDFS extends Node {
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
        try {
            assert(!block):"Node is blocked. Can't put it in heap.";
                this.color = StdDraw.YELLOW;
                this.inStack = true;
                draw();
        }
        catch(AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    public NodeDFS parent() {
        return this.parentInTravel;
    }

    public void setParent(NodeDFS parent) {
        this.parentInTravel = parent;
    }
}

class PlaneDFS {
    private MyStack<NodeDFS> nodesInProcess;
    protected int size, totalSteps, finalX, finalY;
    protected NodeDFS sites[][], current;
    protected boolean traveled;

    public PlaneDFS(int size) {
        this.size = size;
        this.totalSteps = 1;
        this.current = null;
        this.traveled = false;
        this.finalX = size-1;
        this.finalY = size-1;
        sites = new NodeDFS[size][size];
        this.nodesInProcess = new MyStack<>();
        BuildGrid(size);
        this.showGrid();
        startSimulation();
    }

    private void startSimulation() {
        int start = 0;
        int mode = 0;
        boolean sSet = false, eSet = false;
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
                    while(!sSet && true) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setStartPosition(x, y);
                            sSet = true;
                            break;
                        }
                    }
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                    while(!eSet && true) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setEndPosition(x, y);
                            eSet = true;
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
                StdDraw.keyRemove(KeyEvent.VK_X);
                return;
            }
        }
    }

    protected void BuildGrid(int size) {
        int height = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.2);
        StdDraw.setCanvasSize(height, height);
        StdDraw.frame.setTitle("DFS");
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
                sites[i][j] = new NodeDFS(i, j);
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
            putInStack(current.x(), current.y()+1);
        }
        if(isValid(current.x()+1, current.y())) {
            sites[current.x()+1][current.y()].setParent(current);
            putInStack(current.x()+1, current.y());
        }
        if(isValid(current.x(), current.y()-1)) {
            sites[current.x()][current.y()-1].setParent(current);
            putInStack(current.x(), current.y()-1);
        }
        if(isValid(current.x()-1, current.y())) {
            sites[current.x()-1][current.y()].setParent(current);
            putInStack(current.x()-1, current.y());
        }
        if(nodesInProcess.isEmpty())
            if(!traveled)
                throw new IllegalStateException("This maze is not solvable.");
        current = nodesInProcess.pop();
        if(current.x() == finalX && current.y() == finalY) {
            traveled = true;
            current.process();
            showPath();
        }
    }

    private boolean isValid(int i, int j) {
        if(i<0 || i>=size || j<0 || j>= size)
            return false;
        if(sites[i][j].processed() || sites[i][j].isBlocked() || sites[i][j].inStack())
            return false;
        return true;
    }

    private void putInStack(int i, int j) {
        sites[i][j].stack();
        nodesInProcess.push(sites[i][j]);
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


/*---------------------------------------
-------------------------
Greedy BFS (Greedy Breadth First Search)
-------------------------
---------------------------------------*/

class NodeGreedyBFS extends Node implements Comparable<NodeGreedyBFS> {
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

class PlaneGreedyBFS{
    protected int size, totalSteps, finalX, finalY;
    protected NodeGreedyBFS sites[][], current;
    protected boolean traveled;
    private Heap<NodeGreedyBFS> nodesInProcess;

    public PlaneGreedyBFS(int size) {
        this.size = size;
        this.totalSteps = 1;
        this.current = null;
        this.traveled = false;
        this.finalX = size-1;
        this.finalY = size-1;
        this.nodesInProcess = new Heap<>();
        this.sites = new NodeGreedyBFS[size][size];
        BuildGrid(size);
        showGrid();
        startSimulation();
    }

    private void startSimulation() {
        int start = 0;
        int mode = 0;
        boolean sSet = false, eSet = false;
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
                    while(!sSet && true) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setStartPosition(x, y);
                            sSet = true;
                            break;
                        }
                    }
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                    while(!eSet && true) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setEndPosition(x, y);
                            eSet = true;
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
                StdDraw.keyRemove(KeyEvent.VK_X);
                return;
            }
        }
    }

    protected void BuildGrid(int size) {
        int height = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.2);
        StdDraw.setCanvasSize(height, height);
        StdDraw.frame.setTitle("Greedy BFS");
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
            for(int x=0; x<size; x++)
                for(int y=0; y<size; y++) {
                    sites[x][y].setHeuristicValue(i, j);
                }
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
                sites[i][j] = new NodeGreedyBFS(i, j);
                sites[i][j].setHeuristicValue(size-1, size-1);
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
        if(totalSteps++ == 1) {
            current.putInHeap();
            current.setParent(null);
        }
        current.process();
        if(isValid(current.x(), current.y()+1)) {
            sites[current.x()][current.y()+1].setParent(current);
            putInHeap(current.x(), current.y()+1);
        }
        if(isValid(current.x()+1, current.y())) {
            sites[current.x()+1][current.y()].setParent(current);
            putInHeap(current.x()+1, current.y());
        }
        if(isValid(current.x(), current.y()-1)) {
            sites[current.x()][current.y()-1].setParent(current);
            putInHeap(current.x(), current.y()-1);
        }
        if(isValid(current.x()-1, current.y())) {
            sites[current.x()-1][current.y()].setParent(current);
            putInHeap(current.x()-1, current.y());
        }
        if(nodesInProcess.isEmpty())
            if(!traveled)
                throw new IllegalStateException("This maze is not solvable.");
        current = nodesInProcess.removeMin();
        if(current.x() == finalX && current.y() == finalY) {
            traveled = true;
            current.process();
            showPath();
        }
    }

    private boolean isValid(int i, int j) {
        if(i<0 || i>=size || j<0 || j>= size)
            return false;
        if(sites[i][j].processed() || sites[i][j].isBlocked() || sites[i][j].inHeap())
            return false;
        return true;
    }

    private void putInHeap(int i, int j) {
        sites[i][j].putInHeap();
        nodesInProcess.Insert(sites[i][j]);
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

/*---------------------------------------
-------------------------
Dijkstra
-------------------------
---------------------------------------*/

class NodeDijkstra extends Node implements Comparable<NodeDijkstra> {
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
        StdDraw.filledCircle(x+0.5, y+0.5, 0.5);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.circle(x+0.5, y+0.5, 0.5);
        if(oldColor.getRed() < 100)
            StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.text(x+0.5, y+0.5, this.cost+"");
    }
}

class PlaneDijkstra{
    protected int size, totalSteps, finalX, finalY;
    protected NodeDijkstra sites[][], current;
    protected boolean traveled;
    private Heap<NodeDijkstra> nodesInProcess;

    public PlaneDijkstra(int size) {
        this.size = size;
        this.totalSteps = 1;
        this.current = null;
        this.traveled = false;
        this.finalX = size-1;
        this.finalY = size-1;
        this.nodesInProcess = new Heap<>();
        this.sites = new NodeDijkstra[size][size];
        BuildGrid(size);
        showGrid();
        startSimulation();
    }

    private void startSimulation() {
        int start = 0;
        int mode = 0;
        boolean sSet = false, eSet = false;
        while(true) {

            if(start == 0) {

                if(StdDraw.isKeyPressed(KeyEvent.VK_A))
                    mode = 1;

                try {
                    if(StdDraw.isMousePressed()) {
                        int x = (int)StdDraw.mouseX();
                        int y = (int)StdDraw.mouseY();
                        if(mode == 1)
                            sites[x][y].increaseCost();
                    }
                }
                catch(Exception e) {}

                if(StdDraw.isKeyPressed(KeyEvent.VK_ENTER))
                    start = 1;


                if(StdDraw.isKeyPressed(KeyEvent.VK_S)) {
                    while(!sSet && true) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setStartPosition(x, y);
                            sSet = true;
                            break;
                        }
                    }
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                    while(!eSet && true) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setEndPosition(x, y);
                            eSet = true;
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
                StdDraw.keyRemove(KeyEvent.VK_X);
                return;
            }
        }
    }

    protected void BuildGrid(int size) {
        int height = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.2);
        StdDraw.setCanvasSize(height, height);
        StdDraw.frame.setTitle("Dijkstra");
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

    public void increaseCost(int i, int j) {
        sites[i][j].increaseCost();
    }

    protected void showGrid() {
        StdDraw.enableDoubleBuffering();
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++) {
                sites[i][j] = new NodeDijkstra(i, j);
                    sites[i][j].draw();
            }
        this.current = sites[0][0];
        StdDraw.show();
        StdDraw.disableDoubleBuffering();
    }

    public void redraw(boolean old) {
        if(!traveled()) {
            System.out.println("Complete Traversal First.");
            return;
        }
        StdDraw.enableDoubleBuffering();
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++) {
                if(!old)
                    sites[i][j].draw();
                else
                    sites[i][j].drawOld();
            }
        StdDraw.show();
        StdDraw.disableDoubleBuffering();
    }

    public int stepsCovered() {
        return totalSteps;
    }

    public void nextStep() {
        if(totalSteps++ == 1) {
            current.setParent(null);
            current.putInHeap();
        }
        current.process();
        if(isValid(current.x(), current.y()+1)) {
            if(sites[current.x()][current.y()+1].inHeap()) {
                if(sites[current.x()][current.y()+1].betterParent(current)) {
                    sites[current.x()][current.y()+1].setParent(current);
                    upDateInHeap(current.x(), current.y()+1);
                }
            }
            else {
                sites[current.x()][current.y()+1].setParent(current);
                putInHeap(current.x(), current.y()+1);
            }
        }
        if(isValid(current.x()+1, current.y())) {
            if(sites[current.x()+1][current.y()].inHeap()) {
                if(sites[current.x()+1][current.y()].betterParent(current)) {
                    sites[current.x()+1][current.y()].setParent(current);
                    upDateInHeap(current.x()+1, current.y());
                }
            }
            else {
                sites[current.x()+1][current.y()].setParent(current);
                putInHeap(current.x()+1, current.y());
            }
        }
        if(isValid(current.x(), current.y()-1)) {
            if(sites[current.x()][current.y()-1].inHeap()) {
                if(sites[current.x()][current.y()-1].betterParent(current)) {
                    sites[current.x()][current.y()-1].setParent(current);
                    upDateInHeap(current.x(), current.y()-1);
                }
            }
            else {
                sites[current.x()][current.y()-1].setParent(current);
                putInHeap(current.x(), current.y()-1);
            }
        }
        if(isValid(current.x()-1, current.y())) {
            if(sites[current.x()-1][current.y()].inHeap()) {
                if(sites[current.x()-1][current.y()].betterParent(current)) {
                    sites[current.x()-1][current.y()].setParent(current);
                    upDateInHeap(current.x()-1, current.y());
                }
            }
            else {
                sites[current.x()-1][current.y()].setParent(current);
                putInHeap(current.x()-1, current.y());
            }
        }
        if(nodesInProcess.isEmpty())
            if(!traveled)
                throw new IllegalStateException("This maze is not solvable.");
        current = nodesInProcess.removeMin();
        if(current.x() == finalX && current.y() == finalY) {
            traveled = true;
            current.process();
            showPath();
        }
    }

    private boolean isValid(int i, int j) {
        if(i<0 || i>=size || j<0 || j>= size)
            return false;
        if(sites[i][j].processed() || sites[i][j].isBlocked())
            return false;
        return true;
    }

    private void putInHeap(int i, int j) {
        sites[i][j].putInHeap();
        nodesInProcess.Insert(sites[i][j]);
    }

    private void upDateInHeap(int i, int j) {
        nodesInProcess.updateOnItem(sites[i][j]);
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

/*---------------------------------------
-------------------------
A*
-------------------------
---------------------------------------*/

class NodeA_Star extends Node implements Comparable<NodeA_Star> {
    private boolean inHeap;
    private NodeA_Star parentInTravel;
    private double greedyValue, dijkstraValue;
    private Color oldColor;

    public NodeA_Star(int x, int y) {
        super(x, y);
        this.inHeap = false;
        this.dijkstraValue = 0;
        this.oldColor = super.color;
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
            StdDraw.filledCircle(x+0.5, y+0.5, 0.5);
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.circle(x+0.5, y+0.5, 0.5);
            if(oldColor.getRed() < 100)
                StdDraw.setPenColor(StdDraw.WHITE);
            StdDraw.text(x+0.5, y+0.5, "1");
        }
    }
}

class PlaneA_Star{
    protected int size, totalSteps, finalX, finalY;
    protected NodeA_Star sites[][], current;
    protected boolean traveled;
    private Heap<NodeA_Star> nodesInProcess;

    public PlaneA_Star(int size) {
        this.size = size;
        this.totalSteps = 1;
        this.current = null;
        this.traveled = false;
        this.finalX = size-1;
        this.finalY = size-1;
        this.nodesInProcess = new Heap<>();
        this.sites = new NodeA_Star[size][size];
        BuildGrid(size);
        showGrid();
        startSimulation();
    }

    private void startSimulation() {
        int start = 0;
        int mode = 0;
        boolean sSet = false, eSet = false;
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
                    while(!sSet && true) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setStartPosition(x, y);
                            sSet = true;
                            break;
                        }
                    }
                }
                if(StdDraw.isKeyPressed(KeyEvent.VK_E)) {
                    while(!eSet && true) {
                        mode = 0;
                        if(StdDraw.isMousePressed()) {
                            int x = (int)StdDraw.mouseX();
                            int y = (int)StdDraw.mouseY();
                            setEndPosition(x, y);
                            eSet = true;
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
                StdDraw.keyRemove(KeyEvent.VK_X);
                return;
            }
        }
    }

    protected void BuildGrid(int size) {
        int height = (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.2);
        StdDraw.setCanvasSize(height, height);
        StdDraw.frame.setTitle("A Star");
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
            for(NodeA_Star[] L: sites)
                for(NodeA_Star node: L)
                    node.setGreedyValue(i, j);
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
                sites[i][j] = new NodeA_Star(i, j);
                sites[i][j].setGreedyValue(size-1, size-1);
                sites[i][j].draw();
            }
        this.current = sites[0][0];
        StdDraw.show();
        StdDraw.disableDoubleBuffering();
    }

    public void redraw(boolean old) {
        if(!traveled()) {
            System.out.println("Complete Traversal First.");
            return;
        }
        StdDraw.enableDoubleBuffering();
        for(int i=0; i<size; i++)
            for(int j=0; j<size; j++) {
                if(!old)
                    sites[i][j].draw();
                else
                    sites[i][j].drawOld();
            }
        StdDraw.show();
        StdDraw.disableDoubleBuffering();
    }

    public int stepsCovered() {
        return totalSteps;
    }

    public void nextStep() {
        if(totalSteps++ == 1) {
            current.setParent(null);
            current.putInHeap();
        }
        current.process();
        if(isValid(current.x(), current.y()+1)) {
            if(sites[current.x()][current.y()+1].inHeap()) {
                if(sites[current.x()][current.y()+1].betterParent(current)) {
                    sites[current.x()][current.y()+1].setParent(current);
                    upDateInHeap(current.x(), current.y()+1);
                }
            }
            else {
                sites[current.x()][current.y()+1].setParent(current);
                putInHeap(current.x(), current.y()+1);
            }
        }
        if(isValid(current.x()+1, current.y())) {
            if(sites[current.x()+1][current.y()].inHeap()) {
                if(sites[current.x()+1][current.y()].betterParent(current)) {
                    sites[current.x()+1][current.y()].setParent(current);
                    upDateInHeap(current.x()+1, current.y());
                }
            }
            else {
                sites[current.x()+1][current.y()].setParent(current);
                putInHeap(current.x()+1, current.y());
            }
        }
        if(isValid(current.x(), current.y()-1)) {
            if(sites[current.x()][current.y()-1].inHeap()) {
                if(sites[current.x()][current.y()-1].betterParent(current)) {
                    sites[current.x()][current.y()-1].setParent(current);
                    upDateInHeap(current.x(), current.y()-1);
                }
            }
            else {
                sites[current.x()][current.y()-1].setParent(current);
                putInHeap(current.x(), current.y()-1);
            }
        }
        if(isValid(current.x()-1, current.y())) {
            if(sites[current.x()-1][current.y()].inHeap()) {
                if(sites[current.x()-1][current.y()].betterParent(current)) {
                    sites[current.x()-1][current.y()].setParent(current);
                    upDateInHeap(current.x()-1, current.y());
                }
            }
            else {
                sites[current.x()-1][current.y()].setParent(current);
                putInHeap(current.x()-1, current.y());
            }
        }
        if(nodesInProcess.isEmpty())
            if(!traveled)
                throw new IllegalStateException("This maze is not solvable.");
        current = nodesInProcess.removeMin();
        if(current.x() == finalX && current.y() == finalY) {
            traveled = true;
            current.process();
            showPath();
        }
    }

    private boolean isValid(int i, int j) {
        if(i<0 || i>=size || j<0 || j>= size)
            return false;
        if(sites[i][j].processed() || sites[i][j].isBlocked())
            return false;
        return true;
    }

    private void putInHeap(int i, int j) {
        sites[i][j].putInHeap();
        nodesInProcess.Insert(sites[i][j]);
    }

    private void upDateInHeap(int i, int j) {
        nodesInProcess.updateOnItem(sites[i][j]);
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
