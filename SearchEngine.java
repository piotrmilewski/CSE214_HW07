/**
 * The <code>WebGraph</code> class initializes a <code>WebGraph</code> from the appropriate text files and allows
 * the user to search for keywords in the graph.
 *
 *
 * @author Piotr Milewski
 *    email: piotr.milewski@stonybrook.edu
 *    Stony Brook ID: 112291666
 **/

import java.io.*;
import java.util.LinkedList;
import java.util.Comparator;
import java.util.Collections;

public class SearchEngine{

    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE = "links.txt";
    private static WebGraph web;
    private static WebPage compile; //ensures WebPage compiles
    private static IndexComparator compileI; //ensures IndexComparator compiles
    private static URLComparator compileU; //ensures URLComparator compiles
    private static RankComparator compileR; //ensures RankComparator compiles
    private static KeywordComparator compileK; //ensures KeywordComparator compiles

    public static void main(String[] args) throws IOException{

	InputStreamReader inStream = new InputStreamReader(System.in);
	BufferedReader stdin = new BufferedReader(inStream);
	
	try{
	    System.out.println("\nLoading WebGraph data...");
	    web = WebGraph.buildFromFiles(PAGES_FILE, LINKS_FILE);
	    System.out.println("Success!");
	}
	catch (IOException ioe){
	    System.out.println("Failure!");
	    System.out.println("Missing necessary files to load WebGraph data.");
	    return;
	}

	boolean run = true;
	
	while (run){
	    
	    String output = "\nMenu:\n";
	    output += "    (AP) - Add a new page to the graph.\n";
	    output += "    (RP) - Remove a page from the graph.\n";
	    output += "    (AL) - Add a link between pages in the graph.\n";
	    output += "    (RL) - Remove a link between pages in the graph.\n";
	    output += "    (P)  - Print the graph.\n";
	    output += "    (S)  - Search for pages with a keyword.\n";
	    output += "    (Q)  - Quit.\n\n";
	    output += "Please select an option: ";
	    System.out.print(output);

	    String sourceUrl, destinationUrl;
	    String input = stdin.readLine();
	    String[] newInput = input.split(" ");
	    String[] parsedInput;
	    LinkedList<String> newKeywords = new LinkedList<String>();

	    if (newInput[0].equals("AP")){ //add error checking
		System.out.print("Enter a URL: ");
		sourceUrl = stdin.readLine();
		System.out.print("Enter keywords (space-separated): ");
		input = stdin.readLine();
		parsedInput = input.split(" ");
		for (int i = 0; i < parsedInput.length; i++)
		    newKeywords.add(parsedInput[i]);
		web.addPage(sourceUrl, newKeywords);
	    }
	    else if (newInput[0].equals("RP")){
		System.out.print("Enter a URL: ");
		input = stdin.readLine();
		web.removePage(input);
	    }
	    else if (newInput[0].equals("AL")){
		System.out.print("Enter a source URL: ");
		sourceUrl = stdin.readLine();
		System.out.print("Enter a destination URL: ");
		destinationUrl = stdin.readLine();
		web.addLink(sourceUrl, destinationUrl);
	    }
	    else if (newInput[0].equals("RL")){
		System.out.print("Enter a source URL: ");
		sourceUrl = stdin.readLine();
		System.out.print("Enter a destination URL: ");
		destinationUrl = stdin.readLine();
		web.removeLink(sourceUrl, destinationUrl);
	    }
	    else if (newInput[0].equals("P")){
		output = "\n    (I) Sort based on index (ASC)\n";
		output += "    (U) Sort based on URL (ASC)\n";
		output += "    (R) Sort based on rank (DSC)\n";
		output += "\nPlease select an option: ";
		System.out.print(output);

		input = stdin.readLine();
		newInput = input.split(" ");

		if (newInput[0].equals("I")){
		    Collections.sort(web.getPages(), new IndexComparator());
		    web.printTable();
		}
		else if (newInput[0].equals("U")){
		    Collections.sort(web.getPages(), new URLComparator());
		    web.printTable();
		}
		else if (newInput[0].equals("R")){
		    Collections.sort(web.getPages(), new RankComparator());
		    web.printTable();
		}
		else{
		    System.out.print("\nErroneous input detected, please try again (make sure to use capital letters and no spaces).\n");
		}
	    }
	    else if (newInput[0].equals("S")){
		System.out.print("Search keyword: ");
		input = stdin.readLine();
		web.search(input);
	    }
	    else if (newInput[0].equals("Q")){ //done
		System.out.println("\nGoodbye.\n");
		run = false;
	    }
	    else{ //done
		System.out.println("Erroneous input detected, please try again (make sure to use capital letters and no spaces).\n");
	    }

	}
	
    }
    
}
