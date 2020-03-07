import java.util.ArrayList;

public class NodeVertex {
    public int prev;
    private int xCoord;
    private int yCoord;
    private int name;
    //array list of all its edges/connections
    private ArrayList<NodeVertex> connections;
    private double distance;
    //boolean to check whether or not node has been visited
    private boolean visited;

    public NodeVertex(int name, int x, int y) {
        xCoord = x;
        yCoord = y;
        this.name = name;
        connections = new ArrayList<NodeVertex>();
        visited = false;
    }

    public NodeVertex() {
        connections = new ArrayList<NodeVertex>();
        visited = false;
    }

    public int getName() {
        return name;
    }

    public void addConnection(NodeVertex n) {
        connections.add(n);
    }

    public ArrayList<NodeVertex> getConnections() {
        return connections;
    }

    public int getX() {
        return xCoord;
    }

    public int getY() {
        return yCoord;
    }

    public void setPrev(int n) {
        prev = n;
    }

    public void setDistance(double p) {
        distance = p;
    }

    public double getDistance() {
        return distance;
    }

    public int getPrev() {
        return prev;
    }

    public void setVisited(boolean x) {
        visited = x;
    }

    public boolean checkVisited() {
        return visited;
    }

    public String toString() {
        String x = "Name: " + name + "|  Distance: " + distance + "| Prev: " + prev + " | visited: " + visited;
        return x;
    }
}