package org.smart.entc.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Created by john on 3/4/15.
 */
@Controller
@RequestMapping("/")
public class BaseController {
    HttpSession session;

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable String name, ModelMap model) {

        model.addAttribute("message", "FYP-11 Web Project + Spring 3 MVC - you are now at page: " + name);
        return "pages/index";

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "pages/login";
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home(HttpServletRequest request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String name = auth.getName(); //get logged in username
        session = request.getSession();
        session.setAttribute("userName", name);
        return "pages/home";
    }

    @RequestMapping(value = "/1stfloor", method = RequestMethod.GET)
    public String first() {
        return "pages/firstfloor";
    }

    @RequestMapping(value = "/3rdfloor", method = RequestMethod.GET)
    public String third() {
        return "pages/thirdfloor";
    }
}
