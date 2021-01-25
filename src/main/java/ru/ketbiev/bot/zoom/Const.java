package ru.ketbiev.bot.zoom;

public class Const {
    public final static String BOT_USERNAME = "YourZoomBot";
    public final static String TOKEN_BOT = "";
    public final static String TOKEN_ZOOM = "";
    //02:11

    public static final String GET_USER_URL = "https://api.zoom.us/v2/users/%s";//{idUser}

    public final static String GET_MEETING_URL = "https://api.zoom.us/v2/meetings/%s";//{idMeeting}
    public final static String GET_LIST_MEETING_URL = "https://api.zoom.us/v2/users/%s/meetings";//{idUser}
    public final static String POST_MEETING_URL = "https://api.zoom.us/v2/users/%s/meetings";//{idUser}
    public final static String DELETE_MEETING_URL = "https://api.zoom.us/v2/meetings/%s";//{idMeeting}

    public final static String GET_ROOMS_URL = "https://api.zoom.us/v2/rooms/";
}
