package org.smart.entc.controllers;

import org.smart.entc.repo.DataLayer;
import org.smart.entc.repo.object.Node;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by john on 3/4/15.
 */
@Controller
@RequestMapping("/")
public class NodeController {

    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public String info(String location,ModelMap model) {
        Node node= DataLayer.getNodeByName(location);
        if(node==null){
            return "pages/403";
        }
        model.addAttribute("node",node);
        return "pages/infoType"+node.getType();
    }
}
