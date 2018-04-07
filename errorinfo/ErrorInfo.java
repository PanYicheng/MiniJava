/**
 * @Author:
 * @Description:
 * @Data: Created in 13:09 06/04/18
 * @Modified By:
 */
package errorinfo;
import java.util.ArrayList;

public class ErrorInfo {
    private static final ArrayList<Integer> rows = new ArrayList<>();
    private static final ArrayList<Integer> cols = new ArrayList<>();
    private static final ArrayList<String> msgs = new ArrayList<>();
    private static int size = 0;
    public ErrorInfo(){
    }

    public static int getSize(){
        return size;
    }

    public static void printAll(){
        for(Integer i=0;i<size;i++)
            System.out.println("[" + i.toString() + "] " +
                    " " + msgs.get(i) +
                    " row:" + rows.get(i).toString() +
                    " col:" + cols.get(i).toString());
    }

    public static void addInfo(int row,int col,String msg) {
        rows.add(row);
        cols.add(col);
        msgs.add(msg);
        size++;
    }
}
