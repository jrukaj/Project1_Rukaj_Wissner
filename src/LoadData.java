import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.*;

import com.opencsv.*;
import com.opencsv.exceptions.CsvException;

/**
 * Class that contains the load function and helpers
 * for the load function
 * 
 * @author Drew Wissler 906106464
 * @author Jonathan Rukaj
 * @version 29.09.2020
 *
 */
public class LoadData {
	//The BST that stores all the states
	private BST<State> bst;
	//The array that stores state abbreviations
	private String[] state_abvs;
	//The total number of states in the US
	private static final int NUMBER_OF_STATES = 59;
	//The total number of records
	private int totalRecords = 0;
	//Array of the state names
	String[] stateNames = new String[NUMBER_OF_STATES];


	public LoadData() { 
		bst = new BST<State>();
		state_abvs = new String[NUMBER_OF_STATES];
	}
	/**
	 * Compares the data quality between two states
	 * 
	 * @param state1 A state being compared
	 * @param state2 A state being compared
	 * @return true if first state has a better grade
	 */
	private boolean isGradeBetter(State state1, State state2) {
		int state1Int = 0;
		int state2Int = 0;
		switch (state1.getGrade()) {
		case "A+": 
			state1Int = 9;
			break;
		case "A":
			state1Int = 8;
			break;
		case "B+":
			state1Int = 7;
			break;
		case "B": 
			state1Int = 6;
			break;
		case "C+":
			state1Int = 5;
			break;
		case "C":
			state1Int = 4;
			break;
		case "D+":
			state1Int = 3;
			break;
		case "D":
			state1Int = 2;
			break;
		case "F":
			state1Int = 1;
			break;
		default:
			state1Int = 0;
			break;
		}
		switch (state2.getGrade()) {
		case "A+": 
			state2Int = 9;
			break;
		case "A":
			state2Int = 8;
			break;
		case "B+":
			state2Int = 7;
			break;
		case "B": 
			state2Int = 6;
			break;
		case "C+":
			state2Int = 5;
			break;
		case "C":
			state2Int = 4;
			break;
		case "D+":
			state2Int = 3;
			break;
		case "D":
			state2Int = 2;
			break;
		case "F":
			state2Int = 1;
			break;
		default:
			state2Int = 0;
			break;
		}

		return (state1Int > state2Int);
	}

	/**
	 * Creates an array of the states and territories used to compare with
	 * the states in the data file
	 * 
	 * @param textFile - Text file with state abbreviations
	 * @throws FileNotFoundException if the file is not found
	 */
	private void makeStateArray(String textFile) throws FileNotFoundException {
		File file = new File(textFile);
		Scanner scan = new Scanner(file);
		int i = 0;
		while (scan.hasNext()) {           
			state_abvs[i] = scan.next();
			i++;
		}
		scan.close();
	}

	/**
	 * Compares the values of two states to see if anything needs to be updated
	 * 
	 * @param better - The state with the better quality grade
	 * @param worse - The state with the worse quality grade
	 * @return true if any values were changed
	 */
	private boolean compareStates(State better, State worse) {
		boolean isChanged = false;    

		if (better.getPositives().isEmpty() && !worse.getPositives().isEmpty()) {
			better.setPositives(worse.getPositives());
			isChanged = true;
		}
		if (better.getNegatives().isEmpty() && !worse.getNegatives().isEmpty()) {
			better.setNegatives(worse.getNegatives());
			isChanged = true;
		}
		if (better.getHospitalized().isEmpty() && !worse.getHospitalized().isEmpty()) {
			better.setHospitalized(worse.getHospitalized());
			isChanged = true;
		}
		if (better.getOnVentilCurr().isEmpty() && !worse.getOnVentilCurr().isEmpty()) {
			better.setOnVentilCurr(worse.getOnVentilCurr());
			isChanged = true;
		}
		if (better.getOnVentilCum().isEmpty() && !worse.getOnVentilCum().isEmpty()) {
			better.setOnVentilCum(worse.getOnVentilCum());
			isChanged = true;
		}
		if (better.getRecovered().isEmpty() && !worse.getRecovered().isEmpty()) {
			better.setRecovered(worse.getRecovered());
			isChanged = true;
		}
		if (better.getDeath().isEmpty() && !worse.getDeath().isEmpty()) {
			better.setDeath(worse.getDeath());
			isChanged = true;
		}

		return isChanged;
	}

