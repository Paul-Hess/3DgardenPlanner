import java.util.Map;
import java.util.HashMap;
import java.util.List;
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

      model.put("plants", Plant.all());

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

      if(currentUser.getGardens().size() > 0) {
        model.put("gardens", currentUser.getGardens());
      }

      model.put("template", "templates/gardens.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden/:garden_id", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);
      int gardenId = Integer.parseInt(request.params("garden_id"));
      Garden currentGarden = Garden.findById(gardenId);
      model.put("garden", currentGarden);

      model.put("template", "templates/garden.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden/:garden_id/edit-garden", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);

      int gardenId = Integer.parseInt(request.params("garden_id"));
      Garden currentGarden = Garden.findById(gardenId);
      model.put("garden", currentGarden);

      model.put("allPlants", Plant.all());

      model.put("template", "templates/garden-edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/user/:user_id/garden/:garden_id/add-plant", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      model.put("currentUser", currentUser);
      request.session().attribute("authenticated", currentUser);

      int gardenId = Integer.parseInt(request.params("garden_id"));
      Garden currentGarden = Garden.findById(gardenId);
      model.put("currentGarden", currentGarden);
      int nextPositionNorth = 0;
      int nextPositionWest = 0;

      if(currentGarden.getPlants().size() == 0) {
        model.put("plants", Plant.all());
        model.put("nextPositionNorth", nextPositionNorth);
        model.put("nextPositionWest", nextPositionWest);
      } else if (currentGarden.checkAvailableGround() > 0) {
        nextPositionNorth = currentGarden.getNextPositionNorth();
        nextPositionWest = currentGarden.getNextPositionWest();
        model.put("nextPositionNorth", nextPositionNorth);
        model.put("nextPositionWest", nextPositionWest);
        model.put("plants", currentGarden.findByAvailableGround(currentGarden.checkAvailableGround()));
      }

      model.put("template", "templates/garden-add-plant.vtl");
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

      String name = request.queryParams("garden-name");
      int length = Integer.parseInt(request.queryParams("length"));
      int width = Integer.parseInt(request.queryParams("width"));
      Garden newGarden = new Garden(name, length, width, id);
      newGarden.save();


      String responseUrl = String.format("/user/%d/garden/%d/add-plant", id, newGarden.getId());
      response.redirect(responseUrl);
      return null;
    }, new VelocityTemplateEngine());

    post("/user/:user_id/garden/:garden_id/add-plant", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      int gardenId = Integer.parseInt(request.params("garden_id"));
      Garden currentGarden = Garden.findById(gardenId);
      String thisGet = String.format("/user/%d/garden/%d/add-plant", currentUser.getId(), currentGarden.getId());
      String gardenPage = String.format("/user/%d/garden/%d", currentUser.getId(), currentGarden.getId());

      if(currentGarden.checkAvailableGround() < 1) {
        response.redirect(gardenPage);
      } else {
        int plantId = Integer.parseInt(request.queryParams("plant"));
        Plant currentPlant = Plant.findById(plantId);
        currentGarden.addPlant(currentPlant);
        if(currentGarden.checkAvailableGround() < 1) {
          response.redirect(gardenPage);
        } else {
          response.redirect(thisGet);
        }
      }
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

      int gardenId = Integer.parseInt(request.params("garden_id"));
      Garden currentGarden = Garden.findById(gardenId);

      int oldPlantId = Integer.parseInt(request.queryParams("to-replace"));
      Plant oldPlant = Plant.findById(oldPlantId);
      int newPlantId = Integer.parseInt(request.queryParams("replacement"));
      Plant newPlant = Plant.findById(newPlantId);
      currentGarden.updateGardenPlant(oldPlant, newPlant, currentGarden.getPlants().indexOf(oldPlant));

      String responseUrl = String.format("/user/%d/garden/%d", id, gardenId);
      response.redirect(responseUrl);
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

    post("/plants/search-name", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      if(request.session().attribute("authenticated") instanceof User) {
        request.session().attribute("authenticated", request.session().attribute("authenticated"));
      }

      String search = request.queryParams("search-name");
      List<Plant> searchResults = Plant.findByName(search);
      model.put("searchResults", searchResults);

      model.put("template", "templates/search-results.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/plants/search-latin", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      if(request.session().attribute("authenticated") instanceof User) {
        request.session().attribute("authenticated", request.session().attribute("authenticated"));
      }

      String search = request.queryParams("search-latin");
      List<Plant> searchResults = Plant.findByLatinName(search);
      model.put("searchResults", searchResults);

      model.put("template", "templates/search-results.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/plants/search-zone", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      if(request.session().attribute("authenticated") instanceof User) {
        request.session().attribute("authenticated", request.session().attribute("authenticated"));
      }

      String search = request.queryParams("search-zone");
      List<Plant> searchResults = Plant.findByZone(search);
      model.put("searchResults", searchResults);

      model.put("template", "templates/search-results.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/plants/search-season", (request, response) -> {
      Map<String, Object> model = new HashMap<String, Object>();

      if(request.session().attribute("authenticated") instanceof User) {
        request.session().attribute("authenticated", request.session().attribute("authenticated"));
      }

      String search = request.queryParams("search-season");
      List<Plant> searchResults = Plant.findBySeason(search);
      model.put("searchResults", searchResults);

      model.put("template", "templates/search-results.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());


    post("/user/:user_id/garden/:garden_id/delete", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      int gardenId = Integer.parseInt(request.params("garden_id"));
      Garden currentGarden = Garden.findById(gardenId);
      currentGarden.delete();


      response.redirect("/user/" + id);
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

    post("/user/:user_id/admin/add-companion", (request, response) -> {

      int id = Integer.parseInt(request.params("user_id"));
      User currentUser = User.findById(id);
      request.session().attribute("authenticated", currentUser);

      int plantId = Integer.parseInt(request.queryParams("plant"));
      String[] companions = request.queryParamsValues("companions");
      Plant mainPlant = Plant.findById(plantId);

      for(String companionId : companions) {
        int intId = Integer.parseInt(companionId);
        Plant companion = Plant.findById(intId);
        mainPlant.addCompanion(companion);
      }

      response.redirect("/user/" + id + "/admin");
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

    before("/user/:user_id/garden/:garden_id/add-plant", (request, response) -> {
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