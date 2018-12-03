import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;

public class WebGraph{

    public static final int MAX_PAGES = 40;
    private LinkedList<WebPage> pages;
    private int[][] edges;
    private int logicalSize = 0;

    private WebGraph(LinkedList<WebPage> newPages, int[][] newEdges){
	pages = newPages;
	edges = newEdges;
    }
    
    public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException, IOException{
	FileInputStream fis = new FileInputStream(pagesFile);
	InputStreamReader inStream = new InputStreamReader(fis);
	BufferedReader reader = new BufferedReader(inStream);

	LinkedList<WebPage> tempPages = new LinkedList<WebPage>();
	WebPage newPage;
	LinkedList<String> newKeywords = new LinkedList<String>();
	String newUrl;
	int index = 0;

	String line = reader.readLine();
	String[] words;
	while (line != null){
	    words = line.split(" ");
	    newUrl = words[0];
	    index++;
	    for (int i = 1; i < words.length; i++){
		newKeywords.add(words[i]);
	    }
	    newPage = new WebPage(newUrl, index, 0, newKeywords);
	    tempPages.add(newPage);
	    line = reader.readLine();
	    logicalSize++;
	}

	fis = new FileInputStream(linksFile);
	inStream = new InputStreamReader(fis);
	reader = new BufferedReader(inStream);
	
	int[][] tempEdges = new int[MAX_PAGES][MAX_PAGES];
	int indexFrom = 0;
	int indexTo = 0;

	line = reader.readLine();
	while (line != null){
	    words = line.split(" ");
	    ListIterator<WebPage> litr = tempPages.listIterator();
	    while (litr.hasNext()){
		WebPage curr = litr.next();
		if (curr.getUrl().equals(words[0]))
		    indexFrom = curr.getIndex();
	    }

	    litr = tempPages.listIterator();
	    while (litr.hasNext()){
		WebPage curr = litr.next();
		if (curr.getUrl().equals(words[1]))
		    indexFrom = curr.getIndex();
	    }
	    tempEdges[indexFrom][indexTo] = 1;
	    line = reader.readLine();
	}
	WebGraph newGraph = new WebGraph(tempPages, tempEdges);
	newGraph.updatePageRanks();
	return newGraph;
    }
    
    public void addPage(String url, LinkedList<String> keywords) throws IllegalArgumentException{
	if (url == null){
	    illegalArgumentExceptionHelper("url cannot be null");
	}
	ListIterator<WebPage> litr = tempPages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (url.equals(curr.getUrl())){
		illegalArgumentExceptionHelper("url already exists in the graph");
	    }
	}
	WebPage newPage = new WebPage(url, pages.size(), 0, keywords);
	pages.add(newPage);
	logicalSize++;
	updatePageRanks();
    }

    public void addLink(String source, String destination) throws IllegalArgumentException{
	if (sourse == null || destination == null)
	    illegalArgumentExceptionHelper("urls cannot be null");
	int indexFrom = -1;
	int indexTo = -1;
	ListIterator<WebPage> litr = pages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (curr.getUrl().equals(words[0]))
		indexFrom = curr.getIndex();
	}

	litr = tempPages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (curr.getUrl().equals(words[1]))
		indexFrom = curr.getIndex();
	}
	if (indexFrom != -1 && indexTo != -1){
	    edges[indexFrom][indexTo] = 1;
	}
	else{
	    illegalArgumentExceptionHelper("url could not be found");
	}
	updatePageRanks();
    }

    public void removePage(String url){
	if (url == null)
	    illegalArgumentExceptionHelper("urls cannot be null");
	int index = -1;
	ListIterator<WebPage> litr = pages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (curr.getUrl().equals(words[0]))
		index = curr.getIndex();
	}
	if (index != -1){
	    pages.remove(index);
	    logicalSize--;
	}
	else{
	    illegalArgumentExceptionHelper("url could not be found");
	}

	for (int i = index; i < pages.size(); i++)
	    pages.get(i).setIndex(pages.get(i).getIndex()-1);

	for (int j = 0; j < pages.size(); j++){ //CHECK LATER CAUSE I ADD
	    for (int k = 0; k < pages.size(); k++)
		edges[k][index+j] = edges[k][index+j+1];
	    for (k = 0; k < pages.size()-1; k++)
		edges[index+j][k] = edges[index+j+1][k];
	}
	updatePageRanks();
    }

    public void removeLink(String source, String destination){
	if (source == null || destination == null)
	    illegalArgumentExceptionHelper("urls cannot be null");

	int indexFrom = -1;
	int indexTo = -1;
	ListIterator<WebPage> litr = pages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (curr.getUrl().equals(words[0]))
		indexFrom = curr.getIndex();
	}

	litr = tempPages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (curr.getUrl().equals(words[1]))
		indexFrom = curr.getIndex();
	}
	if (indexTo != -1 && indexFrom != -1)
	    edges[indexFrom][indexTo] = 0;
	else
	    illegalArgumentExceptionHelper("one of the URLs could not be found");
	updatePageRanks();
    }

    public void updatePageRanks(){
	ListIterator<WebPage> litr = pages.listIterator();
	int newRank = 0;
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    newRank = 0;
	    for (int i = 0; i < logicalSize; i++){
		if (edges[i][curr.getIndex()] == 1)
		    newRank++;
	    }
	    curr.setRank(newRank);
	}
    }

    public void printTable(){
	String tempStorage = "";
	String output = "Index     URL               PageRank  Links               Keywords\n";
	output += "------------------------------------------------------------------------------------------------------\n";
	ListIterator<WebPage> litr = pages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    output += curr;
	    if (curr.getRank() > 0){
		tempStorage = "";
		for (int i = 0; i < logicalSize; i++){
		    if (edges[curr.getIndex()][i] == 1)
			tempStorage += i;
		    if (i != logicalSize-1)
			tempStorage += ", ";
		}
	    }
	    output = output.replace("***", tempStorage);
	}
	System.out.println(output);
    }

    private void illegalArgumentExceptionHelper(String message){
	try{
	    throw new IllegalArgumentException(message);
	}
	catch (IllegalArgumentException iae){
	    System.out.println(iae);
	}
    }
}
