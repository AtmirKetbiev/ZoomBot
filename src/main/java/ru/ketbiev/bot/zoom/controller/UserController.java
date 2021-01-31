package ru.ketbiev.bot.zoom.controller;

import ru.ketbiev.bot.zoom.config.Const;
import ru.ketbiev.bot.zoom.zoomapi.Handler;

import java.util.HashMap;
import java.util.Map;

public class UserController {
    public String getUser(String token) {
        Handler handler = new Handler();
        String url = String.format(Const.GET_USER_URL, "me");
        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        return handler.getRequest(url, header);
    }
}