	/**
	 * Changes the date to a readable form
	 * @param str - The date from the data
	 * @return The readable date
	 */
	private String changeDate(String str) {
		String day, month, year;
		year = str.substring(0,4);
		month = str.substring(4,6);
		day = str.substring(6,8);

		return month + "/" + day + "/" + year;

	}

	/**
	 * Method that adds commas to numbers for printing
	 *
	 * @param state string that is getting commas added to it
	 * @return string with commas
	 */
	private String changeNumbers(String state) {
		String str = state;

		if (state.length() > 3) {
			StringBuilder sb = new StringBuilder();
			int len2 = state.length() % 3;

			sb.append(state.substring(0, len2));

			while (len2 != state.length()) {
				if (len2 != 0) {
					sb.append(",");
				}
				sb.append(state.substring(len2, len2 + 3));       
				len2 += 3;
			}
			str = sb.toString();
		}
		return str;
	}

	/**
	 * Turns the positives, deaths and hospitalized people into integers for the 
	 * summarydata function
	 * 
	 * @param state The state 
	 * @return an array of integers with
	 *      0 - positives
	 *      1 - deaths
	 *      2 - hospitalized
	 */
	private int[] dataHelper(State state) {
		int[] data = new int[3];
		if (state.getPositives().isEmpty()) {
			data[0] = 0;
		}
		else {
			String str = state.getPositives().substring(0, state.getPositives().length() - 2);
			data[0] = Integer.valueOf(str);
		}
		if (state.getDeath().isEmpty()) {
			data[1] = 0;
		}
		else {
			String str = state.getDeath().substring(0, state.getDeath().length() - 2);
			data[1] = Integer.valueOf(str);
		}
		if (state.getHospitalized().isEmpty()) {
			data[2] = 0;
		}
		else {
			String str = state.getHospitalized().substring(0, state.getHospitalized().length() - 2);
			data[2] = Integer.valueOf(str);
		}
		return data;
	}

	/**
	 * Finds the most recent date in the data for the search call with no arguments
	 * 
	 * @return the most recent date
	 */
	private String recentDate() {
		java.util.Iterator<State> iter = bst.iterator();
		String highestDate = iter.next().getDate();
		while (iter.hasNext()) {
			String newDate = iter.next().getDate();
			if (Integer.valueOf(newDate) > Integer.valueOf(highestDate)) {
				highestDate = newDate;
			}
		}
		return highestDate;
	}

