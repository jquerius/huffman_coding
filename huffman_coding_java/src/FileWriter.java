import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileWriter {

    private OutputStream output;
    private int currentByte;
    private int numBitsFilled;



    /**
     * Creates a new File Writer object, that holds an output stream for writing
     * to a new encoded file.
     *
     * The file writer can take an int, and write it as a bit to the new encoded file.
     * @param out
     */
    public FileWriter(File out) {
        try {
            output = new BufferedOutputStream(new FileOutputStream(out));
            currentByte = 0;
            numBitsFilled = 0;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }



    /**
     * Take an integer representing one bit and write it to the file
     * by shifting the current byte over to the left 1 bit and putting
     * the bit into the empty slot.
     *
     * @param b
     * @throws IOException
     */
    public void write(int b) throws IOException {
        if (b != 0 && b != 1)
            throw new IllegalArgumentException("Argument must be 0 or 1");

        // build up a byte
        // shift current byte left 1 space, and ORR the result with B

        // eg, currentByte = 101
        // int = 1
        // currentByte << 1 == 1010
        // 1010 | b == 1011
        currentByte = (currentByte << 1) | b;

        numBitsFilled++;
        if (numBitsFilled == 8) {
            output.write(currentByte);
            currentByte = 0;
            numBitsFilled = 0;
        }

    }


    /**
     * Shuts down the output stream.
     * @throws IOException
     */
    public void close() throws IOException {
        while (numBitsFilled != 0)
            write(0);
        output.close();
    }


    /**
     * Scan the file into a string and return it
     * @param file
     * @return
     */
    public static String readFile(File file) {

        try {

            byte[] encoded = Files.readAllBytes(Paths.get(file.getPath()));
            return new String(encoded, Charset.defaultCharset());

            /*// open the file
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(file.getAbsolutePath()), "UTF8"));
            StringBuilder stringBuilder = new StringBuilder();

            // read each line from the file and
            String s;
            while((s = bufferedReader.readLine()) != null) {
                stringBuilder.append(s);
            }

            return stringBuilder.toString();*/

        } catch(IOException e) {
            System.err.println("Problem parsing the file " + file.getName());
        }

        return "";
    }

}
