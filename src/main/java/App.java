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
    	model.put("template", "templates/sign-up.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/log-in", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("template", "templates/log-in.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // before filters ==================================== //

    before("/user/:user_id", (request, response) -> {
    	boolean authenticated = false;
    	int userId = Integer.parseInt(request.params("user_id"));
    	User currentUser = User.findById(userId);
    	User loggedInUser = request.session().attribute("authenticated");
    	if(currentUser.equals(loggedInUser)) {
    		authenticated = true;
    	}
    	if(!authenticated) {
    		halt(401, "you must be logged in to view this page!");
    	}
    });

    before("/user/:user_id/edit", (request, response) -> {
    	boolean authenticated = false;
    	int userId = Integer.parseInt(request.params("user_id"));
    	User currentUser = User.findById(userId);
    	User loggedInUser = request.session().attribute("authenticated");
    	if(currentUser.equals(loggedInUser)) {
    		authenticated = true;
    	}
    	if(!authenticated) {
    		halt(401, "you must be logged in to view this page!");
    	}
    });

    before("/user/:user_id/admin", (request, response) -> {
    	boolean authenticated = false;
    	int userId = Integer.parseInt(request.params("user_id"));
    	User currentUser = User.findById(userId);
    	User loggedInUser = request.session().attribute("authenticated");
    	if(currentUser.equals(loggedInUser)) {
    		if(currentUser.isAdmin()) {
    			authenticated = true;
    		}
    	}
    	if(!authenticated) {
    		halt(401, "you must be logged in to view this page!");
    	}
    });

    before("/user/:user_id/plant-new", (request, response) -> {
    	boolean authenticated = false;
    	int userId = Integer.parseInt(request.params("user_id"));
    	User currentUser = User.findById(userId);
    	User loggedInUser = request.session().attribute("authenticated");
    	if(currentUser.equals(loggedInUser)) {
    		if(currentUser.isAdmin()) {
    			authenticated = true;
    		}
    	}
    	if(!authenticated) {
    		halt(401, "you must be logged in to view this page!");
    	}
    });

    before("/user/:user_id/plant/:plant_id/edit-plant", (request, response) -> {
    	boolean authenticated = false;
    	int userId = Integer.parseInt(request.params("user_id"));
    	User currentUser = User.findById(userId);
    	User loggedInUser = request.session().attribute("authenticated");
    	if(currentUser.equals(loggedInUser)) {
    		if(currentUser.isAdmin()) {
    			authenticated = true;
    		}
    	}
    	if(!authenticated) {
    		halt(401, "you must be logged in to view this page!");
    	}
    });


    before("/user/:user_id/garden-new", (request, response) -> {
    	boolean authenticated = false;
    	int userId = Integer.parseInt(request.params("user_id"));
    	User currentUser = User.findById(userId);
    	User loggedInUser = request.session().attribute("authenticated");
    	if(currentUser.equals(loggedInUser)) {
    		if(currentUser.isAdmin()) {
    			authenticated = true;
    		}
    	}
    	if(!authenticated) {
    		halt(401, "you must be logged in to view this page!");
    	}
    });

    before("/user/:user_id/gardens", (request, response) -> {
    	boolean authenticated = false;
    	int userId = Integer.parseInt(request.params("user_id"));
    	User currentUser = User.findById(userId);
    	User loggedInUser = request.session().attribute("authenticated");
    	if(currentUser.equals(loggedInUser)) {
    		if(currentUser.isAdmin()) {
    			authenticated = true;
    		}
    	}
    	if(!authenticated) {
    		halt(401, "you must be logged in to view this page!");
    	}
    });

    before("/user/:user_id/garden/:garden_id", (request, response) -> {
    	boolean authenticated = false;
    	int userId = Integer.parseInt(request.params("user_id"));
    	User currentUser = User.findById(userId);
    	User loggedInUser = request.session().attribute("authenticated");
    	if(currentUser.equals(loggedInUser)) {
    		if(currentUser.isAdmin()) {
    			authenticated = true;
    		}
    	}
    	if(!authenticated) {
    		halt(401, "you must be logged in to view this page!");
    	}
    });

    before("/user/:user_id/garden/:garden_id/edit-garden", (request, response) -> {
    	boolean authenticated = false;
    	int userId = Integer.parseInt(request.params("user_id"));
    	User currentUser = User.findById(userId);
    	User loggedInUser = request.session().attribute("authenticated");
    	if(currentUser.equals(loggedInUser)) {
    		if(currentUser.isAdmin()) {
    			authenticated = true;
    		}
    	}
    	if(!authenticated) {
    		halt(401, "you must be logged in to view this page!");
    	}
    });




    // ======================================================= //

    get("/user/:user_id", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	int userId = Integer.parseInt(request.params("user_id"));
    	User currentUser = User.findById(userId);
    	User loggedInUser = request.session().attribute("authenticated");

    	model.put("authenticated", true);
    	model.put("currentUser", currentUser);

    	model.put("template", "templates/user.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/admin", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("template", "templates/admin.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/user/:user_id/edit", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("template", "templates/user-edit.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden-new", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("template", "templates/garden-new.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/gardens", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("template", "templates/gardens.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden/:garden_id", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("template", "templates/garden.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden/:garden_id/edit-garden", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("template", "templates/garden-edit.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/plants", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("plants", Plant.all());
    	model.put("template", "templates/plants.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // admin only route
    get("/user/:user_id/plant-new", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("template", "templates/plant-new.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/plant/:plant_id", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("template", "templates/plant.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // admin only route
    get("/user/:user_id/plant/:plant_id/edit-plant", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/sign-up", (request, response) -> {
    	boolean error = false;
    	String email = request.queryParams("user-email");
    	String plainPassword = request.queryParams("user-password");
    	String confirmation = request.queryParams("pass-confirmation");
    	String name = request.queryParams("user-name");
    	if(plainPassword.equals(confirmation)) {
    		User newUser = new User(name, email, plainPassword);
    		newUser.save();
    		response.redirect("/log-in");
    		return null;
    	} else {
    		Map<String, Object> model = new HashMap<String, Object>();
				error = true;
    		model.put("error", error);
    		model.put("template", "templates/sign-up.vtl");
    		return new ModelAndView(model, layout);
    	}  	
    }, new VelocityTemplateEngine());

    post("/log-in", (request, response) -> {
    	
    	String email = request.queryParams("user-email");
    	String plainPassword = request.queryParams("user-password");
    	String confirmation = request.queryParams("pass-confirmation");

    	if(plainPassword.equals(confirmation)) {
    		if(User.checkUserAuth(email, plainPassword)) {
    			User currentUser = User.findByEmail(email);	
    			request.session().attribute("authenticated", currentUser);
    			response.redirect("/user/" + currentUser.getId());
    			return null;
    		} else {
    			Map<String, Object> model = new HashMap<String, Object>();
    			boolean authFail = true;
	    		model.put("authFail", authFail);
	    		model.put("template", "templates/log-in.vtl");
	    		return new ModelAndView(model, layout);
    		}
    	} else {
     		Map<String, Object> model = new HashMap<String, Object>();
     		boolean error = true;
    		model.put("error", error);
    		model.put("template", "templates/log-in.vtl");
    		return new ModelAndView(model, layout);
    	}
    }, new VelocityTemplateEngine());

    post("/log-out", (request, response) -> {
    	response.redirect("/");
    	return null;
    });

    post("/user/:user_id/edit", (request, response) -> {
    	response.redirect("/user/:user_id");
    	return null;
    });

    post("/user/:user_id/garden-new", (request, response) -> {
    	response.redirect("/user/:user_id/garden/:garden_id");
    	return null;
    });

    post("/user/:user_id/plant-new", (request, response) -> {
    	response.redirect("user/:user_id/plant-new");
    	return null;
    });

    post("/user/:user_id/garden/:garden_id/edit-garden", (request, response) -> {
    	response.redirect("/user/:user_id/garden/:garden_id");
    	return null;
    });

    post("/user/:user_id/plant/:plant_id/edit-plant", (request, response) -> {
    	response.redirect("/user/:user_id/plant/:plant_id");
    	return null;
    });

    post("/user/:user_id/search-plants", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
    	model.put("template", "templates/search-results.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/user/:user_id/garden/:garden_id/plant/:plant_id/remove-garden-plant", (request, response) -> {
    	response.redirect("/user/:user_id/garden/:garden_id/edit-garden");
    	return null;
    });

    post("/user/:user_id/garden/:garden_id/delete", (request, response) -> {
    	response.redirect("/user/:user_id");
    	return null;
    });

    post("/user/:user_id/delete-account", (request, response) -> {
    	response.redirect("/sign-up");
    	return null;
    });

    post("/user/:user_id/add-plant", (request, response) -> {
    	response.redirect("/user/:user_id/add-plant");
   		return null;
    });


  }

}