package org.example.admin.modules.admin.rest;

import org.example.common.msg.ObjectRestResponse;
import org.example.common.rest.BaseController;
import org.example.common.util.TreeUtil;
import org.example.admin.modules.admin.biz.GroupBiz;
import org.example.admin.modules.admin.biz.ResourceAuthorityBiz;
import org.example.admin.modules.admin.constant.AdminCommonConstant;
import org.example.admin.modules.admin.entity.Group;
import org.example.admin.modules.admin.vo.AuthorityMenuTree;
import org.example.admin.modules.admin.vo.GroupTree;
import org.example.admin.modules.admin.vo.GroupUsers;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * ${DESCRIPTION}
 *
 * @author the sun
 * @create 2017-06-12 8:49
 */
@Controller
@RequestMapping("group")
public class GroupController extends BaseController<GroupBiz, Group> {
    @Autowired
    private ResourceAuthorityBiz resourceAuthorityBiz;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<Group>> list(String name,String groupType) {
        if(StringUtils.isBlank(name)&&StringUtils.isBlank(groupType)) {
            return new ObjectRestResponse<>().data(new ArrayList<Group>());
        }
        Example example = new Example(Group.class);
        if (StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(groupType)) {
            example.createCriteria().andEqualTo("groupType", groupType);
        }

        return new ObjectRestResponse().data(baseBiz.selectByExample(example));
    }



    @RequestMapping(value = "/{id}/user", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifiyUsers(@PathVariable int id,String members,String leaders){
        baseBiz.modifyGroupUsers(id, members, leaders);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/user", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<GroupUsers> getUsers(@PathVariable int id){
        return new ObjectRestResponse<GroupUsers>().data(baseBiz.getGroupUsers(id));
    }

    @RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse modifyMenuAuthority(@PathVariable  int id, String menuTrees){
        String [] menus = menuTrees.split(",");
        baseBiz.modifyAuthorityMenu(id, menus);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/authority/menu", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<AuthorityMenuTree>> getMenuAuthority(@PathVariable  int id){
        return new ObjectRestResponse().data(baseBiz.getAuthorityMenu(id));
    }

    @RequestMapping(value = "/{id}/authority/element/add", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse addElementAuthority(@PathVariable  int id,int menuId, int elementId){
        baseBiz.modifyAuthorityElement(id,menuId,elementId);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/authority/element/remove", method = RequestMethod.PUT)
    @ResponseBody
    public ObjectRestResponse removeElementAuthority(@PathVariable int id,int menuId, int elementId){
        baseBiz.removeAuthorityElement(id,menuId,elementId);
        return new ObjectRestResponse();
    }

    @RequestMapping(value = "/{id}/authority/element", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<Integer>> getElementAuthority(@PathVariable  int id){
        return new ObjectRestResponse().data(baseBiz.getAuthorityElement(id));
    }


    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    @ResponseBody
    public ObjectRestResponse<List<GroupTree>> tree(String name, String groupType) {
        if(StringUtils.isBlank(name)&&StringUtils.isBlank(groupType)) {
            return new ObjectRestResponse<>().data(new ArrayList<GroupTree>());
        }
        Example example = new Example(Group.class);
        if (StringUtils.isNotBlank(name)) {
            example.createCriteria().andLike("name", "%" + name + "%");
        }
        if (StringUtils.isNotBlank(groupType)) {
            example.createCriteria().andEqualTo("groupType", groupType);
        }
        return  new ObjectRestResponse<>().data(getTree(baseBiz.selectByExample(example), AdminCommonConstant.ROOT));
    }


    private List<GroupTree> getTree(List<Group> groups,int root) {
        List<GroupTree> trees = new ArrayList<GroupTree>();
        GroupTree node = null;
        for (Group group : groups) {
            node = new GroupTree();
            node.setLabel(group.getName());
            BeanUtils.copyProperties(group, node);
            trees.add(node);
        }
        return TreeUtil.bulid(trees,root) ;
    }
}