	/**
	 * Parses through the data and stores each line into a "state" which
	 * is then stored in a BST based on the name of the state. Data is updated 
	 * or not used based on certain qualifications
	 * 
	 * @param textFile - The name of the file
	 * @throws FileNotFoundException if there is on file found
	 */
	public void load(String textFile) throws FileNotFoundException {      
		File fileName = new File(textFile);
		makeStateArray("States_Abbreviations.txt");

		File f = new File("State_Names.txt");
        
        Scanner scan = new Scanner(f);
        for (int i = 0; i < NUMBER_OF_STATES; i++) {
            stateNames[i] = scan.nextLine();
        }
        scan.close();
		if (!fileName.exists()) {
			System.out.println("File " + textFile + " was not found");
			return;          
		}

		Scanner scanner = new Scanner(fileName);

		scanner.nextLine();

		while(scanner.hasNext()) {
			String[] dataArr = scanner.next().split(",");
			State state = new State();
			state.stateInfo(dataArr);          

			//boolean to see if the data is valid
			boolean validData = false;
			//Checks to see if state, quality grade, or date exists
			if (!state.getState().isEmpty() && !state.getGrade().isEmpty() &&
					!state.getDate().isEmpty() && state.getDate().length() == 8) {
				validData = true;
			}

			//boolean to see if the name is valid
			boolean validName = false;

			int i = 0;
			//Checks to see if any states have invalid name
			while (i < NUMBER_OF_STATES && validData) {
				if (state_abvs[i].equals(state.getState())) {
					validName = true;
					break;
				}
				i++;
			}


			//boolean that is true if state has the same abbreviation and date as another 
			//state in the bst
			boolean notSameName = true;

			java.util.Iterator<State> iter = bst.iterator();

			//checks to see if other state in bst has same abbreviation and date and updates
			//the data if needed
			while (iter.hasNext()) {
				State iterState = iter.next();
				if (iterState.getState().equals(state.getState()) &&
						iterState.getDate().equals(state.getDate())) {
					notSameName = false;
					if (isGradeBetter(state, iterState)) {
						System.out.println("Data has been updated for " + state.getState() +
								" " + changeDate(state.getDate()));
						state.insert(bst);
						bst.remove(iterState);
						totalRecords++;
						if (compareStates(state, iterState)) {
							//
						}
					}
					else {
						if (compareStates(iterState, state)) {
							totalRecords++;
							System.out.println("Data has been updated for the missing " +
									"data in " + state.getState());
						}
						else {
							System.out.println("Low quality data rejected for " + state.getState());
						}
					}

				}
			}

			//print statements 
			if (validName && validData && notSameName) {
				totalRecords++;
				state.insert(bst);
			}
			else if (!validData) {
				System.out.println("Discard invalid record");
			}
			else if (!validName) {
				System.out.println("State of " + state.getState() + " does "
						+ "not exist!");
			}            
		}
		scanner.close();

		System.out.println("Finished loading " + fileName + " file");
		System.out.println(totalRecords + " records have been loaded");          
	}
	//IN FILE EXECUTOR CLASS THERE NEEDS TO BE A METHOD THAT CHANGES THE STATE TO THE ABBREVIATION

	

	/**
	 * Creates a summary of all the data for each state. If a state has multiple dates, their values are combined
	 * into one 
	 */
	public void summarydata() {
		java.util.Iterator<State> iter = bst.iterator();

		State[] stateArr = new State[totalRecords];

		int totalStates = 1, totalCase = 0, totalDeath = 0, totalHospitalized = 0;
		int count = 1;

		stateArr[0] = iter.next();

		//Counts number of individual states (ignores duplicates)
		while (iter.hasNext()) {
			State state = iter.next();
			if (!state.getState().equals(stateArr[count - 1].getState())) {
				totalStates++;
			}
			stateArr[count] = state;
			count++;       
		}

		System.out.println("Data Summary for " + totalStates + " states");
		System.out.println("State\tTotal Case\tTotal Death\tTotal Hospitalized");

		//Print each line of data one by one 
		for (int i = 0; i < totalRecords; i++) {
			int[] data2 = new int[3];
			boolean isSame = true;
			boolean oneState = true;
			//Checks to see if two states are the same. If so, the values are combined
			while (i + 1 != totalRecords && isSame) {
				if (stateArr[i].getState().equals(stateArr[i + 1].getState())) {
					int[] data1 = dataHelper(stateArr[i]);
					data2 = dataHelper(stateArr[i + 1]);
					data2[0] += data1[0];
					data2[1] += data1[1];
					data2[2] += data1[2];
					oneState = false;
					i++;
				}
				else {
					isSame = false;
				}      
			}
			if (oneState) {
				data2 = dataHelper(stateArr[i]);
			}

			//Adds each value to the total amount
			totalCase += data2[0];
			totalDeath += data2[1];
			totalHospitalized += data2[2];

			System.out.println(stateArr[i].getState() + "\t" + changeNumbers(data2[0] + "") + "\t\t" + changeNumbers(data2[1] + "") + "\t\t" +
					changeNumbers(data2[2] + ""));
		}
		System.out.println("Total Cases: " + changeNumbers(totalCase + ""));
		System.out.println("Total Death: " + changeNumbers(totalDeath + ""));
		System.out.println("Total Hospitalized: " + changeNumbers(totalHospitalized + ""));
	}

