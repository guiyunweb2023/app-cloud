package org.example.admin.modules.auth.controller;

import org.example.common.msg.ObjectRestResponse;
import org.example.admin.configuration.KeyConfiguration;
import org.example.admin.modules.auth.service.AuthClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ace on 2017/9/10.
 */
@RestController
@RequestMapping("client")
public class ClientController{

    @Autowired
    private AuthClientService authClientService;

    @Autowired
    private KeyConfiguration keyConfiguration;


    @RequestMapping(value = "/myClient")
    public ObjectRestResponse getAllowedClient(String serviceId, String secret) {
        return new ObjectRestResponse<List<String>>().data(authClientService.getAllowedClient(serviceId, secret));
    }


    @RequestMapping(value = "/userPubKey",method = RequestMethod.POST)
    public ObjectRestResponse<byte[]> getUserPublicKey(@RequestParam("clientId") String clientId, @RequestParam("secret") String secret) throws Exception {
        authClientService.validate(clientId, secret);
        return new ObjectRestResponse<byte[]>().data(keyConfiguration.getUserPubKey());
    }


}
