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
        User user1 = new User("l@ade.ma", "password", new Profile("Laurens", "Adema", "Ik ben Laurens", "Tilburg", "www.ade.ma", "https://pbs.twimg.com/profile_images/906864070690820097/1-MOnAuY_400x400.jpg"));
        userService.addUser(user1);
        user1 = userService.getUserByEmail(user1.getEmail());

        tweetService.addTweet(new Tweet("Hello World", user1));

        User user2 = new User("a.doe@ade.ma", "password", new Profile("Anna", "Doe", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, adipisci", "Lorem", "www.google.nl", "https://mdbootstrap.com/img/Photos/Avatars/img%20%2810%29.jpg"));
        userService.addUser(user2);
        user2 = userService.getUserByEmail(user2.getEmail());

        tweetService.addTweet(new Tweet("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, adipisci", user2));

        User user3 = new User("j.doe@ade.ma", "password", new Profile("Jonathan", "Doe", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, adipisci", "Lorem", "www.google.nl", "https://mdbootstrap.com/img/Photos/Avatars/img%20%289%29.jpg"));
        userService.addUser(user3);
        user3 = userService.getUserByEmail(user3.getEmail());

        tweetService.addTweet(new Tweet("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, adipisci", user3));

        User user4 = new User("m.portman@ade.ma", "password", new Profile("Maria", "Portman", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, adipisci", "Lorem", "www.google.nl", "https://mdbootstrap.com/img/Photos/Avatars/img%20%2819%29.jpg"));
        userService.addUser(user4);
        user4 = userService.getUserByEmail(user4.getEmail());

        tweetService.addTweet(new Tweet("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, adipisci", user4));

        User user5 = new User("a.smith@ade.ma", "password", new Profile("Anna", "Smith", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, adipisci", "Lorem", "www.google.nl", "https://mdbootstrap.com/img/Photos/Avatars/img%20%2820%29.jpg"));
        userService.addUser(user5);
        user5 = userService.getUserByEmail(user5.getEmail());

        tweetService.addTweet(new Tweet("Lorem ipsum dolor sit amet, consectetur adipisicing elit. Eos, adipisci", user5));
    }
}
