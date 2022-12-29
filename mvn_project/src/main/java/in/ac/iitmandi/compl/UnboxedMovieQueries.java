package in.ac.iitmandi.compl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import in.ac.iitmandi.compl.utils.Movie;
import in.ac.iitmandi.compl.utils.MovieContainer;
import in.ac.iitmandi.compl.utils.UnboxedMovie;
import in.ac.iitmandi.compl.utils.UnboxedRatingsSorter;

public class UnboxedMovieQueries {

    public static long memory() {
        System.gc();
        long bytes = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long mbytes = bytes / 1024 / 1024;
        return mbytes;
    }

    public static List<UnboxedMovie> loadMovies(String jsonPath) throws FileNotFoundException, IOException {
        List<UnboxedMovie> movies = new ArrayList<>();
        Gson gson = new Gson();
        long memoryBefore;
        long memoryAfter;
        long startTime;
        long finishTime;
        memoryBefore = memory();
        startTime = System.currentTimeMillis();
        try (BufferedReader br = new BufferedReader(new FileReader(jsonPath))) {
            String line;
            while ((line = br.readLine()) != null) {
            	Movie movie = gson.fromJson(line, Movie.class);
            	UnboxedMovie valuemovie = movie.toNonValueMovie();
                movies.add(valuemovie);
            }
        }
        finishTime = System.currentTimeMillis();
        memoryAfter = memory();
        System.out.println(String.format("[NonValue-Memory] Loading movies took %d ms and use %d MB of heap memory", finishTime - startTime, memoryAfter - memoryBefore));
        return movies;
    }

    // Print how many movies were released in 1970.
    public static void q1(List<UnboxedMovie> movies) {
        long startTime;
        long finishTime;
        long count;
        startTime = System.currentTimeMillis();
        count = movies.stream().filter(s -> s.getYear() == 1970).count();
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("[NonValue-Q1] took %d ms; result = %d", finishTime - startTime, count));
    }
    

 // Print one of the movies with highest rating.
    public static void q2(List<UnboxedMovie> movies) {
        long startTime;
        long finishTime;
        List<UnboxedMovie> result;
        result = new ArrayList<>(movies);
        startTime = System.currentTimeMillis();
        Collections.sort(result, new UnboxedRatingsSorter());
        UnboxedMovie firstMovie = result.get(0);
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("[NonValue-Q2] took %d ms; result = %s", finishTime - startTime, firstMovie));
    }
    
    
    // Print the average rating of movies in 2000.
    public static void q3(List<UnboxedMovie> movies) {
        long startTime;
        long finishTime;
        startTime = System.currentTimeMillis();
        List<Float> ratingList = movies.stream().filter(s -> s.getYear() == 2000).map(s -> s.getRating()).collect(Collectors.toList());
        double average = ratingList.stream().mapToDouble(r -> r).average().orElse(Double.NaN);
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("[NonValue-Q3] took %d ms; result = %f", finishTime - startTime, average));
    }
    
    // Print the year with most no of votes.
    public static void q4(List<UnboxedMovie> movies) {
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
        for(UnboxedMovie movie:movies) {
        	if(yearVotesMap == null) {
        		yearVotesMap = new HashMap<>();
        	}
        	Integer year = movie.getYear();
			if(yearVotesMap.containsKey(year)) {
        		yearVotesMap.replace(year, (yearVotesMap.get(year)+movie.getVotes()));
        	}else {
        		yearVotesMap.put(year, movie.getVotes());
        	}
        	
        }
        List<Integer> valList = new ArrayList<>(yearVotesMap.values());
        valList.sort(voteComparator);
//        System.out.println(valList.get(0));
        Map.Entry<Integer,Integer> entry = yearVotesMap.entrySet().iterator().next();
        Integer key = entry.getKey();
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("[NonValue-Q4] took %d ms; result: "+key, finishTime - startTime));
    }
    
    // Print the year with most no of movies.
    public static void q5(List<UnboxedMovie> movies) {
        long startTime;
        long finishTime;
        Map<Integer,Integer> yearCountMap;
        Comparator<Integer> countComparator = new Comparator<Integer>() {
            @Override public int compare(Integer val1, Integer val2) {
                return (val1 > val2 ? 1 : 0);
            }           
        };
        yearCountMap = new HashMap<>();
        startTime = System.currentTimeMillis();
        for(UnboxedMovie movie:movies) {
        	if(yearCountMap == null) {
        		yearCountMap = new HashMap<>();
        	}
        	Integer year = movie.getYear();
			if(yearCountMap.containsKey(year)) {
        		yearCountMap.replace(year, (yearCountMap.get(year)+1));
        	}else {
        		yearCountMap.put(year, 1);
        	}
        	
        }
        List<Integer> valList = new ArrayList<>(yearCountMap.values());
        valList.sort(countComparator);
//        System.out.println(valList.get(0));
        Map.Entry<Integer,Integer> entry = yearCountMap.entrySet().iterator().next();
        Integer key = entry.getKey();
        finishTime = System.currentTimeMillis();
        System.out.println(String.format("[NonValue-Q5] took %d ms; result: "+key, finishTime - startTime));
    }
    
