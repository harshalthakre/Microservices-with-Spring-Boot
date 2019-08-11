package org.studio.moviecatlogservice.resources;

import com.netflix.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.studio.moviecatlogservice.models.CatalogItem;
import org.studio.moviecatlogservice.models.Movie;
import org.studio.moviecatlogservice.models.Rating;
import org.studio.moviecatlogservice.models.UserRating;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping(method = RequestMethod.GET,value = "/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){

        // get all rated movie IDs
        UserRating ratings= restTemplate.getForObject("http://movie-rating-service/ratingsdata/users/"+userId, UserRating.class);

        return ratings.getUserRating().stream().map(rating ->{
            // for each movieId, call movieInfo service and get all details
           Movie movie= restTemplate.getForObject("http://movie-info-service/movies/"+rating.getMovieId(), Movie.class);
         /* Movie movie=webClientBuilder.build()
                  .get()
                  .uri("http://localhost:8082/movies/"+rating.getMovieId())
                  .retrieve()
                  .bodyToMono(Movie.class)
                  .block();*/
            // put them all together
            return new CatalogItem(movie.getName(),"DESC",rating.getRating());
        }).collect(Collectors.toList());


    }
}
