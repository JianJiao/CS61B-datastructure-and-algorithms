/* OpenCommercial.java */

import java.net.*;
import java.io.*;

/**  A class that provides a main function to read five lines of a commercial
 *   Web page and print them in reverse order, given the name of a company.
 */

class OpenCommercial {

  /** Prompts the user for the name X of a company (a single string), opens
   *  the Web site corresponding to www.X.com, and prints the first five lines
   *  of the Web page in reverse order.
   *  @param arg is not used.
   *  @exception Exception thrown if there are any problems parsing the 
   *             user's input or opening the connection.
   */
  public static void main(String[] arg) throws Exception {

    BufferedReader keyboard;
    String inputLine;

    keyboard = new BufferedReader(new InputStreamReader(System.in));

    System.out.print("Please enter the name of a company (without spaces): ");
    System.out.flush();        /* Make sure the line is printed immediately. */
    inputLine = keyboard.readLine();

    /* Replace this comment with your solution.  */
    URL url;

    try{
   	String a = "http://www." + inputLine + ".com";
	url = new URL(a);
	URLConnection conn = url.openConnection();
	
	BufferedReader br = new BufferedReader(
				new InputStreamReader(conn.getInputStream()));
	String line;
	int i =0;
	String[] lines = new String[5];
	while((line = br.readLine())!=null&&i<5){
		lines[i] = line;
		i++;
	}
	for(int j=4;j>=0;j--){
		System.out.println(lines[j]); }
    }catch(Exception e){
   	e.printStackTrace(); 
    }

  }
}
