package ru.ketbiev.bot.zoom.controller;

import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;
import ru.ketbiev.bot.zoom.auxiliary.StaticValues;
import ru.ketbiev.bot.zoom.config.Const;
import ru.ketbiev.bot.zoom.repositories.TokenRepositories;
import ru.ketbiev.bot.zoom.auxiliary.ConvertAnswer;

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
//        UserToken userToken = tokenRepositories.get(id);
//        if (userToken!=null) {
//            return userToken.getAccessToken();
//        } else {
//            Authorization authorization = new Authorization();
//            JSONObject tokenObject = authorization.getOAuthToken();
//            tokenRepositories.set(id, tokenObject);
//            return tokenObject.get("access_token").toString();
//        }
        return Const.TOKEN_ZOOM;
    }

    public Reply start() {
        return Reply.of(update -> {
//            tokenRepositories.delete(update.getMessage().getChatId());
//            Authorization authorization = new Authorization();
//            JSONObject tokenObject = authorization.getOAuthToken();
//            tokenRepositories.set(update.getMessage().getChatId(), tokenObject);
            silent.send(StaticValues.START_MASSAGE, update.getMessage().getChatId());
        }, update -> update.getMessage().getText().equals("/start"));
    }

    public Reply getUser() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String answer = userController.getUser(token);
            silent.send(ConvertAnswer.convertUserToString(answer), userId);
        }, update -> update.getMessage().getText().equals("/me"));
    }

    public Reply getMeeting() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String message = update.getMessage().getText()
                    .replace("/get", "")
                    .replace(" ", "");
            String answer = meetingController.getMeeting(message, token);
            silent.send(ConvertAnswer.convertMeetingToString(answer), userId);
        }, update -> update.getMessage().getText().startsWith("/get"));
    }

    public Reply getAllMeetingForUser() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String answer = meetingController.getAllMeeting(update.getMessage().getText(), token);
            silent.send(ConvertAnswer.convertListMeetingToString(answer), userId);
        }, update -> update.getMessage().getText().equals("/events"));
    }

    public Reply postMeeting() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String answer = meetingController.putMeeting(update.getMessage().getText(), token);
            silent.send(ConvertAnswer.convertMeetingToString(answer), userId);
        }, update -> update.getMessage().getText().startsWith("/book"));
    }

    public Reply patchMeeting() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String answer = meetingController.updateMeeting(update.getMessage().getText(), token);
            silent.send(answer, userId);
        }, update -> update.getMessage().getText().startsWith("/set"));
    }

    public Reply deleteMeeting() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String message = update.getMessage().getText()
                    .replace("/delete", "")
                    .replace(" ", "");
            String answer = meetingController.deleteMeeting(message, token);
            silent.send(answer, userId);
        }, update -> update.getMessage().getText().startsWith("/delete"));
    }

    public Reply getRooms() {
        return Reply.of(update -> {
            Long userId = update.getMessage().getChatId();
            String token = getToken(userId);
            String answer = roomController.getRooms(token);
            silent.send(ConvertAnswer.convertRoomsToString(answer), userId);
        }, update -> update.getMessage().getText().equals("/rooms"));
    }

}