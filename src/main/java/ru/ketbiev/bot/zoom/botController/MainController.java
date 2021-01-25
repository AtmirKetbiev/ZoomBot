package ru.ketbiev.bot.zoom.botController;

import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.abilitybots.api.util.AbilityExtension;
import ru.ketbiev.bot.zoom.view.Convert;
import ru.ketbiev.bot.zoom.zoomController.MeetingController;
import ru.ketbiev.bot.zoom.zoomController.RoomController;
import ru.ketbiev.bot.zoom.zoomController.UserController;

public class MainController implements AbilityExtension {

    private final SilentSender silent;

    public MainController(SilentSender silent) {
        this.silent = silent;
    }

    public Reply start() {
        return Reply.of(update -> {
            silent.send("Start message", update.getMessage().getChatId());
        }, update -> update.getMessage().getText().equals("/start"));
    }

    public Reply getUser() {
        return Reply.of(update -> {
            UserController userController = new UserController();
            String str = userController.getUser();
            silent.send(Convert.convertUserToString(str), update.getMessage().getChatId());
        }, update -> update.getMessage().getText().equals("/me"));
    }

    public Reply postMeeting() {
        return Reply.of(update -> {
            MeetingController meetingController = new MeetingController();
            String str = meetingController.putMeeting(update.getMessage().getText());
            silent.send(str, update.getMessage().getChatId());
        }, update -> update.getMessage().getText().startsWith("/book"));
    }

    public Reply patchMeeting() {
        return Reply.of(update -> {
            MeetingController meetingController = new MeetingController();
            String str = meetingController.updateMeeting(update.getMessage().getText());
            silent.send(str, update.getMessage().getChatId());
        }, update -> update.getMessage().getText().startsWith("/set"));
    }

    public Reply deleteMeeting() {
        return Reply.of(update -> {
            MeetingController meetingController = new MeetingController();
            String str = meetingController.deleteMeeting(update.getMessage().getText());
            silent.send(str, update.getMessage().getChatId());
        }, update -> update.getMessage().getText().startsWith("/delete"));
    }

    public Reply getAllMeetingForUser() {
        return Reply.of(update -> {
            MeetingController meetingController = new MeetingController();
            String str = meetingController.getAllMeeting(update.getMessage().getText());
            silent.send(str, update.getMessage().getChatId());
        }, update -> update.getMessage().getText().startsWith("/events"));
    }

    public Reply getMeeting() {
        return Reply.of(update -> {
            MeetingController meetingController = new MeetingController();
            String str = meetingController.getMeeting(update.getMessage().getText());
            silent.send(Convert.convertMeetingToString(str), update.getMessage().getChatId());
        }, update -> update.getMessage().getText().startsWith("/get"));
    }

    // /rooms
    public Reply getRooms() {
        return Reply.of(update -> {
            RoomController roomController = new RoomController();
            String str = roomController.getRooms();
            silent.send(str, update.getMessage().getChatId());
        }, update -> update.getMessage().getText().startsWith("/rooms"));
    }

}