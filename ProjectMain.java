import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;
import java.util.Scanner;


public class ProjectMain extends JPanel {
    private static Scanner usaScanner;
    private static File usaFile = new File("sale.txt");
    private static int NumOfVertices;
    private static int NumOfEdges;
    private static int endNode;
    private ArrayList<NodeVertex> visitedList;
    private static NodeVertex[] graph;

    public static void main(String[] args) throws FileNotFoundException {
        Scanner scan = new Scanner(System.in);
        //reads in the file and assigns vertices and edges
        readInUsa();
        System.out.println("Hello! Welcome to my map of the United States! I will give you a visual representation of the shortest path from two nodes.");
        System.out.println("Please give me a start node name. (It is a number. See the sale.txt to see nodes x and y coordinates)");
        int begin = 0;
        begin = scan.nextInt();
        System.out.println("What is the end node?");
        endNode = scan.nextInt();
        NodeVertex start = new NodeVertex(begin, graph[begin].getX(), graph[begin].getY());
        //runs alg using graph and starting node
        Djkistra(graph, start);
        //visual component (displays map and then start and end with connections drawn in red)
        ProjectMain points = new ProjectMain();
        JFrame frame = new JFrame("Points");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(points);
        frame.setSize(350, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
    //graphics
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.black);

        Dimension size = getSize();
        int w = size.width;
        int h = size.height;

        //drawing basic map
        for (int i = 0; i < NumOfVertices; i++) {
            int x = (int) ((graph[i].getX() * 1.0) / 10000 * w);
            //frame has 0,0 top left, usa has 0,0 bottom left
            int y = size.height - (int) ((graph[i].getY() * 1.0) / 4000 * h);
            g2d.drawLine(x, y, x, y);
        }
        //draws path on map
        ArrayList<Integer> arr = getPath(graph[endNode]);
        int startx = (int) ((graph[arr.get(0)].getX() * 1.0) / 10000 * w);
        int starty = size.height - (int) ((graph[arr.get(0)].getY() * 1.0) / 4000 * h);
        g2d.setColor(Color.red);
        g2d.drawLine(startx, starty, startx, starty);
        for (int x = 0; x < arr.size() - 1; x++) {
            int xcoord = (int) ((graph[arr.get(x)].getX() * 1.0) / 10000 * w);
            int ycoord = size.height - (int) ((graph[arr.get(x)].getY() * 1.0) / 4000 * h);
            int xcoord2 = (int) ((graph[arr.get(x + 1)].getX() * 1.0) / 10000 * w);
            int ycoord2 = size.height - (int) ((graph[arr.get(x + 1)].getY() * 1.0) / 4000 * h);
            g2d.drawLine(xcoord, ycoord, xcoord2, ycoord2);
        }
        //prints path of nodes on graphics
        String message = "Path of Nodes: ";
        for (int x = 0; x < arr.size(); x++) {
            message += arr.get(x) + " , ";
        }
        Font f1 = new Font("Arial", Font.BOLD, 10);
        g.setFont(f1);
        g.drawString(message, 10, h - 10);
    }

    //reads in file
    public static void readInUsa() throws FileNotFoundException {
        usaScanner = new Scanner(usaFile);
        NumOfVertices = usaScanner.nextInt();
        NumOfEdges = usaScanner.nextInt();
        graph = new NodeVertex[NumOfVertices];
        for (int i = 0; i < NumOfVertices; i++) {
            int name = usaScanner.nextInt();
            int x = usaScanner.nextInt();
            int y = usaScanner.nextInt();
            NodeVertex n = new NodeVertex(name, x, y);
            graph[name] = n;
        }
        //gets ride of space line
        usaScanner.nextLine();
        while (usaScanner.hasNextLine()) {
            int vertex = usaScanner.nextInt();
            int connection = usaScanner.nextInt();
            graph[vertex].addConnection(graph[connection]);
            graph[connection].addConnection(graph[vertex]);
        }
    }
    //perform algorithm within this method
    public static void Djkistra(NodeVertex[] graph, NodeVertex start) {
        //creates priority queue
        Queue queue = new Queue(NumOfVertices);
        //alg sets start distance to 0 and begins with all others at max value
        for (int i = 0; i < graph.length; i++) {
            if (graph[i].getName() == start.getName()) {
                graph[i].setDistance(0);
            } else {
                graph[i].setDistance(Double.MAX_VALUE);
            }
            graph[i].setPrev(-1);
            queue.enqueue(graph[i]);
        }
        //have to visit every node
        for (int index = 0; index < NumOfVertices; index++) {
            NodeVertex currentNode = queue.getLowest();
            ArrayList<NodeVertex> connections = currentNode.getConnections();
            for (int connection = 0; connection < connections.size(); connection++) {
                NodeVertex connectedNode = connections.get(connection);
                //check has not been visited
                if (connectedNode.checkVisited() != true) {
                    double newDistance = currentNode.getDistance() + distBetweenTwo(connectedNode, currentNode);
                    //only reset distance if newDistance is shorter
                    if (connectedNode.getDistance() > newDistance) {
                        connectedNode.setDistance(newDistance);
                        connectedNode.setPrev(currentNode.getName());
                    }
                }
            }
            currentNode.setVisited(true);
        }
        //prints path in console too
        System.out.println("The path from the start node to your end node is: ");
        System.out.println(getPath(graph[endNode]));
    }

    //traverses through previous and gets path
    //uses reversePath and forwardPath array lists because at first the path is reversed
    public static ArrayList<Integer> getPath(NodeVertex x) {
        ArrayList<Integer> reversePath = new ArrayList<Integer>();
        ArrayList<Integer> forwardPath = new ArrayList<Integer>();
        int temp;
        reversePath.add(x.getName());
        while (graph[x.getName()].getPrev() != -1) {
            temp = graph[x.getName()].getPrev();
            reversePath.add(temp);
            x = graph[temp];
        }
        for (int i = reversePath.size() - 1; i >= 0; i--) {
            forwardPath.add(reversePath.get(i));
        }
        return forwardPath;
    }
    //distance formula between 2 nodes x and y coordinates
    public static double distBetweenTwo(NodeVertex node1, NodeVertex node2) {
        double x = (node1.getX() - node2.getX()) * (node1.getX() - node2.getX());
        double y = (node1.getY() - node2.getY()) * (node1.getY() - node2.getY());
        double sub = x + y;
        return Math.sqrt(sub);
    }
    //sets correct distance between 2 nodes (uses distBetweenTwo)
    public static void setCorrectDistance(NodeVertex node1, NodeVertex node2) {
        if (node1.getDistance() + distBetweenTwo(node1, node2) < node2.getDistance()) {
            node2.setDistance(node1.getDistance() + distBetweenTwo(node1, node2));
            int num = node2.getName();
            graph[num].setPrev(node1.getName());
        }
    }
}