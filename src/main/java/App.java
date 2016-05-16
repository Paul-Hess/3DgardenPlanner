import java.util.Map;
import java.util.HashMap;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;
import static spark.Spark.*;

public class App {
  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    // get
    get("/", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/home.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sign-up", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/log-in", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/user/:user_id", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/user/:user_id/edit", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden-new", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/user/:user_id/gardens", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden/:garden_id", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden/:garden_id/edit-garden", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/plants", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/user/:user_id/plant-new", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/user/:user_id/plants", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/user/:user_id/plant/:plant_id", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    get("/user/:user_id/plant/:plant_id/edit-plant", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout)
    }, new VelocityTemplateEngine());

    post("/sign-up", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/log-in", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/log-out", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/user/:user_id/edit", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/user/:user_id/garden-new", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/user/:user_id/plant-new", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/user/:user_id/garden/:garden_id/edit-garden", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/user/:user_id/plant/:plant_id/edit-plant", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/user/:user_id/search-plants", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/user/:user_id/garden/:garden_id/plant/:plant_id/remove-garden-plant", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/user/:user_id/garden/:garden_id/delete", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/user/:user_id/delete-account", (request, response) -> {
    	response.redirect("");
    	return null;
    });

    post("/user/:user_id/add-plant", (request, response) -> {
    	response.redirect("");
    	return null;
    });


  }

}