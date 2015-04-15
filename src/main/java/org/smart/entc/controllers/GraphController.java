package org.smart.entc.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by john on 3/4/15.
 */
@Controller
@RequestMapping("/")
public class GraphController {

    @RequestMapping(value = "/light", method = RequestMethod.GET)
    public String light() {
        return "graph/lightGraph";

    }

    @RequestMapping(value = "/humidity", method = RequestMethod.GET)
    public String humidity() {
        return "graph/humidityGraph";

    }

    @RequestMapping(value = "/temperature", method = RequestMethod.GET)
    public String temperature() {
        return "graph/temperatureGraph";

    }

    @RequestMapping(value = "/noise", method = RequestMethod.GET)
    public String noise() {
        return "graph/noiseGraph";

    }
}
