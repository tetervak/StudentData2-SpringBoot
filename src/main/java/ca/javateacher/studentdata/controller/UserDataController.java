package ca.javateacher.studentdata.controller;

import ca.javateacher.studentdata.data.LoginDataService;
import ca.javateacher.studentdata.model.PasswordGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/users")
public class UserDataController {

    private final Logger logger = LoggerFactory.getLogger(UserDataController.class);

    private final LoginDataService loginDataService;
    private final PasswordGenerator passwordGenerator;

    @Autowired
    public UserDataController(
            @Qualifier("loginDataServiceJpaImpl") LoginDataService loginDataService,
            PasswordGenerator passwordGenerator) {
        this.loginDataService = loginDataService;
        this.passwordGenerator = passwordGenerator;
    }

    @GetMapping(value={"/","/Index"})
    public String index(){
        logger.trace("index() is called");
        return "users/Index";
    }

    // an admin clicks "List Users" link in "Index.html",
    @GetMapping("/ListUsers")
    public String listUsers(Model model) {
        logger.trace("listUsers() is called");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("you", authentication.getName());
        model.addAttribute("users",
                loginDataService.getAllUserNames("ROLE_USER"));
        model.addAttribute("admins",
                loginDataService.getAllUserNames("ROLE_ADMIN"));
        return "users/ListUsers";
    }

    // an admin clicks "Add User" link in "ListUsers.html",
    @GetMapping("/AddUser")
    public String addUser(Model model) {
        logger.trace("addUser() is called");
        String message = "Enter login and password for the new user account.";
        model.addAttribute("message", message);
        model.addAttribute("random", passwordGenerator.randomPassword());
        return "users/AddUser";
    }

    // an admin clicks on "Add User" button in "AddUser.html",
    // the form submits the data to "InsertUser"
    @PostMapping("/InsertUser")
    public String insertUser(HttpServletRequest request) {
        logger.trace("insertUser() is called");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String role = request.getParameter("role");
        String message;
        if (login == null || login.trim().isEmpty()) {
            logger.trace("missing login input");
            message = "The account login cannot be left empty";
        } else if (loginDataService.userExists(login)) {
            logger.trace("the login is already in use");
            message = "The login is already in use.";
        } else if (password == null || password.trim().isEmpty()) {
            logger.trace("missing password input");
            message = "The account password cannot be left empty.";
        } else {
            login = login.trim();
            password = password.trim();
            loginDataService.insertUser(login, password);
            logger.trace("added user " + login);
            request.setAttribute("login",login);
            if(role != null && role.equals("admin")){
                loginDataService.insertRole(login, "ROLE_ADMIN");
                logger.trace("added ROLE_ADMIN to " + login);
                request.setAttribute("role","admin");
            }else{
                loginDataService.insertRole(login, "ROLE_USER");
                logger.trace("added ROLE_USER to " + login);
                request.setAttribute("role","user");
            }
            return "users/UserAdded";
        }
        request.setAttribute("message", message);
        request.setAttribute("random", passwordGenerator.randomPassword());
        return "users/AddUser";
    }

    // an admin clicks "Delete" link in "ListUsers.html",
    @GetMapping("/DeleteUser")
    public String deleteUser() {
        logger.trace("deleteUser() is called");
        return "users/DeleteUser";
    }

    // an admin clicks on "Delete User" button in "DeleteUser.jsp",
    // the form submits the data to "RemoveUser"
    @PostMapping("/RemoveUser")
    public String removeUser(@RequestParam String login) {
        loginDataService.removeRoles(login);
        loginDataService.removeUser(login);
        return "redirect:/users/ListUsers";
    }
}
