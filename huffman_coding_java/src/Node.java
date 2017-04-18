/**
 * Inner class representing a node in the tree
 */
public class Node implements Comparable<Node> {
    protected Node parent, left, right;
    protected Integer frequency;
    protected Character c;

    public Node() {}

    public Node(Integer i) {
        this.frequency = i;
    }

    public Node(Character c, Integer i) {
        this.c = c;
        this.frequency = i;
    }

    public void addChildren(Node node1, Node node2) {
        switch (node1.compareTo(node2)) {
            case 1: {
                this.left = node2;
                this.right = node1;
                break;
            }

            default: {
                this.left = node1;
                this.right = node2;
            }
        }
    }

    public boolean hasCharacter() {
        return this.c != null;
    }

    @Override
    public int compareTo(Node o) {
        if (o.frequency > this.frequency) return -1;
        else if (o.frequency < this.frequency) return 1;
        return 0;
    }

    @Override
    public String toString() {
        String result = " <";
        if (this.c != null) result += this.c + ", ";
        if (this.frequency != null) result += this.frequency;
        result += ">";
        return result;
    }

}
