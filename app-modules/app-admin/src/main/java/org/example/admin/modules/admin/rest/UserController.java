package org.example.admin.modules.admin.rest;

import org.example.common.msg.ObjectRestResponse;
import org.example.common.rest.BaseController;
import org.example.admin.modules.admin.biz.MenuBiz;
import org.example.admin.modules.admin.biz.UserBiz;
import org.example.admin.modules.admin.entity.Menu;
import org.example.admin.modules.admin.entity.User;
import org.example.admin.modules.admin.rpc.service.PermissionService;
import org.example.admin.modules.admin.vo.FrontUser;
import org.example.admin.modules.admin.vo.FrontUserV2;
import org.example.admin.modules.admin.vo.MenuTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author the sun
 * @create 2017-06-08 11:51
 */
@RestController
@RequestMapping("user")
public class UserController extends BaseController<UserBiz,User> {
    @Autowired
    private PermissionService permissionService;

    @Autowired
    private MenuBiz menuBiz;

    @RequestMapping(value = "/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getUserInfo(String token) throws Exception {
        FrontUser userInfo = permissionService.getUserInfo(token);
        if(userInfo==null) {
            return ResponseEntity.status(401).body(false);
        } else {
            return ResponseEntity.ok(userInfo);
        }
    }

    @RequestMapping(value = "/v2/front/info", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse getUserInfoV2() throws Exception {
        FrontUserV2 userInfo = permissionService.getUserInfoV2();
        return new ObjectRestResponse<FrontUserV2>().data(userInfo);
    }

    @RequestMapping(value = "/front/menus", method = RequestMethod.GET)
    public @ResponseBody
    List<MenuTree> getMenusByUsername(String token) throws Exception {
        return permissionService.getMenusByUsername(token);
    }

    @RequestMapping(value = "/front/menu/all", method = RequestMethod.GET)
    public @ResponseBody
    List<Menu> getAllMenus() throws Exception {
        return menuBiz.selectListAll();
    }
}
