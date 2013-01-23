import fshdump.*;

import java.io.*;
import static java.lang.System.*;

public class ParserTest {

    public static void main(String[] args) throws IOException, DamagedFileException {
        DumpResult dumpResult = new DumpResult();
        DumpOptions dumpOptions = new DumpOptions();
        StrictDumpParser dumpParser = new StrictDumpParser();
        StrictDumpWriter dumpWriter = new StrictDumpWriter();

        String inputData = "Hello<Fo\\<ba\\>bo;Baz;Bat;Bo\\;oz<Fuck;yeAh!;>>";
        String outputData = null;

        StringWriter outputWriter = new StringWriter();
        Writer outWriter = new PrintWriter(out);

        // Parse the input dump.
        DataFeedElement[] e = dumpParser.parseDump(new StringReader(inputData), dumpOptions);
        out.printf("Parsed %d top-level element(s).\n", e.length);
        if (e.length == 0) {
            out.printf("Error, input actually has 1 top-level element.\n");
            return;
        }
        e[0].formatNice(outWriter, 0);
        out.println("Parsed Tree:");
        outWriter.flush();

        out.println("Writing new dump from parsed elements..");
        dumpWriter.writeFeed(dumpOptions, dumpResult, outputWriter, e[0]);
        outputData = outputWriter.getBuffer().toString();
        out.println("Comparing..");
        if (outputData.equals(inputData)) {
            out.println("Input and Output data are equal.");
        }
        else {
            out.println("ERROR: Input and Output data are not equal!!");
            out.println(inputData);
            out.println(outputData);
        }

    }

}
