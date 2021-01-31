package ru.ketbiev.bot.zoom.controller;

import org.json.JSONObject;
import ru.ketbiev.bot.zoom.auxiliary.StaticValues;
import ru.ketbiev.bot.zoom.config.Const;
import ru.ketbiev.bot.zoom.auxiliary.InboundTransform;
import ru.ketbiev.bot.zoom.zoomapi.Handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MeetingController {

    public String getMeeting(String str, String token) {
        Handler handler = new Handler();
        String url = String.format(Const.GET_MEETING_URL, str);

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);

        return handler.getRequest(url, header);
    }

    public String getAllMeeting(String token) {
        Handler handler = new Handler();
        String url = String.format(Const.GET_LIST_MEETING_URL, "me");

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);

        return handler.getRequest(url, header);
    }

    public String putMeeting(String str, String token) {
        Handler handler = new Handler();
        String url = String.format(Const.POST_MEETING_URL, "me");

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        header.put("Authorization", "Bearer " + token);

        JSONObject body = getBodyPutMeeting(str);
        if (body == null) {
            return StaticValues.BODY_EMPTY;
        }
        return handler.postRequest(url, header, body);
    }

    public String updateMeeting(String str, String token) {
        Handler handler = new Handler();

        Map<String, String> header = new HashMap<>();
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json");
        header.put("Authorization", "Bearer " + token);

        JSONObject body = new JSONObject();
        JSONObject parameters = getBodyUpdateMeeting(str);
        if (parameters == null) {
            return StaticValues.BODY_EMPTY;
        }
        String url = String.format(Const.GET_MEETING_URL, parameters.get("id_meeting"));
        body.put((String) parameters.get("parameter"), parameters.get("value"));
        return handler.patchRequest(url, header, body);
    }

    public String deleteMeeting(String str, String token) {
        Handler handler = new Handler();
        String url = String.format(Const.DELETE_MEETING_URL, str);

        Map<String, String> header = new HashMap<>();
        header.put("Authorization", "Bearer " + token);

        return handler.deleteRequest(url, header);
    }

    public JSONObject getBodyPutMeeting(String str) {
        String param = str.replace("/book ", "").trim();
        String[] param1 = param.split("<|>");
        if (param1.length < 2) {
            return null;
        }
        String[] arrParam = param1[2].split(" ");
        arrParam[0] = param1[1];
        InboundTransform constValid = new InboundTransform();
        if (arrParam.length == 5) {
            return constValid.getRecurringMeeting(arrParam);
        } else if (arrParam.length == 4) {
            return constValid.getScheduledMeeting(arrParam);
        } else {
            return null;
        }
    }

    public JSONObject getBodyUpdateMeeting(String str) {
        String param = str.replace("/set ", "");
        String[] arrParam = param.split(" ", 3);
        if (arrParam.length < 3 || Arrays.stream(StaticValues.BODY_PARAM).noneMatch(arrParam[1]::contains)) {
            return null;
        }
        JSONObject body = new JSONObject();
        body.put("id_meeting", arrParam[0]);
        body.put("parameter", arrParam[1]);
        body.put("value", arrParam[2]);
        return body;
    }
}