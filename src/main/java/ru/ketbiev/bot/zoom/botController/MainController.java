package ru.ketbiev.bot.zoom.botController;

import org.json.JSONObject;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;
import ru.ketbiev.bot.zoom.model.UserToken;
import ru.ketbiev.bot.zoom.repositories.TokenRepositories;
import ru.ketbiev.bot.zoom.view.Convert;
import ru.ketbiev.bot.zoom.zoomController.MeetingController;
import ru.ketbiev.bot.zoom.zoomController.RoomController;
import ru.ketbiev.bot.zoom.zoomController.UserController;
import ru.ketbiev.bot.zoom.zoomapi.Authorization;

public class MainController implements AbilityExtension {

    private final SilentSender silent;
    private final TokenRepositories tokenRepositories;
    private final UserController userController = new UserController();
    private final MeetingController meetingController = new MeetingController();
    private final RoomController roomController = new RoomController();

    public MainController(SilentSender silent, TokenRepositories tr) {
        this.silent = silent;
        this.tokenRepositories = tr;
    }

    public String getToken(Long id) {
        UserToken userToken = tokenRepositories.get(id);
        if (userToken!=null) {
            return userToken.getAccessToken();
        } else {
            Authorization authorization = new Authorization();
            JSONObject tokenObject = authorization.getOAuthToken();
            tokenRepositories.set(id, tokenObject);
            return tokenObject.get("access_token").toString();
        }
    }

    public Reply start() {
        return Reply.of(update -> {
            String url = "https://zoom.us/oauth/authorize?response_type=code&client_id=rdhWtihrRIOCyzShwI9gJQ&redirect_uri=http://localhost:8080/zoom?id="+update.getMessage().getChatId();
            silent.send(url, update.getMessage().getChatId());
        }, update -> update.getMessage().getText().equals("/start"));
    }

    public Reply getUser() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String answer = userController.getUser(token);
            silent.send(Convert.convertUserToString(answer), userId);
        }, update -> update.getMessage().getText().equals("/me"));
    }

    public Reply postMeeting() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String str = meetingController.putMeeting(update.getMessage().getText(), token);
            silent.send(str, userId);
        }, update -> update.getMessage().getText().startsWith("/book"));
    }

    public Reply patchMeeting() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String str = meetingController.updateMeeting(update.getMessage().getText(), token);
            silent.send(str, userId);
        }, update -> update.getMessage().getText().startsWith("/set"));
    }

    public Reply deleteMeeting() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String str = meetingController.deleteMeeting(update.getMessage().getText(), token);
            silent.send(str, userId);
        }, update -> update.getMessage().getText().startsWith("/delete"));
    }

    public Reply getAllMeetingForUser() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String str = meetingController.getAllMeeting(update.getMessage().getText(), token);
            silent.send(str, userId);
        }, update -> update.getMessage().getText().startsWith("/events"));
    }

    public Reply getMeeting() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String str = meetingController.getMeeting(update.getMessage().getText(), token);
            silent.send(Convert.convertMeetingToString(str), userId);
        }, update -> update.getMessage().getText().startsWith("/get"));
    }

    public Reply getRooms() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String str = roomController.getRooms(token);
            silent.send(str, userId);
        }, update -> update.getMessage().getText().startsWith("/rooms"));
    }

}