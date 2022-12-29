package in.ac.iitmandi.compl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import in.ac.iitmandi.compl.utils.Movie;
import in.ac.iitmandi.compl.utils.MovieContainer;
import in.ac.iitmandi.compl.utils.RatingsSorter;
import in.ac.iitmandi.compl.utils.UnboxedMovie;
import in.ac.iitmandi.compl.utils.ValueActor;
import in.ac.iitmandi.compl.utils.ValueMovie;

public class ValueMovieQueries {

    public static List<MovieContainer> loadMovies(String jsonPath) throws FileNotFoundException, IOException {
        List<MovieContainer> movies = new ArrayList<>();
        Gson gson = new Gson();
        long memoryBefore;
        long memoryAfter;
        long startTime;
        long finishTime;
        memoryBefore = MovieQueries.memory();
        startTime = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(jsonPath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Movie movie = gson.fromJson(line, Movie.class);
                MovieContainer containerObj = new MovieContainer(movie.toValueMovie());
                movies.add(containerObj);
            }
        }
        finishTime = System.currentTimeMillis();
        memoryAfter = MovieQueries.memory();
        System.out.println(String.format("[Value-Memory] Loading movies took %d ms and use %d MB of heap memory", finishTime - startTime, memoryAfter - memoryBefore));
        return movies;
    }

 // Print how many movies were released in 1970.
    public static void q1(List<MovieContainer> movies) {
        long startTime;
        long finishTime;
        long count;
        startTime = System.currentTimeMillis();
        count = movies.stream().filter(s -> s.getMovie().getYear() == 1970).count();
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("[ValueMovies-Q1] took %d ms; result = %d", finishTime - startTime, count));
    }
    
 // Print one of the movies with highest rating.
    public static void q2(List<MovieContainer> movies) {
        long startTime;
        long finishTime;
        List<MovieContainer> result;
        result = new ArrayList<>(movies);
        startTime = System.currentTimeMillis();
        Collections.sort(result, new RatingsSorter());
        MovieContainer firstMovie = result.get(0);
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("[ValueMovies-Q2] took %d ms; result = %s", finishTime - startTime, firstMovie.getMovie()));
    }
    
    // Print the average rating of movies in 2000.
    public static void q3(List<MovieContainer> movies) {
        long startTime;
        long finishTime;
        startTime = System.currentTimeMillis();
        List<Float> ratingList = movies.stream().filter(s -> s.getMovie().getYear() == 2000).map(s -> s.getMovie().getRating()).collect(Collectors.toList());
        double average = ratingList.stream().mapToDouble(r -> r).average().orElse(Double.NaN);
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("[ValueMovies-Q3] took %d ms; result = %f", finishTime - startTime, average));
    }
    
 // Print the year with most no of votes.
    public static void q4(List<MovieContainer> movies) {
        long startTime;
        long finishTime;
        Map<Integer,Integer> yearVotesMap;
        Comparator<Integer> voteComparator = new Comparator<Integer>() {
            @Override public int compare(Integer val1, Integer val2) {
                return (val1 > val2 ? 1 : 0);
            }           
        };
        yearVotesMap = new HashMap<>();
        startTime = System.currentTimeMillis();
        for(MovieContainer movie:movies) {
        	if(yearVotesMap == null) {
        		yearVotesMap = new HashMap<>();
        	}
        	Integer year = movie.getMovie().getYear();
			if(yearVotesMap.containsKey(year)) {
				Integer cont = yearVotesMap.get(year);
        		Integer newVal = yearVotesMap.get(year)+movie.getMovie().getVotes();
				yearVotesMap.replace(year, newVal);
//        		System.out.println("Year: "+year+" NewCount: "+yearVotesMap.get(year)+" Actual Val:"+newVal);
        	}else {
        		yearVotesMap.put(year, movie.getMovie().getVotes());
//        		System.out.println("Year: "+year+" Count: "+yearVotesMap.get(year)+" Actual Val:"+movie.getMovie().getVotes());
        	}
        	
        }
        List<Integer> valList = new ArrayList<>(yearVotesMap.values());
        Collections.sort(valList, voteComparator);
        System.out.println(valList.get(0));
        Map.Entry<Integer,Integer> entry = yearVotesMap.entrySet().iterator().next();
        Integer key = entry.getKey();
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("[ValueMovies-Q4] took %d ms; result: "+key, finishTime - startTime));
    }
    
 // Print the year with most no of movies.
    public static void q5(List<MovieContainer> movies) {
        long startTime;
        long finishTime;
        SortedMap<Integer,Integer> yearCountMap;
        Comparator<Integer> countComparator = new Comparator<Integer>() {
            @Override public int compare(Integer val1, Integer val2) {
                return (val1 < val2 ? 1 : 0);
            }           
        };
        yearCountMap = new TreeMap<>(countComparator);
        startTime = System.currentTimeMillis();
        for(MovieContainer movie:movies) {
        	if(yearCountMap == null) {
        		yearCountMap = new TreeMap<>(countComparator);
        	}
        	Integer year = movie.getMovie().getYear();
			if(yearCountMap.containsKey(year)) {
				Integer cont = yearCountMap.get(year);
				Integer newVal = cont+1;
        		yearCountMap.replace(year,newVal);
        		
        		System.out.println("Year: "+year+" NewCount: "+yearCountMap.get(year)+" Actual Val:"+newVal);
        	}else {
        		yearCountMap.put(year, 1);
//        		System.out.println("Year: "+year+" CountVal: "+yearCountMap.get(year)+" Actual Val:1");
        	}
        	
        }
        List<Integer> valList = new ArrayList<>(yearCountMap.values());
//        valList.sort(countComparator);
        Collections.sort(valList, countComparator);
        System.out.println(valList.get(0));
        Map.Entry<Integer,Integer> entry = yearCountMap.entrySet().iterator().next();
        Integer key = entry.getKey();
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("[ValueMovies-Q5] took %d ms; result: "+key, finishTime - startTime));
    }
    
