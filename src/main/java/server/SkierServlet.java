package server;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.regex.*;
import io.swagger.client.model.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "SkierServlet")
public class SkierServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse res) throws ServletException, IOException {
        BufferedReader reader = request.getReader();
        String jsonString = "";
        try {
            for (String line; (line = reader.readLine()) != null; jsonString += line);
        } catch(IOException e) {
            e.printStackTrace();
        }

        res.setContentType("application/json");
        String urlPath = request.getPathInfo();
        if (!isBodyValid(jsonString)) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            res.getWriter().write("{\"message\": \"invalid input\"}");
        }else if (!isUrlValid(urlPath)) {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("{\"message\": \"not found\"}");
        } else {
            res.setStatus(HttpServletResponse.SC_CREATED);
            res.getWriter().write("{\"message\": \"ok\"}");
        }
    }

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        String urlPath = req.getPathInfo();

        if (isUrlValid(urlPath)) {
            res.setStatus(HttpServletResponse.SC_OK);
            if (urlPath.split("/").length == 3) {
                SkierVertical skierVertical = new SkierVertical();
                SkierVerticalResorts skierVerticalResorts = new SkierVerticalResorts();
                skierVerticalResorts.setSeasonID("2019");
                skierVerticalResorts.setTotalVert(5432);
                skierVertical.addResortsItem(skierVerticalResorts);
                res.getWriter().write(new Gson().toJson(skierVertical));
            } else {
                res.getWriter().write("12345");
            }
        } else {
            res.setStatus(HttpServletResponse.SC_NOT_FOUND);
            res.getWriter().write("{\"message\": \"not found\"}");
        }
    }


    private boolean isUrlValid(String urlPath) {
        // urlPath  = "/{resortID}/seasons/{seasonID}/days/{dayID}/skiers/{skierID}"
        // urlPath  = "/{skierID}/vertical"
        Pattern skierApiPattern = Pattern.compile("^/([\\d]+)/seasons/([\\d]+)/days/([\\d]+)/skiers/([\\d]+)$");
        Pattern verticalApiPattern = Pattern.compile("^/([\\d]+)/vertical$");

        Matcher skierApiMatches = skierApiPattern.matcher(urlPath);
        Matcher verticalApiMatches = verticalApiPattern.matcher(urlPath);
        boolean skierApiFind = skierApiMatches.find();
        boolean verticalApiFind = verticalApiMatches.find();
        if (!skierApiFind && !verticalApiFind) {
            return false;
        }
        if (skierApiFind) {
            int day = Integer.parseInt(skierApiMatches.group(3));
            if (day > 366 || day < 1) {
                return false;
            }
        }
        return true;
    }

    private boolean isBodyValid(String jsonStr) {
        if (jsonStr.isEmpty()) {
            return false;
        }
        try {
            Gson gson = new Gson();
            LiftRide liftRide = gson.fromJson(jsonStr, LiftRide.class);
            if (liftRide == null) {
                return false;
            }
        }
        catch(Exception e){
            return false;
        }

        return true;
    }
}
