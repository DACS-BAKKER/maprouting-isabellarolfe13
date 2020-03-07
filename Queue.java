public class Queue {
    int n = 0;
    private int heapSize;
    private int currentSize = 0;
    private NodeVertex[] PQ;

    public Queue(int vertices) {
        heapSize = vertices;
        PQ = new NodeVertex[heapSize];
    }

    public boolean isEmpty() {
        return currentSize == 0;
    }

    //adds onto queue
    public void enqueue(NodeVertex n) {
        PQ[currentSize] = n;
        currentSize += 1;
    }

    public int getSize() {
        return currentSize;
    }
    //instead of dequeue this just returns lowest priority Node
    public NodeVertex getLowest() {
        int temp = -1;
        for (int index = 0; index < PQ.length; index++) {
            if (PQ[index].checkVisited() != true) {
                temp = PQ[index].getName();
            }
        }
        for (int x = 0; x < PQ.length; x++) {
            if (PQ[x].getDistance() < PQ[temp].getDistance() && PQ[x].checkVisited() != true) {
                temp = PQ[x].getName();
            }
        }
        return PQ[temp];
    }

    public String toString() {
        String y = "";
        for (int x = 0; x < PQ.length; x++) {
            y += PQ[x] + "\n";
        }
        return y;
    }
}
