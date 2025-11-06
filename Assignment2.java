import java.util.*;

class Movie {
    String title;
    double rating;
    int year;
    int popularity;

    Movie(String title, double rating, int year, int popularity) {
        this.title = title;
        this.rating = rating;
        this.year = year;
        this.popularity = popularity;
    }
}

public class Assignment2{

   
    static boolean cmpRating(Movie a, Movie b) {
        return a.rating > b.rating;
    }

    static boolean cmpYear(Movie a, Movie b) {
        return a.year > b.year;
    }

    static boolean cmpPopularity(Movie a, Movie b) {
        return a.popularity > b.popularity;
    }

    
    static int partition(List<Movie> movies, int low, int high, Comparator<Movie> cmp) {
        Movie pivot = movies.get(high);
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (cmp.compare(movies.get(j), pivot) > 0) {
                i++;
                Collections.swap(movies, i, j);
            }
        }
        Collections.swap(movies, i + 1, high);
        return i + 1;
    }

    
    static void quickSort(List<Movie> movies, int low, int high, Comparator<Movie> cmp) {
        if (low < high) {
            int pi = partition(movies, low, high, cmp);
            quickSort(movies, low, pi - 1, cmp);
            quickSort(movies, pi + 1, high, cmp);
        }
    }

    
    static void printMovies(List<Movie> movies) {
        for (Movie m : movies) {
            System.out.println(m.title + " | Rating: " + m.rating + " | Year: " + m.year + " | Popularity: " + m.popularity);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        List<Movie> movies = new ArrayList<>(Arrays.asList(
                new Movie("Movie A", 8.2, 2019, 1500),
                new Movie("Movie B", 9.1, 2021, 3000),
                new Movie("Movie C", 7.5, 2018, 2000),
                new Movie("Movie D", 8.9, 2020, 2500),
                new Movie("Movie E", 6.8, 2017, 1200)
        ));

        System.out.println("Sort movies by:\n1. IMDB Rating\n2. Release Year\n3. Popularity");
        System.out.print("Enter choice: ");
        int choice = sc.nextInt();

        Comparator<Movie> cmp;
        switch (choice) {
            case 1:
                cmp = Comparator.comparingDouble((Movie m) -> m.rating).reversed();
                break;
            case 2:
                cmp = Comparator.comparingInt((Movie m) -> m.year).reversed();
                break;
            case 3:
                cmp = Comparator.comparingInt((Movie m) -> m.popularity).reversed();
                break;
            default:
                System.out.println("Invalid choice. Sorting by rating.");
                cmp = Comparator.comparingDouble((Movie m) -> m.rating).reversed();
        }

        quickSort(movies, 0, movies.size() - 1, cmp);

        System.out.println("\nMovies sorted:");
        printMovies(movies);

        sc.close();
    }
}
/*1. IMDB Rating
2. Release Year
3. Popularity
Enter choice:
2

Movies sorted:
Movie E | Rating: 6.8 | Year: 2017 | Popularity: 1200
Movie C | Rating: 7.5 | Year: 2018 | Popularity: 2000
Movie A | Rating: 8.2 | Year: 2019 | Popularity: 1500
Movie D | Rating: 8.9 | Year: 2020 | Popularity: 2500

Movie B | Rating: 9.1 | Year: 2021 | Popularity: 3000*/