	public String dumpdata(String filename) {
		int count = 0;
		try {
			// Create file, and write using FileWriter and
			// CSVWriter
			File file = new File(filename);
			FileWriter outputfile = new FileWriter(file);

			CSVWriter writer = new CSVWriter(outputfile);

			// Create and write header to CSV
			String[] header = {"date", "state", "positive", "negative",
					"hospitalized", "onVentilatorCurrently", "onVentilatorCumulative",
					"recovered", "dataQualityGrade", "death"};
			writer.writeNext(header);

			java.util.Iterator<State> iter = bst.iterator();
			while (iter.hasNext()) {
				// Create new String array to hold data
				String data[] = new String[10];
				State state = iter.next();

				// Write state data to CSV
				data[0] = state.getDate();
				data[1] = state.getState();
				data[2] = state.getPositives();
				data[3] = state.getNegatives();
				data[4] = state.getHospitalized();
				data[5] = state.getOnVentilCurr();
				data[6] = state.getOnVentilCum();
				data[7] = state.getRecovered();
				data[8] = state.getGrade();
				data[9] = state.getDeath();
				writer.writeNext(data);
				count++;

			} // End of while

			System.out.println(Integer.toString(count) + " records have been saved in the " + filename + " file.");

		} // End of try
		catch (IOException e) {
			e.printStackTrace();
		} // End of catch
		return filename;
	}

	public void search(String stateName, int numRecords) throws IOException, CsvException {
		// Create iterator and files to write data to
		java.util.Iterator<State> iter = bst.iterator();
		
		int count = 0;
		
	
		String str = changeAbbrev(stateName);    
		State[] searchStates = new State[numRecords];
		
		
		
		while (iter.hasNext() && count < numRecords) {
			State temp = iter.next();
			if (temp.getState().equals(str)) {
				searchStates[count] = temp;
				count++;
			}
			
		}
		
		
		if (count == 0) {
			System.out.println("There are no records from " + stateName);
		}
		else {
		    int counter = 0;
		    System.out.println(count + " records are printed out for the state of " + stateName);
		    System.out.println("date           positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   "
                + "recovered   dataQualityGrade   death   ");
		    while (counter < count) {
	            System.out.println(changeDate(searchStates[counter].getDate()) + "\t" + changeNumbers(eliminateDecimal(searchStates[counter].getPositives())) + "\t\t" +
	                    changeNumbers(eliminateDecimal(searchStates[counter].getNegatives())) + "\t\t" + changeNumbers(eliminateDecimal(searchStates[counter].getHospitalized())) + "\t\t" + 
	                    changeNumbers(eliminateDecimal(searchStates[counter].getOnVentilCurr())) + "\t\t\t" + changeNumbers(eliminateDecimal(searchStates[counter].getOnVentilCum())) + "\t\t\t" + 
	                    changeNumbers(eliminateDecimal(searchStates[counter].getRecovered())) + "\t\t" + searchStates[counter].getGrade() + "\t\t\t" + 
	                    changeNumbers(eliminateDecimal(searchStates[counter].getDeath())));
	            counter++;
	        }
		}

	}

