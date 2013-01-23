import fshdump.*;
import fshdump.exc.ParseError;

import java.io.*;
import static java.lang.System.*;

public class ParserTest {

    public static void main(String[] args) throws IOException, ParseError {
        DumpResult dumpResult = new DumpResult();
        DumpParser dumpParser = new DumpParser(new DumpOptions(), true);
        StrictDumpWriter dumpWriter = new StrictDumpWriter();

        // The data contains unnamed elements. The output is expected to differ
        // from the input.
        String inputData =
                "Hello<\n" +
                "   Fo\\<ba\\>bo;\n" +
                "   Baz;\n" +
                "   Bat;\n" +
                "   Bo\\;oz<\n" +
                "       Fuck;\n" +
                "       yeAh!;\n" +
                "   >\n" +
                "   Baha;\n" +
                "   ;\n" +
                "   Cool<\n" +
                "       Stuff;\n" +
                "       Sub<\n" +
                "           Buh;\n" +
                "       >\n" +
                "   >\n" +
                ">  ";
        String outputData = null;

        StringWriter outputWriter = new StringWriter();
        Writer outWriter = new PrintWriter(out);

        // Parse the input dump.
        DataFeedElement[] e = dumpParser.parseDump(new StringReader(inputData), dumpResult);
        out.printf("Parsed %d top-level element(s).\n", e.length);
        if (e.length != 1) {
            out.printf("Error, input actually has 1 top-level element.\n");
            return;
        }

        dumpWriter.writeFeed(new DumpOptions(), dumpResult, outputWriter, e[0]);
        outputData = outputWriter.getBuffer().toString();
        out.println("Input Data:\n" + inputData);

        e[0].formatNice(outWriter, 0);
        out.println("Resulting (parsed) tree:");
        outWriter.flush();

        out.println("Re-dumped Data:\n   " + outputData);
    }

}
