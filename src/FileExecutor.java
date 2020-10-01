import java.util.ArrayList;
/**
 * Class that creates Section objects and executes the commands.
 * @author Jonathan Rukaj
 * @author Drew Wissler
 * @version 1
 */
public class FileExecutor {

	public LoadData loaddata;


	/**
	 * Constructor for file executor
	 */
	public FileExecutor() {
		loaddata = new LoadData();
	}

	/**
	 * Takes in list of list of strings, executing the commands
	 * @param list
	 *            the list of commands
	 */
	public void execute(ArrayList<ArrayList<String>> list) {
		for (ArrayList<String> cmd: list) {
			
			// Switch the command, doing necessary operations
			// depending on the command
			switch (cmd.get(0).toLowerCase()) {

			case "load":
				String fileName = cmd.get(1);
				try {
					loaddata.load(fileName);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				break;
				
			case "summarydata":
				loaddata.summarydata();
				break;

			case "search":
				loaddata.search(cmd);
				break;

			case "dumpdata":
				String filename = cmd.get(1);
				loaddata.dumpdata(filename);
				break;			

			} // end of switch
		} // End of for-loop
	} // End of execute
}
