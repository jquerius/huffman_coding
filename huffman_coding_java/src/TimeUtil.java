/**
 * Created by zacharymikel on 4/2/17.
 */
public class TimeUtil {

    private Long start;
    public void start() {
        start = System.currentTimeMillis();
    }
    public String end() {
        String result = String.valueOf((System.currentTimeMillis() - start) + " ms");
        start = null;
        return result;
    }
    public boolean active() {
        return start != null;
    }

}
