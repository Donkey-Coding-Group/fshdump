import fshdump.util.formatting.*;

/**
 * Created with IntelliJ IDEA.
 * User: niklas
 * Date: 19.01.13
 * Time: 00:51
 * To change this template use File | Settings | File Templates.
 */
public class Test {

    public static void main(String[] args) {
        Column collection = new Column();
        collection.separator = new SimpleTextSeparator("-", "|");
        collection.add("Foo Bar Tag Here!", 10);
        collection.add("And some other shit there...", 15);

        CompiledFormatter formatter = collection.compileFormatter();
        int lines = formatter.getLineCount();
        for (int i=0; i < lines; i++) {
            System.out.println(formatter.getLine(i));
        }
    }

}
