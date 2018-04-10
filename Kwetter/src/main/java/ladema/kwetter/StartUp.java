package ladema.kwetter;

import ladema.kwetter.domain.Profile;
import ladema.kwetter.domain.Tweet;
import ladema.kwetter.domain.User;
import ladema.kwetter.service.TweetService;
import ladema.kwetter.service.UserService;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

@Singleton
@Startup
public class StartUp {
    @Inject
    private UserService userService;
    @Inject
    private TweetService tweetService;

    @PostConstruct
    private void intData(){
        User user1 = new User("laurens1@school.nl", "password", new Profile("Laurens", "Adema", "Ik ben Laurens", "Een stoel", "www.ade.ma", "www.ade.ma/plaatje.jpeg"));
        userService.addUser(user1);
        System.out.println("USER 1 ID: " + user1.getId());
        userService.addUser(new User("laurens2@school.nl", "password", new Profile("Laurens", "Adema", "Ik ben Laurens", "Een stoel", "www.ade.ma", "www.ade.ma/plaatje.jpeg")));
        userService.addUser(new User("laurens3@school.nl", "password", new Profile("Laurens", "Adema", "Ik ben Laurens", "Een stoel", "www.ade.ma", "www.ade.ma/plaatje.jpeg")));
        userService.addUser(new User("laurens4@school.nl", "password", new Profile("Laurens", "Adema", "Ik ben Laurens", "Een stoel", "www.ade.ma", "www.ade.ma/plaatje.jpeg")));
        tweetService.addTweet(new Tweet("Dit is een tweet", user1));
    }
}
