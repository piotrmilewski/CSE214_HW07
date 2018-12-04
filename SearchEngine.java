import java.io.*;

public class SearchEngine{

    public static final String PAGES_FILE = "pages.txt";
    public static final String LINKS_FILE = "links.txt";
    private static WebGraph web;
    private static WebPage compile; //ensures WebPage compiles

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

	    if (input.equals("AP")){

	    }
	    else if (input.equals("RP")){

	    }
	    else if (input.equals("AL")){
		System.out.print("Enter a source URL: ");
		sourceUrl = stdin.readLine();
		System.out.print("Enter a destination URL: ");
		destinationUrl = stdin.readLine();

		web.addLink(sourceUrl, destinationUrl);
	    }
	    else if (input.equals("RL")){

	    }
	    else if (input.equals("P")){
		output = "\n    (I) Sort based on index (ASC)\n";
		output += "    (U) Sort based on URL (ASC)\n";
		output += "    (R) Sort based on rank (DSC)\n";
		System.out.print(output);

		input = stdin.readLine();

		
	    }
	    else if (input.equals("S")){

	    }
	    else if (input.equals("Q")){
		System.out.println("\nGoodbye.\n");
		run = false;
	    }
	    else{
		System.out.println("Erroneous input detected, please try again (make sure to use capital letters).\n");
	    }

	}
	
    }
    
}
