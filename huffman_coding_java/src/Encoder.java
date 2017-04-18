import java.io.*;
import java.util.*;


public class Encoder {

    private boolean debug;
    private File inputFile;
    private File outputFile;
    private Node root;
    private TimeUtil timeUtil;
    private String fileContent;

    public Encoder(File inputFile, File outputFile, boolean debug) throws IOException {
        this.inputFile = inputFile;
        this.outputFile = outputFile;
        this.root = null;
        this.debug = debug;
        this.timeUtil = new TimeUtil();
    }

    public File encodeFile() throws IOException {

        // encode the file
        // weird character for end of file marker
        log("Opening file " + inputFile.getName() + "...");
        fileContent = FileWriter.readFile(inputFile) + "□";
        endLog("Finished in ");

        // get a frequency table
        log("Building frequency table... ");
        FrequencyMap freqTable = buildFreqTable(fileContent);
        endLog("Finished in ");

        // get a list of nodes ordered from smallest to largest from that table
        log("Sorting frequency table...");
        List<Node> nodes = convertMapToList(freqTable);
        Collections.sort(nodes);
        endLog("Finished in ");

        // build the code tree
        log("Building code tree...");
        buildCodeTree(nodes);
        root = nodes.get(0);
        endLog("Finished in ");

        // get the encoding sequences for each character via the tree
        EncodedTable encodedTable = new EncodedTable();
        buildCodeTable(root, new ArrayList<>(), encodedTable);

        log("Encoding to file... ");
        // iterate each character and write its code to the file
        FileWriter fileWriter = new FileWriter(outputFile);
        for(Character c : fileContent.toCharArray()) {
            List<Integer> list = encodedTable.get(c);
            for(int i : list) {
                fileWriter.write(i);
            }
        }
        endLog("Finished in ");

        fileWriter.close();
        return outputFile;
    }


    /**
     * read each input character and build a map of input characters with
     * their frequency count
     */
    public FrequencyMap buildFreqTable(String s) {

        // replace space with /, newline with //, and tabs with ~
        FrequencyMap freqTable = new FrequencyMap();

        // insert each character of this string into the map
        for(Character c : s.toCharArray()) {
            freqTable.putIfAbsent(c, 0);
            freqTable.put(c, freqTable.get(c) + 1);
        }

        return freqTable;
    }


    /*
     * Traverses the min-heap and prints each node
     * @param n
    public void printMinHeap(List<Node> n) {
        List<Node> next = new ArrayList<>();
        for(Node node : n) {
            if(node != null) {
                System.out.print(node);
                next.add(node.left);
                next.add(node.right);
            }
        }
        System.out.println();
        if(next.size() > 0) printMinHeap(next);
    }*/



    /**
     * Construct a min-heap of all the nodes
     * @param nodeList
     */
    private void buildCodeTree(List<Node> nodeList) {

        if(nodeList.size() <= 1) return;

        Node left = nodeList.remove(0);
        Node right = nodeList.remove(0);

        // now we have our first parent node, and we need to add it back into the list
        Node parent = new Node(left.frequency + right.frequency);
        parent.addChildren(left, right);

        left.parent = parent;
        right.parent = parent;

        nodeList.add(parent);

        buildCodeTree(nodeList);
    }



    /**
     * Add all of the frequency map entries as nodes into a
     * list to be processed.
     * @return
     */
    private List<Node> convertMapToList(FrequencyMap freqTable) {
        List<Node> result = new ArrayList<>();
        for (Map.Entry<Character, Integer> entry : freqTable.entrySet()) {
            result.add(new Node(entry.getKey(), entry.getValue()));
        }
        return result;
    }


    /**
     * Creates a min-heap code tree from a queue of sorted nodes starting with the root.
     * @param node
     * @param binary
     * @param encodedTable
     */
    private void buildCodeTable(Node node, List<Integer> binary, EncodedTable encodedTable) {
        // base case
        if(node == null) return;

        // if n has a character, we want to add it and return
        if(node.c != null) {
            encodedTable.put(node.c, binary);
            return;
        }

        List<Integer> left = new ArrayList<>(binary);
        left.add(0);

        List<Integer> right = new ArrayList<>(binary);
        right.add(1);

        buildCodeTable(node.left, left, encodedTable);
        buildCodeTable(node.right, right, encodedTable);
    }

    public Node getRoot() { return this.root; }

    public void log(String s) {
        if(debug) {
            timeUtil.start();
            System.out.println(s);
        }
    }
    
    public void endLog(String s) {
        if(debug) System.out.println(s + timeUtil.end());
    }

    public String getContent() {
        return fileContent;
    }

    public String writeMap(EncodedTable map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Character, List<Integer>> entry : map.entrySet()) {
            sb.append(entry.getKey() + "\t" + entry.getValue() + "\n");
        }
        return sb.toString();
    }

    public String writeMap(FrequencyMap map) {
        StringBuilder sb = new StringBuilder();
        for(Map.Entry<Character, Integer> entry : map.entrySet()) {
            sb.append(entry.getKey() + "\t" + entry.getValue() + "\n");
        }
        return sb.toString();
    }
}
