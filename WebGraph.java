/**
 * The <code>WebGraph</code> class organizes the WebPage objects as a directed graph. It contains a Collection
 * of WebPages member variable called pages and a 2D array of ints member variable called links.
 *
 *
 * @author Piotr Milewski
 *    email: piotr.milewski@stonybrook.edu
 *    Stony Brook ID: 112291666
 **/

import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Comparator;
import java.util.Collections;

public class WebGraph{

    public static final int MAX_PAGES = 40;
    private LinkedList<WebPage> pages;
    private int[][] edges;
    private static int logicalSize = 0;

    private WebGraph(LinkedList<WebPage> newPages, int[][] newEdges){
	pages = newPages;
	edges = newEdges;
    }

    public LinkedList<WebPage> getPages(){
	return pages;
    }
    
    public static WebGraph buildFromFiles(String pagesFile, String linksFile) throws IllegalArgumentException, IOException{
	FileInputStream fis = new FileInputStream(pagesFile);
	InputStreamReader inStream = new InputStreamReader(fis);
	BufferedReader reader = new BufferedReader(inStream);

	LinkedList<WebPage> tempPages = new LinkedList<WebPage>();
	WebPage newPage;	
	String newUrl;
	int index = 0;

	String line = reader.readLine();
	String[] words;
	while (line != null){
	    LinkedList<String> newKeywords = new LinkedList<String>();
	    words = line.split(" ");
	    newUrl = words[4];
	    for (int i = 5; i < words.length; i++){
		newKeywords.add(words[i]);
	    }
	    newPage = new WebPage(newUrl, index, 0, newKeywords);
	    //System.out.println(newPage);
	    tempPages.add(newPage);
	    //System.out.println(tempPages);
	    line = reader.readLine();
	    logicalSize++;
	    index++;
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
		if (curr.getUrl().equals(words[4]))
		    indexFrom = curr.getIndex();
	    }
	    litr = tempPages.listIterator();
	    while (litr.hasNext()){
		WebPage curr = litr.next();
		if (curr.getUrl().equals(words[5]))
		    indexTo = curr.getIndex();
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
	ListIterator<WebPage> litr = pages.listIterator();
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
	if (source == null || destination == null)
	    illegalArgumentExceptionHelper("urls cannot be null");
	int indexFrom = -1;
	int indexTo = -1;
	ListIterator<WebPage> litr = pages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (curr.getUrl().equals(source))
		indexFrom = curr.getIndex();
	}

	litr = pages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (curr.getUrl().equals(destination))
		indexTo = curr.getIndex();
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
	if (pages.size() == 0)
	    illegalArgumentExceptionHelper("Nothing to remove");
	int index = -1;
	ListIterator<WebPage> litr = pages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (curr.getUrl().equals(url))
		index = curr.getIndex();
	}
	if (index != -1){
	    pages.remove(index);
	    System.out.println(url + " has been removed from the graph!");
	    logicalSize--;
	}
	else{
	    illegalArgumentExceptionHelper("url could not be found");
	    return;
	}

	for (int i = index; i < pages.size(); i++)
	    pages.get(i).setIndex(pages.get(i).getIndex()-1);

	for (int j = 0; j < pages.size(); j++){ //CHECK LATER CAUSE I ADD
	    for (int k = 0; k < pages.size(); k++)
		edges[k][index+j] = edges[k][index+j+1];
	    for (int k = 0; k < pages.size()-1; k++)
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
	    if (curr.getUrl().equals(source))
		indexFrom = curr.getIndex();
	}

	litr = pages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (curr.getUrl().equals(destination))
		indexTo = curr.getIndex();
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
		System.out.println(curr.getIndex());
		if (edges[i][curr.getIndex()] == 1)
		    newRank++;
	    }
	    curr.setRank(newRank);
	}
    }

    public void search(String keyword) throws IllegalArgumentException{
	String tempStorage = "";
	LinkedList<WebPage> searched = new LinkedList<WebPage>();
	ListIterator<WebPage> litr = pages.listIterator();
	boolean invalidKeyword = false;
	boolean found = false;
	String output = "\nRank   PageRank    URL\n";
	output += "---------------------------------------------\n";
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    if (curr.getKeywords().indexOf(keyword) != -1){
		invalidKeyword = true;
		searched.add(curr);
	    }
	}
	Collections.sort(searched, new KeywordComparator());

	litr = searched.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    int index = curr.getIndex();
	    int rank = curr.getRank();
	    String url = curr.getUrl();
	    output += String.format("%3s%2s|%4s%-5s|%1s%-21s\n", index, "", "", rank, "", url);
	}
	if (invalidKeyword)
	    System.out.println(output);
	else
	    illegalArgumentExceptionHelper("Keyword not found in the graph");
    }

    public void printTable(){
	String tempStorage = "";
	String output = "\nIndex     URL               PageRank  Links               Keywords\n";
	output += "------------------------------------------------------------------------------------------------------\n";
	ListIterator<WebPage> litr = pages.listIterator();
	while (litr.hasNext()){
	    WebPage curr = litr.next();
	    output += curr;
	    tempStorage = "";
	    for (int i = 0; i < logicalSize; i++){
		if (edges[curr.getIndex()][i] == 1){
		    if (i != 0 && tempStorage != "")
			tempStorage += ", ";
		    tempStorage += i;
			
		}
	    }
	    tempStorage = String.format("%1s%-18s", "", tempStorage);
	    output = output.replace("*******************", tempStorage);
	}
	System.out.println(output);
    }

    private void illegalArgumentExceptionHelper(String message){
	try{
	    throw new IllegalArgumentException(message);
	}
	catch (IllegalArgumentException iae){
	    System.out.println("\n" + message);
	}
    }
}
/*
  for (int i = 0; i < logicalSize; i++){
  System.out.println();
  for (int j = 0; j < logicalSize; j++){
  System.out.print(edges[i][j] + " ");
  }
  }
*/