//    // Print the average age of mob town actors.
//    public static void q3(JavaRDD<UnboxedMovie> movies) {
//        long startTime;
//        long finishTime;
//        startTime = System.currentTimeMillis();
//        JavaRDD<UnboxedMovie> mobtown = movies.filter(s -> s.name.equals("Mob Town"));
//        int year = mobtown.take(1).get(0).year;
//        JavaRDD<UnboxedActor> actors = mobtown.flatMap(s -> Arrays.asList(s.actors).iterator());
//        int numActors = actors.collect().size();
//        JavaRDD<Integer> ages = actors.map(s -> year - s.birth);
//        int averageAge = ages.reduce((s, d) -> s + d) / numActors;
//        finishTime = System.currentTimeMillis();
//        System.out.println(String.format("[Vanilla-Q3] took %d ms; result = %s", finishTime - startTime, averageAge));
//    }
    
//  Print the average ratings of movies in the year 2000.
//    public static void q3(List<UnboxedMovie> movies) {
//    	long startTime;
//    	long finishTime;
//    	startTime = System.currentTimeMillis();
//    	List<UnboxedMovie> mobtown = movies.stream().filter(s -> s.getYear().compareTo(null));
//    	int year = mobtown.take(1).get(0).year;
//    	JavaRDD<UnboxedActor> actors = mobtown.flatMap(s -> Arrays.asList(s.actors).iterator());
//    	int numActors = actors.collect().size();
//    	JavaRDD<Integer> ages = actors.map(s -> year - s.birth);
//    	int averageAge = ages.reduce((s, d) -> s + d) / numActors;
//    	finishTime = System.currentTimeMillis();
//    	System.out.println(String.format("[Vanilla-Q3] took %d ms; result = %s", finishTime - startTime, averageAge));
//    }

    
    
//
//    // Print the most popular actor name and the number of occurrences in movies.
//    public static void q4(JavaRDD<UnboxedMovie> movies) {
//        long startTime;
//        long finishTime;
//        startTime = System.currentTimeMillis();
//        JavaRDD<UnboxedActor> actors = movies.flatMap(s -> Arrays.asList(s.actors).iterator());
//        JavaPairRDD<String, Integer> tuplesNameNumber = actors.mapToPair(s -> new Tuple2<>(new String(s.name), 1));
//        tuplesNameNumber = tuplesNameNumber.reduceByKey((s, d) -> s + d);
//        JavaPairRDD<Integer, String> tuplesNumberName = tuplesNameNumber.mapToPair(s -> s.swap());
//        Tuple2<Integer, String> popularNumberName = tuplesNumberName.sortByKey(false).first();
//        finishTime = System.currentTimeMillis();
//        System.out.println(String.format("[Vanilla-Q4] took %d ms; result = %s", finishTime - startTime, popularNumberName));
//    }
//
//    // Print the year with more votes.
//    public static void q5(JavaRDD<UnboxedMovie> movies) {
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
//    public static void q6(JavaRDD<UnboxedMovie> movies) {
//        long startTime;
//        long finishTime;
//        startTime = System.currentTimeMillis();
//        JavaPairRDD<UnboxedActor, Float> tuplesActorRating = movies.flatMapToPair(s -> {
//            float rating = s.rating;
//            Set<Tuple2<UnboxedActor, Float>> tuples = new HashSet<>();
//            for (UnboxedActor actor : s.actors) {
//                tuples.add(new Tuple2<>(actor, rating));
//            }
//            return tuples.iterator();
//        });
//        tuplesActorRating = tuplesActorRating.reduceByKey((s, d) -> s + d);
//        JavaPairRDD<Float, UnboxedActor> tuplesRatingActor = tuplesActorRating.mapToPair(s -> s.swap());
//        Tuple2<Float, UnboxedActor> highestRateActor = tuplesRatingActor.sortByKey(false).first();
//        finishTime = System.currentTimeMillis();
//        System.out.println(String.format("[Vanilla-Q6] took %d ms; result = %s", finishTime - startTime, highestRateActor));
//    }

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.err.println("Usage: <appname> <input file>");
            System.exit(1);
        }
        List<UnboxedMovie> movies = loadMovies(args[0]);
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
//        q4(moviesRDD);
//        System.gc();
//        q5(moviesRDD);
//        System.gc();
//        q6(moviesRDD);
    }
}