	/**
	 * Searches the BST for all of the data with the most recent date and prints it out
	 */
	public void search() {
		java.util.Iterator<State> iter = bst.iterator();
		String date = recentDate();

		if (!iter.hasNext()) {
			System.out.println("No available data");
		}

		int i = 0;
		State[] stateArr1 = new State[totalRecords];
		while (iter.hasNext()) {
			State state = iter.next();
			if (state.getDate().equals(date)) {
				stateArr1[i] = state;
				i++;
			}
		}

		int counter = 0;
		System.out.println("There are " + i + " records on " + changeDate(date));
		System.out.println("state\tpositive\tnegative\thospitalized\tonVentilatorCurrently\tonVentilatorCumulative\t"
				+ "recovered\tdataQualityGrade\tdeath");
		while (counter < i) {
			System.out.println(stateArr1[counter].getState() + "\t" + changeNumbers(eliminateDecimal(stateArr1[counter].getPositives())) + "\t\t" +
					changeNumbers(eliminateDecimal(stateArr1[counter].getNegatives())) + "\t\t" + changeNumbers(eliminateDecimal(stateArr1[counter].getHospitalized())) + "\t\t" + 
					changeNumbers(eliminateDecimal(stateArr1[counter].getOnVentilCurr())) + "\t\t\t" + changeNumbers(eliminateDecimal(stateArr1[counter].getOnVentilCum())) + "\t\t\t" + 
					changeNumbers(eliminateDecimal(stateArr1[counter].getRecovered())) + "\t\t" + stateArr1[counter].getGrade() + "\t\t\t" + 
					changeNumbers(eliminateDecimal(stateArr1[counter].getDeath())));
			counter++;
		}
	}

	/**
	 * Searches the BST for the data that matches the date parameter
	 * 
	 * @param str - the date thats beign compared to
	 */
	public void search(String str) {
		String date = str.substring(6) + str.substring(0,2) +  str.substring(3,5);
		java.util.Iterator<State> iter = bst.iterator();


		int i = 0;
		State[] stateArr1 = new State[totalRecords];
		while (iter.hasNext()) {
			State state = iter.next();
			if (state.getDate().equals(date)) {
				stateArr1[i] = state;
				i++;
			}
		}

		if (stateArr1[0] == null) {
			System.out.println("There are no records on " + str);
		}
		else {
			int counter = 0;
			System.out.println("There are " + i + " records on " + changeDate(date));
			System.out.println("state\tpositive\tnegative\thospitalized\tonVentilatorCurrently\tonVentilatorCumulative\t"
					+ "recovered\tdataQualityGrade\tdeath");
			while (counter < i) {
				System.out.println(stateArr1[counter].getState() + "\t" + changeNumbers(eliminateDecimal(stateArr1[counter].getPositives())) + "\t\t" +
						changeNumbers(eliminateDecimal(stateArr1[counter].getNegatives())) + "\t\t" + changeNumbers(eliminateDecimal(stateArr1[counter].getHospitalized())) + "\t\t" + 
						changeNumbers(eliminateDecimal(stateArr1[counter].getOnVentilCurr())) + "\t\t\t" + changeNumbers(eliminateDecimal(stateArr1[counter].getOnVentilCum())) + "\t\t\t" + 
						changeNumbers(eliminateDecimal(stateArr1[counter].getRecovered())) + "\t\t" + stateArr1[counter].getGrade() + "\t\t\t" + 
						changeNumbers(eliminateDecimal(stateArr1[counter].getDeath())));
				counter++;
			}
		}       
	}


	/**
	 * Gets rid of the decimal in the strings of number
	 * ex. eliminateDecimal("100.0") --> "100"
	 * 
	 * @param str the string being changed
	 * @return the changed number without the decimal
	 */
	private String eliminateDecimal(String str) {
		if (!str.isEmpty()) {
			String newStr = str.substring(0, str.length() - 2);
			return newStr;
		}
		else {
			return str;
		}
	}
	
	/**
	 * Sets all values in array to 0
	 * @param arr the array
	 * @return array with 0s in every slot
	 */
	private String[] setZero(String[] arr) {
		for (int i = 0; i < arr.length; i++) {
			arr[i] = "0";
		}
		return arr;
	}
	
	private String changeAbbrev(String state) {
	    String temp = new String();
	    int i = 0;
	    while (i < NUMBER_OF_STATES) {
	        if (state.equals(stateNames[i])) {
	            temp = state_abvs[i];
	            break;
	        }
	        i++;
	    }
	    return temp;
	}


}