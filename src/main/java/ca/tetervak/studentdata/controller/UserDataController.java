package ca.tetervak.studentdata.controller;

import ca.tetervak.studentdata.data.LoginDataService;
import ca.tetervak.studentdata.model.PasswordGenerator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Controller
@RequestMapping("/users")
public class UserDataController {

    private final LoginDataService loginDataService;
    private final PasswordGenerator passwordGenerator;

    public UserDataController(
            LoginDataService loginDataService,
            PasswordGenerator passwordGenerator
    ) {
        this.loginDataService = loginDataService;
        this.passwordGenerator = passwordGenerator;
    }

    @GetMapping(value={"/","/index"})
    public String index(){
        log.trace("index() is called");
        return "users/Index";
    }

    // an admin clicks "List Users" link in "Index.html",
    @GetMapping("/list-users")
    public String listUsers(Model model) {
        log.trace("listUsers() is called");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("you", authentication.getName());
        model.addAttribute("users",
                loginDataService.getAllUserNames("ROLE_USER"));
        model.addAttribute("admins",
                loginDataService.getAllUserNames("ROLE_ADMIN"));
        return "users/ListUsers";
    }

    // an admin clicks "Add User" link in "ListUsers.html",
    @GetMapping("/add-user")
    public String addUser(Model model) {
        log.trace("addUser() is called");
        String message = "Enter login and password for the new user account.";
        model.addAttribute("message", message);
        model.addAttribute("random", passwordGenerator.randomPassword());
        return "users/AddUser";
    }

    // an admin clicks on "Add User" button in "AddUser.html",
    // the form submits the data to "InsertUser"
    @PostMapping("/insert-user")
    public String insertUser(
            @RequestParam String login,
            @RequestParam String password,
            @RequestParam String role,
            Model model
    ) {
        log.trace("insertUser() is called");
        String message;
        if (login == null || login.trim().isEmpty()) {
            log.trace("missing login input");
            message = "The account login cannot be left empty";
        } else if (loginDataService.userExists(login)) {
            log.trace("the login is already in use");
            message = "The login is already in use.";
        } else if (password == null || password.trim().isEmpty()) {
            log.trace("missing password input");
            message = "The account password cannot be left empty.";
        } else {
            login = login.trim();
            password = password.trim();
            loginDataService.insertUser(login, password);
            log.trace("added user " + login);
            model.addAttribute("login",login);
            if(role != null && role.equals("admin")){
                loginDataService.insertRole(login, "ROLE_ADMIN");
                log.trace("added ROLE_ADMIN to " + login);
                model.addAttribute("role","admin");
            }else{
                loginDataService.insertRole(login, "ROLE_USER");
                log.trace("added ROLE_USER to " + login);
                model.addAttribute("role","user");
            }
            return "users/UserAdded";
        }
        model.addAttribute("message", message);
        model.addAttribute("random", passwordGenerator.randomPassword());
        return "users/AddUser";
    }

    // an admin clicks "Delete" link in "ListUsers.html",
    @GetMapping("/delete-user")
    public String deleteUser() {
        log.trace("deleteUser() is called");
        return "users/DeleteUser";
    }

    // an admin clicks on "Delete User" button in "DeleteUser.jsp",
    // the form submits the data to "RemoveUser"
    @PostMapping("/remove-user")
    public String removeUser(@RequestParam String login) {
        loginDataService.removeRoles(login);
        loginDataService.removeUser(login);
        return "redirect:/users/list-users";
    }
}
