//package  com.xyz
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.handlebars.HandlebarsTemplateEngine;
import java.util.Map;
import static spark.Spark.*;

public class App {

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return 4567; //return default port if heroku-port isn't set (i.e. on localhost)
    }

    public static void main(String[] args) {
        port(getHerokuAssignedPort());
        staticFileLocation("/public");

        get("/", (request, response) -> { //request for route happens at this location
            Map<String, Object> model = new HashMap<String, Object>(); // new model is made to store information
            return new ModelAndView(model, "index.hbs"); // assemble individual pieces and render
        }, new HandlebarsTemplateEngine()); //

        get("/matatu", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "matatu.hbs");
        }, new HandlebarsTemplateEngine());

        get("/route", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            String locationA = request.queryParams("locationA");
            String locationB = request.queryParams("locationB");
            model.put("locationA", locationA);
            model.put("locationB", locationB);
            return new ModelAndView(model, "route.hbs");
        }, new HandlebarsTemplateEngine());


        get("/about", (request, response) -> {
            Map<String, Object> model = new HashMap<String, Object>();
            return new ModelAndView(model, "about.hbs");
        }, new HandlebarsTemplateEngine());
    }
}
