package org.smart.entc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by john on 3/4/15.
 */
@Controller
@RequestMapping("/")
public class BaseController {

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public String welcomeName(@PathVariable String name, ModelMap model) {

        model.addAttribute("message", "FYP-11 Web Project + Spring 3 MVC - you are now at page: " + name);
        return "pages/index";

    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public String home() {
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
