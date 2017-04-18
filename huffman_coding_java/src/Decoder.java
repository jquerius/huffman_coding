
import java.io.IOException;
import java.io.InputStream;


public class Decoder {

    private int currentByte;
    private int bitsLeft;
    private InputStream inputStream;
    private Node root;
    private StringBuilder stringBuilder;



    public Decoder(Node root, InputStream inputStream) {
        this.root = root;
        this.inputStream = inputStream;
        stringBuilder = new StringBuilder();
    }



    public String decodeFile() throws IOException {

        while(true) {
            char symbol = readNextSymbol();
            write(symbol);
            if(symbol == '□') break;
        }

        return stringBuilder.toString();

    }



    private char readNextSymbol() throws IOException {
        Node currentNode = root;
        while(true) {
            Node next = new Node();
            int dir = readBit();
            if(dir == -1) return 256;

            if(dir == 0) next = currentNode.left;
            if(dir == 1) next = currentNode.right;

            if(next.hasCharacter()) return next.c;
            else currentNode = next;
        }

    }



    private int readBit() throws IOException {

        if (currentByte == -1) return -1;

        if (bitsLeft == 0) {
            currentByte = inputStream.read();
            if (currentByte == -1) return -1;
            bitsLeft = 8;
        }

        if (bitsLeft <= 0)
            throw new AssertionError();

        bitsLeft--;
        return (currentByte >>> bitsLeft) & 1;

    }


    private void write(char symbol) {
        stringBuilder.append(symbol);
    }


    public String getContent() {
        return stringBuilder.toString();
    }
}
