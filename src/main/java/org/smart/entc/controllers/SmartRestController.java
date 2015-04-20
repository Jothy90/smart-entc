package org.smart.entc.controllers;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONObject;
import org.smart.entc.repo.DataLayer;
import org.smart.entc.repo.object.Node;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by john on 4/17/15.
 */
@RestController
public class SmartRestController {
    private ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());

    /*
{"id":1,"name":"ENTC1","temperature":34,"humidity":60,"noise":3,"light":90,"peopleCount":23,"type":2,"activity":1}*/


    @RequestMapping(value = "/node", method = RequestMethod.GET)
    public
    @ResponseBody
    String getNode(String name) {
        Node node = DataLayer.getNodeByName(name);
        String returnValue = null;
        try {
            returnValue = objectMapper.writeValueAsString(node);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }

    @RequestMapping(value = "/activity", method = RequestMethod.GET)
    public
    @ResponseBody
    String getActivity(String name) {
        Node node = DataLayer.getNodeByName(name);
        String returnValue = null;
        try {
            JSONObject obj = new JSONObject();
            obj.put("type",node.getType());
            obj.put("activity",node.getActivity());
            returnValue = obj.toJSONString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnValue;
    }
}
