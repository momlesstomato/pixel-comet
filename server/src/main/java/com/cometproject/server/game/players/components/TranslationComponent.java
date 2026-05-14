package com.cometproject.server.game.players.components;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Owns translation behavior inside the player subsystem.
 */
public class TranslationComponent {
    /**
     * Executes translate for this player contract.
     *
     * @param langFrom Lang from supplied by the caller.
     * @param langTo Lang to supplied by the caller.
     * @param text Text supplied by the caller.
     * @return Result produced by the operation.
     * @throws IOException When the operation cannot complete.
     */
    public static String translate(String langFrom, String langTo, String text) throws IOException {
        // INSERT YOU URL HERE
        String urlStr = "https://script.google.com/macros/s/AKfycbyUUufa1QBLRQ1tq8lbYhepi4joMcw1ilorXtjiZfFWIVzp5hU/exec" +
                "?q=" + URLEncoder.encode(text, "UTF-8") +
                "&target=" + langTo +
                "&source=" + langFrom;
        URL url = new URL(urlStr);
        StringBuilder response = new StringBuilder();
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }
}