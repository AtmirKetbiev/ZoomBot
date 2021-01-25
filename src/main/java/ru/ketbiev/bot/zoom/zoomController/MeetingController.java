package ru.ketbiev.bot.zoom.zoomController;

import org.json.JSONObject;
import ru.ketbiev.bot.zoom.Const;
import ru.ketbiev.bot.zoom.zoomapi.Handler;

import java.util.HashMap;
import java.util.Map;

public class MeetingController {


    public String getMeeting(String str) {
        Handler handler = new Handler();

        String url = String.format(Const.GET_MEETING_URL, str.replace("/get ", ""));

        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + Const.TOKEN_ZOOM);

        return handler.getRequest(url , header);
    }

    public String getAllMeeting(String str) {
        Handler handler = new Handler();

        String url = String.format(Const.GET_LIST_MEETING_URL, "me");

        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+Const.TOKEN_ZOOM);

        return handler.getRequest(url, header);
    }

    public String putMeeting(String string) {
        Handler handler = new Handler();

        String url = String.format(Const.POST_MEETING_URL, "me");

        Map<String,String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        header.put("Authorization", "Bearer "+Const.TOKEN_ZOOM);

        JSONObject body = new JSONObject();
        body.put("topic", "new");
        body.put("type", "2");
        body.put("start_time", "2021-08-30T22:00:00Z");
        body.put("duration", "30");
        body.put("timezone", "America/New_York");
        body.put("password", "123q");
        body.put("agenda", "ooo");

        return handler.postRequest(url, header, body);
    }

    public String updateMeeting(String str) {
        Map<String,String> header = new HashMap<>();

        String url = String.format(Const.GET_MEETING_URL, "89138339144");

        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        header.put("Authorization", "Bearer "+Const.TOKEN_ZOOM);
        Handler handler = new Handler();

        JSONObject body = new JSONObject();
        body.put("topic", "new name2");
        return handler.patchRequest(url, header, body);
    }

    public String deleteMeeting(String str) {
        Handler handler = new Handler();

        String url = String.format(Const.DELETE_MEETING_URL, str.replace("/delete ", ""));

        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+Const.TOKEN_ZOOM);

        return handler.deleteRequest(url, header);
    }
}