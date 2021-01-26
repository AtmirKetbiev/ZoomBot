package ru.ketbiev.bot.zoom.zoomController;

import ru.ketbiev.bot.zoom.Const;
import ru.ketbiev.bot.zoom.zoomapi.Handler;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    public String getUser(String token) {
        String url = String.format(Const.GET_USER_URL, "me");
        Handler handler = new Handler();
        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        return handler.getRequest(url, header);
    }
}