package com.techhype.digitalinventory.constants;

public class ServerMessage {
    public static String OK = "Successful";
    public static String INTERNAL_SERVER_ERROR = "Internal Server Error";
    public static String NOT_FOUND = "Not Found";
    public static String UNAUTHORIZED = "Unauthorized";
    public static String BAD_REQUEST = "Bad Request";

    public static String Created(String ref) {
        return buildMessage("created", ref);
    }

    public static String Updated(String ref) {
        return buildMessage("updated", ref);
    }

    public static String Deleted(String ref) {
        return String.format("%s has been deleted.", ref);
    }

    private static String buildMessage(String action, String ref) {
        return String.format("%s %s successfully.", ref, action);
    }
}
