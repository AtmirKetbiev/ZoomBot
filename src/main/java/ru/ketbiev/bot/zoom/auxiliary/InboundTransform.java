package ru.ketbiev.bot.zoom.auxiliary;

import org.json.JSONObject;

public class InboundTransform {

    public JSONObject getScheduledMeeting(String[] arr) {
        JSONObject body = new JSONObject();
        body.put("topic", arr[0]);
        body.put("type", "2");
        body.put("duration", arr[1]);
        String date = arr[3].replace(".", "-");
        body.put("start_time", date +"T" + arr[2]+":00");
        return body;
    }

    public JSONObject getRecurringMeeting(String[] arr) {
        JSONObject body = new JSONObject();
        body.put("topic", arr[0]);
        body.put("type", "8");
        body.put("duration", arr[1]);
        body.put("start_time", arr[2]+":00");
        JSONObject body2 = new JSONObject();
        body2.put("weekly_days", getWeekly(arr[3]));
        body2.put("type", getTypeRecurrence(arr[4]));
        body2.put("repeat_interval", getIntervalRecurrence(arr[4]));
        body.put("recurrence", body2);
        return body;
    }

    public String getWeekly(String s) {
        return s.replace("sunday", "1")
                .replace("monday", "2")
                .replace("tuesday", "3")
                .replace("wednesday", "4")
                .replace("thursday", "5")
                .replace("friday", "6")
                .replace("saturday", "7");
    }

    public String getTypeRecurrence(String s) {
        String[] arr = s.split("-");
        return arr[arr.length-1].replace("day", "1")
                .replace("week", "2")
                .replace("monthly", "3");
    }

    public String getIntervalRecurrence(String s) {
        String[] arr = s.split("-");
        if (arr.length<2) {
            return null;
        }
        return arr[1].replace("day", "1")
                .replace("week", "1")
                .replace("two", "2")
                .replace("four", "4");
    }
}