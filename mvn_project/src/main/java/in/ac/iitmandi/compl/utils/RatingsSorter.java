/**
 * 
 */
package in.ac.iitmandi.compl.utils;

import java.util.Comparator;

/**
 * @author arjun
 *
 */
public class RatingsSorter implements Comparator<MovieContainer>{

	public int compare(MovieContainer m1, MovieContainer m2) {
		return (m1.getMovie().getRating() > m2.getMovie().getRating() ? 1 : 0);
	}
	
}
