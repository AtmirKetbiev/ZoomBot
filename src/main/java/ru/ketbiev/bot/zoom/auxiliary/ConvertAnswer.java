package ru.ketbiev.bot.zoom.auxiliary;

import org.json.JSONArray;
import org.json.JSONObject;

public class ConvertAnswer {
    public static String convertUserToString(String str) {
        if (str.startsWith("Error") || str.isEmpty()) {
            return StaticValues.ERROR;
        }
        JSONObject object = new JSONObject(str);
        return "Id: " + object.get("id") +
                "\nName: " + object.get("first_name") + " " + object.get("last_name") +
                "\nEmail: " + object.get("email") +
                "\nCreated: " + object.get("created_at");
    }

    public static String convertMeetingToString(String str) {
        if (str.startsWith("Error") || str.isEmpty()) {
            return StaticValues.ERROR;
        } else if (str.startsWith(StaticValues.BODY_EMPTY)) {
            return StaticValues.BODY_EMPTY;
        }
        JSONObject object = new JSONObject(str);
        if (!object.has("occurrences")) {
            return "Id:           " + object.get("id") +
                    "\nName:       " + object.get("topic") +
                    "\nPassword:   " + object.get("password") +
                    "\nStart:      " + object.get("start_time") +
                    "\nDuration:   " + object.get("duration") +
                    "\nAgenda:     " + object.get("agenda") +
                    "\nHost_email: " + object.get("host_email");
        } else {
            JSONObject object1 = new JSONObject(object.get("occurrences"));
            return "Id:           " + object1.get("id") +
                    "\nName:       " + object1.get("topic") +
                    "\nPassword:   " + object1.get("password") +
                    "\nStart:      " + object1.get("start_time") +
                    "\nDuration:   " + object1.get("duration") +
                    "\nAgenda:     " + object1.get("agenda") +
                    "\nHost_email: " + object1.get("host_email");
        }
    }

    public static String convertListMeetingToString(String str) {
        if (str.startsWith("Error") || str.isEmpty()) {
            return StaticValues.ERROR;
        } else if (str.startsWith(StaticValues.BODY_EMPTY)) {
            return StaticValues.BODY_EMPTY;
        }
        JSONObject object = new JSONObject(str);
        JSONArray listMeeting = (JSONArray) object.get("meetings");
        return convertListToString(listMeeting);
    }

    public static String convertRoomsToString(String str) {
        if (str.startsWith("Error") || str.isEmpty()) {
            return StaticValues.ERROR;
        }
        JSONObject object = new JSONObject(str);
        JSONArray listMeeting = (JSONArray) object.get("rooms");
        return convertListToString(listMeeting);
    }

    public static String convertListToString(JSONArray listMeeting) {
        StringBuilder answer = new StringBuilder();
        JSONObject jsonMeeting;
        for (Object o : listMeeting) {
            jsonMeeting = (JSONObject) o;
            answer.append("\nId:           ").append(jsonMeeting.get("id"))
                    .append("\nName:       ").append(jsonMeeting.get("topic"))
                    .append("\nStart:      ").append(jsonMeeting.get("start_time"))
                    .append("\n");
        }
        return answer.toString();
    }
}
