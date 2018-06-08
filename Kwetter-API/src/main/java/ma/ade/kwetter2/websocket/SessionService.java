package ma.ade.kwetter2.websocket;

import ma.ade.kwetter2.domain.Tweet;
import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.events.LikeEvent;
import ma.ade.kwetter2.events.TweetEvent;
import ma.ade.kwetter2.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ApplicationScoped
public class SessionService {
    private final Map<Session, String> sessions = new HashMap<>();

    @Inject
    private UserService userService;

    void addSession(Session session, String page) {
        sessions.put(session, page);
    }

    void removeSession(Session session) {
        sessions.remove(session);
    }

    void changePage(Session session, String page) {
        sessions.put(session, page);
    }

    private void sendObject(Session session, Object object) {
        try {
            session.getBasicRemote().sendObject(object);
        } catch (IOException | EncodeException e) {
            e.printStackTrace();
        }
    }

    public void tweetCreated(@ObservesAsync TweetEvent event) {
        Tweet tweet = event.getPayload();

        sessions.forEach((session, page) -> {
            User user = userService.getUserByEmail(session.getUserPrincipal().getName());
            sendObject(session, tweet);
        });
    }

    public void tweetLiked(@ObservesAsync LikeEvent event) {
        Tweet tweet = event.getPayload();

        sessions.forEach((session, page) -> {
            User user = userService.getUserByEmail(session.getUserPrincipal().getName());
            sendObject(session, tweet);
        });
    }
}
