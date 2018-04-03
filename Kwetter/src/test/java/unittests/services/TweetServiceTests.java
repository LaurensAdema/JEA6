package unittests.services;

import domain.Profile;
import domain.Tweet;
import domain.User;
import org.junit.Before;
import org.junit.Test;
import service.TweetService;
import service.UserService;

public class TweetServiceTests
{
    private UserService userService;
    private TweetService tweetService;
    
    @Before
    public void setUp()
    {
        userService = new UserService();
        tweetService = new TweetService();
    }
    
    @Test
    public void AddTweetTest() 
    {
        User user1 = new User("laurens1@school.nl", "password", new Profile("Laurens", "Adema", "Ik ben Laurens", "Een stoel", "www.ade.ma", "www.ade.ma/plaatje.jpeg"));
        userService.addUser(user1);
        tweetService.addTweet(new Tweet("Dit is een tweet", user1));
    }
}
