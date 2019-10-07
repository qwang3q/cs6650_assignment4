package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import com.google.gson.Gson;
import io.swagger.client.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ResortServlet")
public class ResortServlet extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        BufferedReader reader = req.getReader();
        String jsonString = "";
        try {
            for (String line; (line = reader.readLine()) != null; jsonString += line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        res.setContentType("application/json");
        String urlPath = req.getPathInfo();

        if (!isBodyValid(jsonString)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"message\": \"invalid input\"}");
        } else if (!isUrlValid(urlPath)) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("{\"message\": \"not found\"}");
        } else {
            res.setStatus(HttpServletResponse.SC_OK);
            res.getWriter().write("{\"message\": \"ok\"}");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        String urlPath = req.getPathInfo();

        if (urlPath == null || urlPath.isEmpty() || urlPath.equals("/")) { // resorts
            res.setStatus(HttpServletResponse.SC_OK);
            List<ResortsList> resortsList = new ArrayList<>();
            ResortsList resorts = new ResortsList();
            ResortsListResorts r = new ResortsListResorts();
            r.setResortName("Alex");
            r.setResortID(1);
            resorts.addResortsItem(r);
            resortsList.add(resorts);
            res.getWriter().write(new Gson().toJson(resortsList));
        } else if (isUrlValid(urlPath)) { // seasons
            res.setStatus(HttpServletResponse.SC_CREATED);
            SeasonsList seasonsList = new SeasonsList();
            seasonsList.addSeasonsItem("2019");
            res.getWriter().write(new Gson().toJson(seasonsList));
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("{\"message\": \"not found\"}");
        }
    }

    private boolean isUrlValid(String urlPath) {
        // urlPath  = "/1/seasons"
        // urlPath = "/"
        Pattern resortApiPattern = Pattern.compile("^/([\\d]+)/seasons$");
        Pattern seasonApiPattern = Pattern.compile("^/?$");
        return (resortApiPattern.matcher(urlPath).matches() || seasonApiPattern.matcher(urlPath).matches());
    }

    private boolean isBodyValid(String jsonStr) {
        if (jsonStr.isEmpty()) {
            return false;
        }

        try {
            Gson gson = new Gson();
            Season season = gson.fromJson(jsonStr, Season.class);
            if (season == null || String.valueOf(season.getYear()).length() != 4)
                return false;
        }
        catch(Exception e){
            return false;
        }

        return true;
    }
}