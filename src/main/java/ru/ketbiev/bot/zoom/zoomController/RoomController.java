package ru.ketbiev.bot.zoom.zoomController;

import ru.ketbiev.bot.zoom.Const;
import ru.ketbiev.bot.zoom.zoomapi.Handler;

import java.util.HashMap;
import java.util.Map;

public class RoomController {

    public String getRooms() {
        Handler handler = new Handler();

        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+ Const.TOKEN_ZOOM);

        return handler.getRequest(Const.GET_ROOMS_URL, header);
    }

}
