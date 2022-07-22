package org.example.admin.modules.admin.rpc;

import org.example.api.vo.authority.CheckPermissionInfo;
import org.example.api.vo.authority.PermissionInfo;
import org.example.admin.modules.admin.rpc.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author the sun
 * @create 2017-06-21 8:15
 */
@RestController
@RequestMapping("/api")
public class UserRest {
    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/permissions", method = RequestMethod.GET)
    public @ResponseBody
    List<PermissionInfo> getAllPermission() {
        return permissionService.getAllPermission();
    }

    @RequestMapping(value = "/user/{username}/permissions", method = RequestMethod.GET)
    public @ResponseBody
    List<PermissionInfo> getPermissionByUsername(@PathVariable("username") String username) {
        return permissionService.getPermissionByUsername(username);
    }

    @RequestMapping(value = "/user/{username}/check_permission", method = RequestMethod.GET)
    public Mono<CheckPermissionInfo> checkUserPermission(@PathVariable("username") String username, String requestUri, String requestMethod) {
        return permissionService.checkUserPermission(username, requestUri, requestMethod);
    }

}
