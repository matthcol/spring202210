package org.example.movieapi.utils;

public class JsonProvider {
    public static String movieDtoJson(String title, short year) {
        // TODO: find string json builder
        var sb = new StringBuilder("{ \"title\": \"");
        return sb.append(title)
                .append("\", \"year\": ")
                .append(year)
                .append("}")
                .toString();
    }
}