//    // Print the average age of mob town actors.
//    public static void q3(JavaRDD<ValueMovie.ref> movies) {
//        long startTime;
//        long finishTime;
//        startTime = System.currentTimeMillis();
//        JavaRDD<ValueMovie.ref> mobtown = movies.filter(s -> new String(s.name).equals("Mob Town"));
//        int year = mobtown.take(1).get(0).year;
//        JavaRDD<ValueActor.ref> actors = mobtown.flatMap(s -> Arrays.asList(s.actors).iterator());
//        int numActors = actors.collect().size();
//        JavaRDD<Integer> ages = actors.map(s -> year - s.birth);
//        int averageAge = ages.reduce((s, d) -> s + d) / numActors;
//        finishTime = System.currentTimeMillis();
//        System.out.println(String.format("[Vanilla-Q3] took %d ms; result = %s", finishTime - startTime, averageAge));
//    }

// // Print the average age of mob town actors.
//    public static void q3(List<MovieContainer> movies) {
//        long startTime;
//        long finishTime;
//        startTime = System.currentTimeMillis();
//        List<MovieContainer> mobtown = movies.stream().filter(s -> new String(s.getMovie().getName()).equals("Mob Town".toCharArray())).collect(Collectors.toList());
//        int year = mobtown.get(0).getMovie().year;
//        List<ValueActor.ref> actors = Arrays.asList(mobtown.get(0).getMovie().actors);
//        int numActors = actors.size();
//        List<Integer> ages = new ArrayList<>();
//        actors.forEach(actor -> ages.add(actor.death - actor.birth));
//        Integer ageSum = ages.stream().reduce(0, Integer::sum);
//        int averageAge = ageSum / numActors;
//        finishTime = System.currentTimeMillis();
//        System.out.println(String.format("[ValueMovies-Q3] took %d ms; result = %s", finishTime - startTime, averageAge));
//    }
    
    
    // Print the most popular actor name and the number of occurrences in movies.
