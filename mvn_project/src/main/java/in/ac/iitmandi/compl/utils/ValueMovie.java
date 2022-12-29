package in.ac.iitmandi.compl.utils;

import java.io.Serializable;
import com.google.gson.GsonBuilder;

public primitive class ValueMovie implements Serializable {

	private final char[] genres;

    /* Minimal version not including any complex inlining operations */
//    public final ValueActor[] actors;

	private final char[] name;

	private final int year;

	private final int votes;

	private final float rating;

    /**
     * Manually modified to confine to JEP spec
     */
    public ValueMovie(String genres, String name, Integer year, Integer votes, Float rating) {
		this.genres = convertStringToCharArray(genres);
        this.name = convertStringToCharArray(name);
        this.year = year.intValue();
        this.votes = votes.intValue();
        this.rating = rating.floatValue();
    }

	private static char[] convertStringToCharArray(String stringVal) {
		char[] charArray = new char[stringVal.length()];
        for (int i = 0; i < stringVal.length(); i++) {
        	charArray[i] = stringVal.charAt(i);
        }
		return charArray;
	}

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Name: ");
        sb.append(this.getName());
        sb.append(" Genres: ");
        sb.append(this.getGenres());
        sb.append(" Year: "+this.getYear());
        sb.append(" Votes: "+this.getVotes());
        sb.append(" Rating: "+this.getRating());
        return sb.toString();
    }

    /**
	 * @return the genres
	 */
	public char[] getGenres() {
		return genres;
	}

	/**
	 * @return the name
	 */
	public char[] getName() {
		return name;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @return the votes
	 */
	public int getVotes() {
		return votes;
	}

	/**
	 * @return the rating
	 */
	public float getRating() {
		return rating;
	}

	public Movie toMovie() {
        Movie movie = new Movie();
        movie.genres = new String(genres);
        movie.name = new String(name);
        movie.year = year;
        movie.votes = votes;
        movie.rating = rating;
        return movie;
    }
}
