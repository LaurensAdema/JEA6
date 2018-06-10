package ma.ade.kwetter2.websocket;

import ma.ade.kwetter2.domain.Tweet;
import ma.ade.kwetter2.domain.User;
import ma.ade.kwetter2.service.UserService;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.ObservesAsync;
import javax.inject.Inject;
import javax.websocket.Session;
import java.util.Collection;
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

    public void updateTweet(@ObservesAsync Tweet toUpdate) {
        sessions.forEach((session, page) -> {
            boolean sendUpdate = false;
            long id = -1;
            if (!page.isEmpty()) {
                try {
                    id = Long.parseLong(page);
                } catch (NumberFormatException ignored) {
                }

                if (toUpdate.getUser().getId() == id) {
                    sendUpdate = true;
                }
            } else {
                if (session.getUserPrincipal() != null && !toUpdate.getUser().getEmail().equalsIgnoreCase(session.getUserPrincipal().getName())) {
                    sendUpdate = userService.isFollowing(session.getUserPrincipal().getName(), toUpdate.getUser().getEmail());
                } else {
                    sendUpdate = true;
                }
            }

            if (sendUpdate) {
                session.getAsyncRemote().sendObject(toUpdate);
            }
        });
    }
}
