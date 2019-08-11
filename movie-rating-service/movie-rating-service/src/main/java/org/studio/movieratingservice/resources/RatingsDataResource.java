package org.studio.movieratingservice.resources;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.studio.movieratingservice.models.Rating;
import org.studio.movieratingservice.models.UserRating;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/ratingsdata")
public class RatingsDataResource {

    @RequestMapping("/{movieId}")
    public Rating getRating(@PathVariable String movieId){
        return new Rating(movieId,4);
    }

    @RequestMapping("users/{userId}")
    public UserRating getRatingByUser(@PathVariable String userId){
        UserRating userRating=new UserRating();
        userRating.setUserRating(Arrays.asList(
                new Rating("1234",4),
                new Rating("5678",3)
        ));
        return userRating;
    }
}
