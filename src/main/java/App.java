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

      if(!(request.session().attribute("authenticated") instanceof User)) {
        boolean notAuth = true;
        model.put("notAuth", notAuth);
      } 

      if(request.session().attribute("authenticated") instanceof User) {
        boolean notAuth = false;
        boolean authenticated = true;
        User currentUser = request.session().attribute("authenticated");
        model.put("notAuth", notAuth);
        model.put("currentUser", currentUser);
        model.put("authenticated", authenticated);
        request.session().attribute("authenticated", currentUser);
      }

      model.put("template", "templates/home.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/sign-up", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
        if(!(request.session().attribute("authenticated") instanceof User)) {
          boolean notAuth = true;
          model.put("notAuth", notAuth);
        }

        if(request.session().attribute("authenticated") instanceof User) {
          boolean notAuth = false;
          User currentUser = request.session().attribute("authenticated");
          model.put("notAuth", notAuth);
          request.session().attribute("authenticated", currentUser);
        }

    	model.put("template", "templates/sign-up.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/log-in", (request, response) -> {
    	Map<String, Object> model = new HashMap<String, Object>();
        
        if(!(request.session().attribute("authenticated") instanceof User)) {
          boolean notAuth = true;
          model.put("notAuth", notAuth);
        }

        if(request.session().attribute("authenticated") instanceof User) {
          boolean notAuth = false;
          User currentUser = request.session().attribute("authenticated");
          model.put("notAuth", notAuth);
          request.session().attribute("authenticated", currentUser);
        }

    	model.put("template", "templates/log-in.vtl");
    	return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/user/:user_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      int year = currentUser.getCreatedAt().getYear() + 1900;
      model.put("year", year);
      if(currentUser.getPlants().size() > 0) {
        model.put("myPlants", currentUser.getPlants());
      }
      request.session().attribute("authenticated", currentUser);

      model.put("template", "templates/user.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/admin", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);

      model.put("template", "templates/admin.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/user/:user_id/edit", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);

      model.put("template", "templates/user-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    get("/user/:user_id/garden-new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);


      model.put("template", "templates/garden-new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/gardens", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);

      model.put("template", "templates/gardens.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden/:garden_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);

      model.put("template", "templates/garden.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden/:garden_id/edit-garden", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);

      model.put("template", "templates/garden-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/plants", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      model.put("plants", Plant.all());

      if(!(request.session().attribute("authenticated") instanceof User)) {
        boolean notAuth = true;
        model.put("notAuth", notAuth);
      }

      if(request.session().attribute("authenticated") instanceof User) {
        boolean notAuth = false;
        boolean authenticated = true;
        User currentUser = request.session().attribute("authenticated");
        model.put("notAuth", notAuth);
        model.put("currentUser", currentUser);
        model.put("authenticated", authenticated);
        request.session().attribute("authenticated", currentUser);
      }

      model.put("template", "templates/plants.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // admin only route
    get("/user/:user_id/plant-new", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);

      model.put("template", "templates/plant-new.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/plant/:plant_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int plantId = Integer.parseInt(request.params("plant_id"));
      Plant currentPlant = Plant.findById(plantId);
      model.put("plant", currentPlant);


      if(!(request.session().attribute("authenticated") instanceof User)) {
        boolean notAuth = true;
        model.put("notAuth", notAuth);
      }

      if(request.session().attribute("authenticated") instanceof User) {
        boolean notAuth = false;
        boolean authenticated = true;
        User currentUser = request.session().attribute("authenticated");
        model.put("notAuth", notAuth);
        model.put("currentUser", currentUser);
        model.put("authenticated", authenticated);
        request.session().attribute("authenticated", currentUser);
      }

      model.put("template", "templates/plant.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    // admin only route
    get("/user/:user_id/plant/:plant_id/edit-plant", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();
      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      int plantId = Integer.parseInt(request.params("plant_id"));
      Plant plant = Plant.findById(plantId);
      model.put("plant", plant);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);

      model.put("template", "templates/plant-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


// post

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

    post("/user/:user_id/log-out", (request, response) -> {
      request.session().removeAttribute("authenticated");
      response.redirect("/");
      return null;
    });

    post("/user/:user_id/edit-name", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      String newName = request.queryParams("new-name");
      String email = request.queryParams("user-email");
      String plainPassword = request.queryParams("user-password");
      String confirmation = request.queryParams("pass-confirmation");
      if(plainPassword.equals(confirmation)) {
        if(currentUser.updateName(email, plainPassword, newName).equals("success") ) {
          User updatedUser = User.findById(id);
          request.session().attribute("authenticated", updatedUser);
          response.redirect("/user/" +  id);
          return null;
        } else {
          Map<String, Object> model = new HashMap<String, Object>();
          boolean authFail = true;
          model.put("authFail", authFail);
          model.put("template", "templates/user-edit.vtl");
          return new ModelAndView(model, layout);
        }
        
      } else {
        Map<String, Object> model = new HashMap<String, Object>();
        boolean error = true;
        model.put("error", error);
        model.put("template", "templates/user-edit.vtl");
        return new ModelAndView(model, layout);
      }

    }, new VelocityTemplateEngine());

    post("/user/:user_id/edit-email", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      String oldEmail = request.queryParams("old-email");
      String plainPassword = request.queryParams("password");
      String confirmation = request.queryParams("password-confirm");
      String newEmail = request.queryParams("new-email");
      if(plainPassword.equals(confirmation)) {
        if(currentUser.updateEmail(oldEmail, plainPassword, newEmail).equals("success")) {
          User updatedUser = User.findById(id);
          request.session().attribute("authenticated", updatedUser);
          response.redirect("/user/" +  id);
          return null;
        } else {
          Map<String, Object> model = new HashMap<String, Object>();
          boolean authFail = true;
          model.put("authFail", authFail);
          model.put("template", "templates/user-edit.vtl");
          return new ModelAndView(model, layout);
        }
      } else {
        Map<String, Object> model = new HashMap<String, Object>();
        boolean error = true;
        model.put("error", error);
        model.put("template", "templates/user-edit.vtl");
        return new ModelAndView(model, layout);
      }

    });

    post("/user/:user_id/edit-password", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);
      String email = request.queryParams("email");
      String oldPassword = request.queryParams("old-password");
      String oldConfirm = request.queryParams("old-confirmation");
      String newPassword = request.queryParams("new-password");
      String newConfirm = request.queryParams("new-confirmation");

      if(oldPassword.equals(oldConfirm) && newPassword.equals(newConfirm)) {
        if(currentUser.updatePassword(email, oldPassword, newPassword).equals("success")) {
          User updatedUser = User.findById(id);
          request.session().attribute("authenticated", updatedUser);
          response.redirect("/user/" +  id);
          return null;
        } else {
          Map<String, Object> model = new HashMap<String, Object>();
          boolean authFail = true;
          model.put("authFail", authFail);
          model.put("template", "templates/user-edit.vtl");
          return new ModelAndView(model, layout);
        }
      } else {
        Map<String, Object> model = new HashMap<String, Object>();
        boolean error = true;
        model.put("error", error);
        model.put("template", "templates/user-edit.vtl");
        return new ModelAndView(model, layout);
      }

    }, new VelocityTemplateEngine());

    post("/user/:user_id/garden-new", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      response.redirect("/user/:user_id/garden/:garden_id");
      return null;
    });

    post("/user/:user_id/plant-new", (request, response) -> {

      String plantName = request.queryParams("plant-name");
      String latinName = request.queryParams("latin-name");
      String zone = request.queryParams("zone");
      int height = Integer.parseInt(request.queryParams("height"));
      int width = Integer.parseInt(request.queryParams("width"));
      String season = request.queryParams("season");
      String icon = request.queryParams("icon-url");

      Plant newPlant = new Plant(plantName, latinName, zone, height, width, season, icon);
      newPlant.save();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      response.redirect("/user/" + currentUser.getId() +"/plant-new");
      return null;
    });

    post("/user/:user_id/garden/:garden_id/edit-garden", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      response.redirect("/user/:user_id/garden/:garden_id");
      return null;
    });

    post("/user/:user_id/plant/:plant_id/edit-plant", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);
      int plantId = Integer.parseInt(request.params("plant_id"));
      Plant plant = Plant.findById(plantId);

      String plantName = request.queryParams("plant-name");
      String latinName = request.queryParams("latin-name");
      String zone = request.queryParams("zone");
      int height = Integer.parseInt(request.queryParams("height"));
      int width = Integer.parseInt(request.queryParams("width"));
      String season = request.queryParams("season");
      String icon = request.queryParams("icon-url");

      plant.update(plantName, latinName, zone, height, width, season, icon);

      String url = String.format("/user/%d/plant/%d", currentUser.getId(), plant.getId());
      response.redirect(url);
      return null;
    });

    post("/user/:user_id/search-plants", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      model.put("template", "templates/search-results.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/user/:user_id/garden/:garden_id/plant/:plant_id/remove-garden-plant", (request, response) -> {
      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      response.redirect("/user/:user_id/garden/:garden_id/edit-garden");
      return null;
    });

    post("/user/:user_id/garden/:garden_id/delete", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      response.redirect("/user/:user_id");
      return null;
    });

    post("/user/:user_id/delete-account", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);
      String password = request.queryParams("account-password");
      String confirmation = request.queryParams("account-confirmation");
      String email = request.queryParams("account-email");
      if(password.equals(confirmation)) {
        if(currentUser.removeAccount(email, password).equals("success")) {
          request.session().removeAttribute("authenticated");
          response.redirect("/sign-up");
          return null;
        } else {
          Map<String, Object> model = new HashMap<String, Object>();
          boolean authFail = true;
          model.put("authFail", authFail);
          model.put("template", "templates/user-edit.vtl");
          return new ModelAndView(model, layout);
        }
      } else {
        Map<String, Object> model = new HashMap<String, Object>();
        boolean error = true;
        model.put("error", error);
        model.put("template", "templates/user-edit.vtl");
        return new ModelAndView(model, layout);
      }

    });

    post("/user/:user_id/plant/:plant_id/add-plant", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      int plantId = Integer.parseInt(request.params("plant_id"));
      Plant currentPlant = Plant.findById(plantId);
      currentUser.addPlant(currentPlant);

      response.redirect("/plant/" + plantId);
      return null;
    });


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
    		authenticated = true;
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
    		authenticated = true;
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
    		authenticated = true;
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
    		authenticated = true;
    	}
    	if(!authenticated) {
    		halt(401, "you must be logged in to view this page!");
    	}
    });

    // ======================================================= //

  }

}