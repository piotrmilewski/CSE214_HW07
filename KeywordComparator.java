/**
 * The <code>KeyboardComparator</code> class is a helper class that helps with sorting WebPages.
 *
 *
 * @author Piotr Milewski
 *    email: piotr.milewski@stonybrook.edu
 *    Stony Brook ID: 112291666
 **/

import java.util.Comparator;

public class KeywordComparator implements Comparator<Object>{

    public int compare(Object o1, Object o2){
	WebPage wp1 = (WebPage) o1;
	WebPage wp2 = (WebPage) o2;
	if (wp1.getRank() == wp2.getRank())
	    return 0;
	else if (wp1.getRank() < wp2.getRank())
	    return 1;
	else
	    return -1;
    }

}
