/**
 * This main class implements a command-line utility for writing
 * an fshdump. Command-line options are:
 *
 * [-s]:            Show statistics (number of files/directories, bytes
 *                  written, execution time).
 * [-f FORMAT]:     Rendering format. strict|readable
 * [-d DIRNAME]:    Name of the directory to create the dump from.
 *                  Defaults to the current working directory.
 * [-o FILENAME]:   Output filename or stdout|null.
 * [-n LINEENDING]: Line ending for readable formatting. cr|lf|crlf
 */

import java.io.File;

public class FSHDumpWriterMain {

    public static void main(String[] args) {
        // TODO: implement
        System.exit(-1);
    }

    private static String escape(String str) {
        str = str.replace(",", "\\,");
        str = str.replace(")", "\\)");
        str = str.replace("(", "\\(");
        return str;
    }

    public static String scan_directory(String path) {
        String string = "";
        boolean putComma = false;

        int fileCount = 0;
        int dirCount = 0;

        File file = new File(path);

        if (file.exists() && file.isDirectory()) {
            File[] subFiles = file.listFiles();
            //Add all necessary strings to one big string
            for (int i = 0; i < subFiles.length; i++) {
                if (subFiles.length >= 1) {
                    if (subFiles[i].isFile()) {
                        //Add a comma if necessary
                        if (putComma) {
                            string += ",";
                        } else {
                            putComma = true;
                        }

                        //Add file name (and increase file count)
                        string += escape(subFiles[i].getName());
                        fileCount++;
                    }
                    else if (subFiles[i].isDirectory()) {
                        //Add a comma if necessary
                        if (putComma) {
                            string += ",";
                        } else {
                            putComma = true;
                        }

                        //Add directory name (and increase directory count)
                        string += escape(subFiles[i].getName());
                        string += "(";
                        string += escape(scan_directory(subFiles[i].getAbsolutePath()));
                        string += ")";
                        dirCount++;
                    }	
                } else {
                    //There are no files/directories in this directory
                }
            }
        } else {
            System.err.println("Given path is not a directory or does not exist!");
        }
        return string;
    }

}
