import java.util.LinkedList;

public class WebPage{

    private String url;
    private int index;
    private int rank;
    private LinkedList<String>keywords;

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
	output += String.format("%10s|%5s|%14s|***|%13s\n", index, url, rank, keywords);
	return output;
    }
}
