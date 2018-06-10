package ma.ade.kwetter2.utilities;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class GsonEncoder implements Encoder.Text<Object> {
    private Gson gson;

    @Override
    public String encode(Object object) throws EncodeException {
        return gson.toJson(object);

    }

    @Override
    public void init(EndpointConfig ec) {
        gson = Converters.registerOffsetDateTime(new GsonBuilder()).create();
    }

    @Override
    public void destroy() {
        gson = null;
    }

}