//    public static void q4(JavaRDD<ValueMovie.ref> movies) {
//        long startTime;
//        long finishTime;
//        startTime = System.currentTimeMillis();
//        JavaRDD<ValueActor.ref> actors = movies.flatMap(s -> Arrays.asList(s.actors).iterator());
//        JavaPairRDD<String, Integer> tuplesNameNumber = actors.mapToPair(s -> new Tuple2<>(new String(s.name), 1));
//        tuplesNameNumber = tuplesNameNumber.reduceByKey((s, d) -> s + d);
//        JavaPairRDD<Integer, String> tuplesNumberName = tuplesNameNumber.mapToPair(s -> s.swap());
//        Tuple2<Integer, String> popularNumberName = tuplesNumberName.sortByKey(false).first();
//        finishTime = System.currentTimeMillis();
//        System.out.println(String.format("[Vanilla-Q4] took %d ms; result = %s", finishTime - startTime, popularNumberName));
//    }
//
//    // Print the year with more votes.
//    public static void q5(JavaRDD<ValueMovie.ref> movies) {
//        long startTime;
//        long finishTime;
//        startTime = System.currentTimeMillis();
//        JavaPairRDD<Integer, Integer> tuplesYearVotes = movies.mapToPair(s -> new Tuple2<>(s.year, s.votes));
//        tuplesYearVotes = tuplesYearVotes.reduceByKey((s, d) -> s + d);
//        JavaPairRDD<Integer, Integer> tuplesVotesYear = tuplesYearVotes.mapToPair(s -> s.swap());
//        Tuple2<Integer, Integer> mostVotesYear = tuplesVotesYear.sortByKey(false).first();
//        finishTime = System.currentTimeMillis();
//        System.out.println(String.format("[Vanilla-Q5] took %d ms; result = %s", finishTime - startTime, mostVotesYear));
//    }
//
//    // Actor with highest average rating of all moves he/she participated.
//    public static void q6(JavaRDD<ValueMovie.ref> movies) {
//        long startTime;
//        long finishTime;
//        startTime = System.currentTimeMillis();
//        JavaPairRDD<Float, ValueActor.ref> tuplesActorRating = movies.flatMapToPair(s -> {
//            float rating = s.rating;
//            Set<Tuple2<ValueActor, Float>> tuples = new HashSet<>();
//            for (ValueActor actor : s.actors) {
//                tuples.add(new Tuple2<>(actor, rating));
//            }
//            return tuples.iterator();
//        });
//        tuplesActorRating = tuplesActorRating.reduceByKey((s, d) -> s + d);
//        JavaPairRDD<Float, ValueActor.ref> tuplesRatingActor = tuplesActorRating.mapToPair(s -> s.swap());
//        Tuple2<Float, ValueActor.ref> highestRateActor = tuplesRatingActor.sortByKey(false).first();
//        finishTime = System.currentTimeMillis();
//        System.out.println(String.format("[Vanilla-Q6] took %d ms; result = %s", finishTime - startTime, highestRateActor));
//    }

    public static void printDataset(List<MovieContainer> movies) {
        for (MovieContainer movie : movies) {
            System.out.println(movie);
        }
    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: <appname> <input file>");
            System.exit(1);
        }
        List<MovieContainer> movies = loadMovies(args[0]);
        q1(movies);
        System.gc();
        q2(movies);
        System.gc();
        q3(movies);
        System.gc();
        q4(movies);
        System.gc();
        q5(movies);
        System.gc();
//        q3(moviesRDD);
//        System.gc();
//        q4(moviesRDD);
//        System.gc();
//        q5(moviesRDD);
//        System.gc();
//        q6(moviesRDD);
    }
}
