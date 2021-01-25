package ru.ketbiev.bot.zoom.zoomController;

import ru.ketbiev.bot.zoom.Const;
import ru.ketbiev.bot.zoom.zoomapi.Handler;

import java.util.HashMap;
import java.util.Map;

public class UserController {


    public String getUser() {
        Handler handler = new Handler();

        String url = String.format(Const.GET_USER_URL, "me");

        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + Const.TOKEN_ZOOM);

        return handler.getRequest(url, header);
    }

}