package ma.ade.kwetter2.websocket;

import ma.ade.kwetter2.authentication.UseAuthentication;
import ma.ade.kwetter2.utilities.GsonEncoder;

import javax.inject.Inject;
import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/events/{page}",
        encoders = { GsonEncoder.class })
@UseAuthentication
public class WebSocketController {
    @Inject
    private SessionService sessionService;

    @OnOpen
    public void open(Session session, @PathParam("page") final String page) {
        sessionService.addSession(session, page);
    }

    @OnClose
    public void close(Session session) {
        sessionService.removeSession(session);
    }

    @OnError
    public void onError(Throwable error) {
    }

    @OnMessage
    public void changePage(String message, Session session) {
        sessionService.changePage(session, message);
    }
}
