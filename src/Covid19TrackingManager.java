
/**
 * Driver class for Covid19TrackingManager. Calls other classes to
 * perform operations.
 * @author Jonathan Rukaj
 * @author Drew Wissler
 * @version 1.0
 * @date 9/26/20
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import com.opencsv.exceptions.CsvException;

public class Covid19TrackingManager {
    public static void main(String[] args) throws IOException, CsvException {
    	// Get filename from command line
        String file = args[0];
        // Create new processor with file as parameter, get 
        // list of commands
        CommandProcessor processor = new CommandProcessor(file);
        ArrayList<ArrayList<String>> commands = processor.parseCmd();
        // Create new FileExecutor and execute commands
        FileExecutor executor = new FileExecutor();
        executor.execute(commands);
    }
}