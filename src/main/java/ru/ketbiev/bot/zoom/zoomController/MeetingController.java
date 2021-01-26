package ru.ketbiev.bot.zoom.zoomController;

import org.json.JSONObject;
import ru.ketbiev.bot.zoom.Const;
import ru.ketbiev.bot.zoom.zoomapi.Handler;

import java.util.HashMap;
import java.util.Map;

public class MeetingController {

    public String getMeeting(String str, String token) {
        Handler handler = new Handler();
        String url = String.format(Const.GET_MEETING_URL, str.replace("/get ", ""));
        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        return handler.getRequest(url , header);
    }

    public String getAllMeeting(String str, String token) {
        Handler handler = new Handler();
        String url = String.format(Const.GET_LIST_MEETING_URL, "me");
        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);
        return handler.getRequest(url, header);
    }

    public String putMeeting(String string, String token) {
        Handler handler = new Handler();
        String url = String.format(Const.POST_MEETING_URL, "me");

        Map<String,String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        header.put("Authorization", "Bearer "+ token);

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

    public String updateMeeting(String str, String token) {
        Map<String,String> header = new HashMap<>();

        String url = String.format(Const.GET_MEETING_URL, "89138339144");

        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        header.put("Authorization", "Bearer "+token);
        Handler handler = new Handler();

        JSONObject body = new JSONObject();
        body.put("topic", "new name2");
        return handler.patchRequest(url, header, body);
    }

    public String deleteMeeting(String str, String token) {
        Handler handler = new Handler();
        String url = String.format(Const.DELETE_MEETING_URL, str.replace("/delete ", ""));
        Map<String,String> header = new HashMap<>();
        header.put("Authorization", "Bearer "+token);
        return handler.deleteRequest(url, header);
    }
}