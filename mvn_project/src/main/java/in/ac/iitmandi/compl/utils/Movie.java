package in.ac.iitmandi.compl.utils;

import java.io.Serializable;
import com.google.gson.GsonBuilder;

public class Movie implements Serializable {

    public String genres;

    public Actor[] actors;

    public String name;

    // TODO - move to date!
    public Integer year;

    public Integer votes;

    public Float rating;

    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }

    /**
     * Manually modified to confine to JEP spec
     */
    public ValueMovie toValueMovie() {
        ValueMovie movie = new ValueMovie(genres, name, year, votes, rating);
//        System.out.println(movie.toString());
        return movie;
    }
    
    /**
     * Manually modified to confine to JEP spec
     */
    public UnboxedMovie toNonValueMovie() {
    	UnboxedMovie movie = new UnboxedMovie(genres, name, year, votes, rating);
        return movie;
    }
}
