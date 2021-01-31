package ru.ketbiev.bot.zoom.auxiliary;

public class StaticValues {

    public final static String START_MASSAGE = "/help\n" +
            "/me - returns basic information about me\n" +
            "/book <name> <duration> <time> <date>\n" +
            "/book <name> <duration> <time> <day of week> <reiteration>\n" +
            "/get <id> - returns the selected meeting\n" +
            "/events - returns a list of meetings\n" +
            "/set <id> <field name> <value> - changes parameters\n" +
            "/delete <id> - delete selected meeting\n\n" +
            "example:\n" +
            "/book <new meeting> 30 16:00 2021.02.05 -> creating a meeting\n" +
            "/book <new meeting> 30 16:00 tuesday every-two-week -> creating a meeting\n" +
            "/get 123456789 -> getting a meeting with id 123\n" +
            "/events -> getting all meeting\n" +
            "/set 123456789 agenda this is new agenda -> change of meeting description\n" +
            "/delete 123456789 - delete meeting";

    public final static String[] BODY_PARAM  = {"topic", "type", "start_time", "duration", "timezone", "password", "agenda"};

    public final static String ERROR = "ERROR: check the entered data";
    public final static String BODY_EMPTY = ERROR + "\nExample" +
            "\n/book <name> <duration> <time> <date>" +
            "\n/book <name> <duration> <time> <day of week> <reiteration>";


}
