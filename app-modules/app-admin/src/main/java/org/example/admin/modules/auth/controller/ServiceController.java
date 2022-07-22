package org.example.admin.modules.auth.controller;

import org.example.common.msg.ObjectRestResponse;
import org.example.common.rest.BaseController;
import org.example.admin.modules.auth.biz.ClientBiz;
import org.example.admin.modules.auth.entity.Client;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ace
 * @create 2017/12/26.
 */
@RestController
@RequestMapping("service")
public class ServiceController extends BaseController<ClientBiz,Client>{

    @RequestMapping(value = "/{id}/client", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyUsers(@PathVariable int id, String clients){
        baseBiz.modifyClientServices(id, clients);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/client", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<Client>> getUsers(@PathVariable int id){
        return new ObjectRestResponse<List<Client>>().data(baseBiz.getClientServices(id));
    }
}
