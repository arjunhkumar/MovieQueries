/**
 * 
 */
package in.ac.iitmandi.compl.utils;

import java.io.Serializable;

/**
 * Created for containerization.
 * @author arjun
 *
 */
public class MovieContainer implements Serializable {

	
	public ValueMovie movie;

	public MovieContainer(ValueMovie movie) {
		this.movie = movie;
	}

	public ValueMovie getMovie() {
		return movie;
	}

	public void setMovie(ValueMovie movie) {
		this.movie = movie;
	}
	
	
}
