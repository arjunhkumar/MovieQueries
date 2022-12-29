/**
 * 
 */
package in.ac.iitmandi.compl.utils;

import java.util.Comparator;

/**
 * @author arjun
 *
 */
public class UnboxedRatingsSorter implements Comparator<UnboxedMovie>{

	public int compare(UnboxedMovie m1, UnboxedMovie m2) {
		return (m1.getRating() > m2.getRating() ? 1 : 0);
	}

}
