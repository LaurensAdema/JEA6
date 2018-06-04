package ma.ade.kwetter2;

import ma.ade.kwetter2.domain.Profile;
import ma.ade.kwetter2.domain.Tweet;
import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.service.TweetService;
import ma.ade.kwetter2.service.UserService;

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
        User user1 = new User("laurens1@school.nl", "password", new Profile("Laurens", "Adema", "Ik ben Laurens", "Een stoel", "www.ade.ma", "https://pbs.twimg.com/profile_images/906864070690820097/1-MOnAuY_400x400.jpg"));
        userService.addUser(user1);
        user1 = userService.getUserByEmail(user1.getEmail());
        userService.addUser(new User("laurens2@school.nl", "password", new Profile("Laurens", "Adema", "Ik ben Laurens", "Een stoel", "www.ade.ma", "www.ade.ma/plaatje.jpeg")));
        userService.addUser(new User("laurens3@school.nl", "password", new Profile("Laurens", "Adema", "Ik ben Laurens", "Een stoel", "www.ade.ma", "www.ade.ma/plaatje.jpeg")));
        userService.addUser(new User("laurens4@school.nl", "password", new Profile("Laurens", "Adema", "Ik ben Laurens", "Een stoel", "www.ade.ma", "www.ade.ma/plaatje.jpeg")));
        tweetService.addTweet(new Tweet("Dit is een tweet", user1));
    }
}
