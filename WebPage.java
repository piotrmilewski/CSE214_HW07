/**
 * The <code>WebPage</code> class represents a hyperlinked document that is used to store information such as
 * the url, the index, the rank, and the keywords associated to the webpage.
 *
 *
 * @author Piotr Milewski
 *    email: piotr.milewski@stonybrook.edu
 *    Stony Brook ID: 112291666
 **/

import java.util.LinkedList;

public class WebPage{

    private String url;
    private int index;
    private int rank;
    private LinkedList<String> keywords;

    public WebPage(){
	url = "";
	index = 0;
	rank = 0;
	keywords = new LinkedList<String>();
    }

    public WebPage(String newUrl, int newIndex, int newRank, LinkedList<String> newKeywords){
	url = newUrl;
	index = newIndex;
	rank = newRank;
	keywords = newKeywords;
    }

    public String getUrl(){
	return url;
    }

    public int getIndex(){
	return index;
    }

    public int getRank(){
	return rank;
    }

    public LinkedList<String> getKeywords(){
	return keywords;
    }

    public void setUrl(String newUrl){
	url = newUrl;
    }

    public void setIndex(int newIndex){
	index = newIndex;
    }

    public void setRank(int newRank){
	rank = newRank;
    }

    public void setKeywords(LinkedList<String> newKeywords){
	keywords = newKeywords;
    }
    
    public String toString(){
	String output = "";
	output += String.format("%3s%2s|%1s%-21s|%3s%-4s|%-19s|%1s%20s\n", index, "", "", url, "", rank, "*******************", "", keywords);
	return output;
    }
}
