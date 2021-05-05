package cst438;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AppController {  
	
	@Autowired
	MovieRatingRepository movieRatingRepository;
	
	@GetMapping("/hello")  // URL like  localhost:8080/hello?name=David
	public String hello(@RequestParam("name") String name, Model model) {
		model.addAttribute("name", name);
		model.addAttribute("date", new java.util.Date().toString());
		return "index";
 
	}
	
	@GetMapping("/movies")
	public String getAllMovies(Model model) {
		Iterable<MovieRating> movieRatings = movieRatingRepository.findAllMovieRatingsOrderByTitleDateDesc();
		model.addAttribute("movieRatings", movieRatings);
		return "movie_rating_list";
		
	}
	
	@PostMapping("/movies/new")
	public String processMovieRatingForm(@Valid MovieRating movieRating, 
			BindingResult result, 
			Model model) {
		if (result.hasErrors()) {
			System.out.println("Errors");
			System.out.println(result);
			return "movie_rating_form";
		}
		movieRating.setDate( new java.util.Date().toString());
		movieRatingRepository.save(movieRating);
		return "movie_rating_show";
	}
	
	@GetMapping("/movies/new")
	public String createMovieRating( Model model) {
		MovieRating movieRating = new MovieRating();
		model.addAttribute("movieRating", movieRating);
		return "movie_rating_form";
	}
	
}