package org.apache.hadoop.fs.parser;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;

/**
 * Parses content generated by micro benchmarks
 *
 * Note: This is not parameterized, and code needs to be changed to point to the correct input
 * paths and prefixes.
 *
 */
public class CustomParser2 {



  void parseRuns() throws IOException {

    // TODO: Change prefixes and RunInfo to point to the correct files.
    // TODO: Eventually accept these as parameters.
    String []prefixes = {"logs_getStatus_0", "logs_getStatus_1", "logs_listFiles_2", "logs_listLocatedStatus_1", "logs_readFooter", "logs_readTest1"};
    String []dirs = {"dir1", "dir2"};

    // Make sure to fix parameters before running.
    System.err.println("Make sure to fix the above parameters, and commend out this section. Exiting.");
    System.exit(-1);

    for (String prefix : prefixes) {
      for (String d : dirs) {
        File dir = new File(d);
        String []fileList = dir.list(new FilenameFilter() {
          @Override
          public boolean accept(File dir, String name) {
            return name.startsWith(prefix);
          }
        });

        Arrays.sort(fileList);

        for (String file : fileList) {
          SingleFileParser parser = new SingleFileParser(new File(dir, file));
          parser.parse();
          StringBuilder sb = new StringBuilder();
          sb.append(file + ",");
          ParserUtils.computeDataReadPerMachine(parser.getPrasedData(), sb);
          ParserUtils.computeTimeTakenPerNode(parser.getPrasedData(), sb);
          for (String operation : parser.getOperations()) {
            ParserUtils.computeTimeTakenPerNode(operation, parser.getPrasedData(), sb);
          }
          // filesPerNode
          System.out.println(sb.toString());
        }
      }
    }
  }


  public static void main(String[] args) throws IOException {
    CustomParser2 parser = new CustomParser2();
    parser.parseRuns();
  }
}
