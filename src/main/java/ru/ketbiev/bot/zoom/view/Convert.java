package ru.ketbiev.bot.zoom.view;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.Map;

public  class Convert {
    public static String convertUserToString(String str)  {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(str, Map.class);
            return "Id: " + map.get("id") +
                    "\nName: " + map.get("first_name") + " " + map.get("last_name")  +
                    "\nEmail: " + map.get("email") +
                    "\nCreated: " + map.get("created_at");
        } catch (JsonProcessingException jsonProcessingException) {
            return "Ooops";
        }

    }


    public static String convertMeetingToString(String str) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Map map = mapper.readValue(str, Map.class);
            return "Id:           " + map.get("id") +
                    "\nName:       " + map.get("topic") +
                    "\nPassword:   " + map.get("password") +
                    "\nStart:      " + map.get("start_time") +
                    "\nDuration:   " + map.get("duration") +
                    "\nAgenda:     " + map.get("agenda") +
                    "\nHost_email: " + map.get("host_email");
        } catch (JsonProcessingException jsonProcessingException) {
            return "Ooops";
        }
    }

}
