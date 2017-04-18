/**
 * Created by zacharymikel on 4/12/17.
 */
public class EqualityCheck {

    public static String statusMessage;

    public static boolean check(String s, String x) {

        char[] sArray = s.toCharArray();
        char[] xArray = x.toCharArray();
        for(int i = 0; i < s.length(); i++) {
            if(sArray[i] != xArray[i]) {
                statusMessage = "Fail! Doesn't match at index " + i;
                return false;
            }
        }

        statusMessage = "Success! Files match.";
        return true;
    }

    public static String getStatusMessage() { return statusMessage; }

}
