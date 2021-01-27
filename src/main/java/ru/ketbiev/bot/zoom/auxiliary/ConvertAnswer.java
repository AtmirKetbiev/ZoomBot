package ru.ketbiev.bot.zoom.auxiliary;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.ketbiev.bot.zoom.config.Const;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public  class ConvertAnswer {
    public static String convertUserToString(String str)  {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(str, Map.class);
            return "Id: " + map.get("id") +
                    "\nName: " + map.get("first_name") + " " + map.get("last_name")  +
                    "\nEmail: " + map.get("email") +
                    "\nCreated: " + map.get("created_at");
        } catch (JsonProcessingException jsonProcessingException) {
            return str;
        }
    }

    public static String convertMeetingToString(String str) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(str, Map.class);
            if (map.isEmpty()) {
                return "warning!";
            }
            if (map.get("occurrences")==null) {
                return "Id:           " + map.get("id") +
                        "\nName:       " + map.get("topic") +
                        "\nPassword:   " + map.get("password") +
                        "\nStart:      " + map.get("start_time").toString().replace("T", " ").replace("Z", "") +
                        "\nDuration:   " + map.get("duration") +
                        "\nAgenda:     " + map.get("agenda") +
                        "\nHost_email: " + map.get("host_email");
            } else {
                ArrayList list = (ArrayList) map.get("occurrences");
                Map map2 = (Map) list.get(0);
                return "Id:           " + map.get("id") +
                        "\nName:       " + map.get("topic") +
                        "\nPassword:   " + map.get("password") +
                        "\nStart:      " + map2.get("start_time").toString().replace("T", " ").replace("Z", "") +
                        "\nDuration:   " + map2.get("duration") +
                        "\nAgenda:     " + map2.get("agenda") +
                        "\nHost_email: " + map.get("host_email");
            }
        } catch (JsonProcessingException jsonProcessingException) {
            return str;
        }
    }

    public static String convertListMeetingToString(String str) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(str, Map.class);
            ArrayList list = (ArrayList) map.get("meetings");
            if (list.isEmpty()) {
                return "not found meeting!";
            }
            StringBuilder s = new StringBuilder();
            LinkedHashMap vv;
            for (Object o : list) {
                vv = (LinkedHashMap<String, String>)o;
                s.append("\nId:           ").append(vv.get("id")).append("\nName:       ").append(vv.get("topic")).append("\nStart:      ").append(vv.get("start_time").toString().replace("T", " ").replace("Z", "")).append("\n");
            }
            return s.toString();
        } catch (JsonProcessingException jsonProcessingException) {
            return str;
        }
    }

    public static String convertRoomsToString(String str) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(str, Map.class);
            ArrayList list = (ArrayList) map.get("rooms");
            if (list.isEmpty()) {
                return "not found rooms";
            }
            StringBuilder s = new StringBuilder();
            LinkedHashMap vv;
            for (Object o : list) {
                vv = (LinkedHashMap<String, String>)o;
                    s.append("\nId:           ").append(vv.get("id")).append("\nName:       ").append(vv.get("topic")).append("\nStart:      ").append(vv.get("start_time").toString().replace("T", " ").replace("Z", "")).append("\n");
            }
            return s.toString();
        } catch (JsonProcessingException jsonProcessingException) {
            return str;
        }
    }
}
