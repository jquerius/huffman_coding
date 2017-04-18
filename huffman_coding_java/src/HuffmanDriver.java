
import java.io.*;


public class HuffmanDriver {

    private static boolean debug;

    public static void main(String[] args) {

        File inputFile = new File(args[0]);
        File outputFile = new File(args[1]);
        debug = Boolean.valueOf(args[2]);

        TimeUtil timeUtil = new TimeUtil();

        try {

            logInfo("==============================================");
            logInfo("JAVA HUFFMAN ENCODER");
            logInfo("==============================================\n\n");

            logInfo("Input file: " + inputFile);
            logInfo("Output file: " + outputFile);

            logInfo("\n\nStarting encoder...");
            logDebug("----------------------------------------------");
            timeUtil.start();
            Encoder encoder = new Encoder(inputFile, outputFile, debug);
            File encoded = encoder.encodeFile();
            logDebug("----------------------------------------------");
            logInfo("Encoding finished.\n\n");

            logInfo("Starting decoder...");
            logDebug("----------------------------------------------");
            Decoder decoder = new Decoder(encoder.getRoot(), new BufferedInputStream(new FileInputStream(encoded)));
            logDebug(decoder.decodeFile());
            logDebug("----------------------------------------------");
            logInfo("Decoding finished.\n\n");

            logInfo("Total time elapsed: " + timeUtil.end());
            logInfo("input file size: " + inputFile.length() + " bytes");
            logInfo("output file size: " + encoded.length() + " bytes");

            EqualityCheck.check(encoder.getContent(), decoder.getContent());
            logInfo(EqualityCheck.getStatusMessage());



        } catch(IOException e) {
            logDebug("Error processing files.");
        }

    }

    private static void logInfo(String s) {
        System.out.println(s);
    }

    private static void logDebug(String s) {
        if(debug)
            System.out.println(s);
    }

}
