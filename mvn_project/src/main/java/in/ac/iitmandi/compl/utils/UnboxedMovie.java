package in.ac.iitmandi.compl.utils;

import java.io.Serializable;
import com.google.gson.GsonBuilder;

public class UnboxedMovie implements Serializable {

    private String genres;

//    public UnboxedActor[] actors;

    private String name;

    // TODO - move to date!
    private Integer year;

    private Integer votes;

    private Float rating;

    /** Added to increase instance size*/
    
//    private String genres1;
//
//	private String name1;
//	
//	private Integer year1;
//	
//	private Integer votes1;
//	
//	private Float rating1;
//	
//	private String genres2;
//
//	private String name2;
//		
//	private Integer year2;
//		
//	private Integer votes2;
//		
//	private Float rating2;
//	
//	private String genres3;
//
//	private String name3;
//		
//	private Integer year3;
//		
//	private Integer votes3;
//		
//	private Float rating3;
    
    
    @Override
    public String toString() {
        return new GsonBuilder().create().toJson(this);
    }

	/**
	 * @param genres
	 * @param name
	 * @param year
	 * @param votes
	 * @param rating
	 */
	public UnboxedMovie(String genres, String name, Integer year, Integer votes, Float rating) {
		this.genres = genres;
		this.name = name;
		this.year = year.intValue();
		this.votes = votes.intValue();
		this.rating = rating.floatValue();
//		this.genres1 = genres;
//		this.name1 = name;
//		this.year1 = year.intValue();
//		this.votes1 = votes.intValue();
//		this.rating1 = rating.floatValue();
//		this.genres2 = genres;
//		this.name2 = name;
//		this.year2 = year.intValue();
//		this.votes2 = votes.intValue();
//		this.rating2 = rating.floatValue();
//		this.genres3 = genres;
//		this.name3 = name;
//		this.year3 = year.intValue();
//		this.votes3 = votes.intValue();
//		this.rating3 = rating.floatValue();
	}

	/**
	 * @return the genres
	 */
	public String getGenres() {
		return genres;
	}

	/**
	 * @param genres the genres to set
	 */
	public void setGenres(String genres) {
		this.genres = genres;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the year
	 */
	public Integer getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(Integer year) {
		this.year = year;
	}

	/**
	 * @return the votes
	 */
	public Integer getVotes() {
		return votes;
	}

	/**
	 * @param votes the votes to set
	 */
	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	/**
	 * @return the rating
	 */
	public Float getRating() {
		return rating;
	}

	/**
	 * @param rating the rating to set
	 */
	public void setRating(Float rating) {
		this.rating = rating;
	}

    
}
