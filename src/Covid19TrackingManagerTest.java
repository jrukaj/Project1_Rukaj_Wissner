import java.io.File;

import student.TestCase;
import static org.junit.Assert.*; 

import java.io.*;
import java.util.*;


class Covid19TrackingManagerTest extends student.TestCase {

	private File file;
	private String expected = "Finished loading head_100_random_30.csv file\r\n" + 
			"30 records have been loaded\r\n" + 
			"2 records are printed out for the state of oregon\r\n" + 
			"date           positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   recovered   dataQualityGrade   death   \r\n" + 
			"08/18/2020     23,676      472,662     1,929          20                       0                        4,468       A+                 397     \r\n" + 
			"08/17/2020     23,451      467,766     1,913          19                       0                        4,419       A+                 388     \r\n" + 
			"There are no records from new Jersey\r\n" + 
			"There are 14 records on 08/18/2020\r\n" + 
			"state   positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   recovered   dataQualityGrade   death   \r\n" + 
			"AZ      194,920     909,077     20,878         260                      0                        28,027      A+                 4,529   \r\n" + 
			"FL      579,932     3,699,108   35,112         0                        0                        0           B                  9,893   \r\n" + 
			"GU      577         28,649      0              0                        0                        353         A                  5       \r\n" + 
			"HI      5,215       158,984     288            25                       0                        1,868       C                  40      \r\n" + 
			"KS      35,167      319,095     2,034          24                       200                      1,409       A+                 405     \r\n" + 
			"KY      40,299      686,601     4,252          0                        0                        9,233       B                  830     \r\n" + 
			"MD      101,235     1,066,070   13,698         0                        0                        6,008       A                  3,650   \r\n" + 
			"MN      66,061      937,245     5,932          0                        0                        59,568      A                  1,767   \r\n" + 
			"MO      69,417      798,064     0              116                      0                        0           A                  1,402   \r\n" + 
			"NH      7,017       179,890     712            0                        0                        6,333       B                  424     \r\n" + 
			"NC      146,779     1,804,341   0              0                        0                        127,749     A+                 2,396   \r\n" + 
			"OR      23,676      472,662     1,929          20                       0                        4,468       A+                 397     \r\n" + 
			"SD      10,443      119,570     927            0                        0                        9,126       A                  154     \r\n" + 
			"WI      71,424      1,075,397   5,380          0                        0                        57,382      A+                 1,059   \r\n" + 
			"Data Summary for 27 states:\r\n" + 
			"State   Total Case  Total Death   Total Hospitalized\r\n" + 
			"AL      109,004     1,925         12,958            \r\n" + 
			"AK      5,110       28            0                 \r\n" + 
			"AS      0           0             0                 \r\n" + 
			"AZ      194,920     4,529         20,878            \r\n" + 
			"AR      53,077      603           3,621             \r\n" + 
			"CA      628,031     11,242        0                 \r\n" + 
			"CO      53,176      1,768         6,735             \r\n" + 
			"DE      16,536      593           0                 \r\n" + 
			"DC      13,273      597           0                 \r\n" + 
			"FL      579,932     9,893         35,112            \r\n" + 
			"GA      238,861     4,727         22,133            \r\n" + 
			"GU      577         5             0                 \r\n" + 
			"HI      5,215       40            288               \r\n" + 
			"ID      27,660      269           1,110             \r\n" + 
			"KS      70,334      810           4,068             \r\n" + 
			"KY      40,299      830           4,252             \r\n" + 
			"MD      101,235     3,650         13,698            \r\n" + 
			"MN      66,061      1,767         5,932             \r\n" + 
			"MO      138,040     2,798         0                 \r\n" + 
			"NH      7,017       424           712               \r\n" + 
			"NM      23,408      714           2,965             \r\n" + 
			"NC      146,779     2,396         0                 \r\n" + 
			"ND      8,647       105           461               \r\n" + 
			"OR      47,127      785           3,842             \r\n" + 
			"PA      124,844     7,468         0                 \r\n" + 
			"SD      10,443      154           927               \r\n" + 
			"WI      71,424      1,059         5,380             \r\n" + 
			"Total Cases: 2,781,030\r\n" + 
			"Total Death: 59,179\r\n" + 
			"Total Hospitalized: 145,072\r\n" + 
			"Low quality data rejected for FL\r\n" + 
			"Low quality data rejected for WI\r\n" + 
			"Low quality data rejected for GU\r\n" + 
			"Low quality data rejected for AK\r\n" + 
			"Low quality data rejected for KY\r\n" + 
			"Low quality data rejected for HI\r\n" + 
			"Low quality data rejected for GA\r\n" + 
			"Low quality data rejected for MO\r\n" + 
			"Low quality data rejected for DE\r\n" + 
			"Low quality data rejected for OR\r\n" + 
			"Low quality data rejected for AS\r\n" + 
			"Finished loading head_200_random_70.csv file\r\n" + 
			"59 records have been loaded\r\n" + 
			"1 records are printed out for the state of new Jersey\r\n" + 
			"date           positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   recovered   dataQualityGrade   death   \r\n" + 
			"08/18/2020     188,098     2,344,647   22,259         41                       0                        33,403      A+                 15,925  \r\n" + 
			"1 records are printed out for the state of KY\r\n" + 
			"date           positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   recovered   dataQualityGrade   death   \r\n" + 
			"08/18/2020     40,299      686,601     4,252          0                        0                        9,233       B                  830     \r\n" + 
			"There are 18 records on 08/16/2020\r\n" + 
			"state   positive    negative    hospitalized   onVentilatorCurrently    onVentilatorCumulative   recovered   dataQualityGrade   death   \r\n" + 
			"AL      108,433     737,991     12,607         0                        720                      41,523      B                  1,898   \r\n" + 
			"AS      0           1,514       0              0                        0                        0           C                  0       \r\n" + 
			"AZ      193,537     898,437     20,755         267                      0                        27,582      A+                 4,506   \r\n" + 
			"DE      16,451      193,934     0              0                        0                        8,671       A+                 593     \r\n" + 
			"DC      13,220      229,632     0              9                        0                        10,493      A+                 597     \r\n" + 
			"FL      573,416     3,659,212   34,341         0                        0                        0           A                  9,587   \r\n" + 
			"GU      516         27,257      0              0                        0                        345         A                  5       \r\n" + 
			"IN      80,415      826,436     9,935          92                       0                        59,801      A+                 3,133   \r\n" + 
			"LA      137,918     1,520,169   0              189                      0                        103,512     A                  4,507   \r\n" + 
			"MD      100,212     1,042,198   13,614         0                        0                        6,004       A                  3,639   \r\n" + 
			"MA      123,200     1,326,768   12,173         28                       0                        100,486     A+                 8,829   \r\n" + 
			"MI      102,259     2,218,791   0              87                       0                        67,778      A+                 6,592   \r\n" + 
			"MO      67,475      778,143     0              121                      0                        0           A                  1,367   \r\n" + 
			"ND      8,587       171,213     459            0                        0                        7,249       C                  105     \r\n" + 
			"OH      108,287     1,721,358   12,236         161                      0                        86,926      A+                 3,826   \r\n" + 
			"SD      10,274      118,500     916            0                        0                        8,939       A                  153     \r\n" + 
			"VI      741         11,430      0              0                        0                        523         C                  9       \r\n" + 
			"WI      70,246      1,060,533   5,304          0                        0                        55,982      A+                 1,046   \r\n" + 
			"89 records have been saved in the xyz13da.csv file";
	private String[] args = new String[2];
	
	/**
	 * Sets up the test cases.
	 */
	public void setUp() {
		file = new File("input_1.txt");
		args[0] = "input_1.txt";
	}
	
	/**
	 * Tests the COVID19TrackingManager
	 */
	public void testAll() throws FileNotFoundException {
		Covid19TrackingManager.main(args);
		String output = systemOut().getHistory();
		assertEquals(output, expected);
	}
}
