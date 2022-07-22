package org.example.admin.modules.auth.service.impl;

import com.alibaba.fastjson.JSON;
import org.example.api.vo.user.UserInfo;
import org.example.common.constant.RedisKeyConstant;
import org.example.common.exception.auth.UserInvalidException;
import org.example.common.util.AddressUtils;
import org.example.common.util.IpUtils;
import org.example.common.util.WebUtils;
import org.example.common.util.jwt.IJWTInfo;
import org.example.common.util.jwt.JWTInfo;
import org.example.admin.modules.admin.entity.OnlineLog;
import org.example.admin.modules.admin.rpc.service.PermissionService;
import org.example.admin.modules.auth.service.AuthService;
import org.example.admin.modules.auth.util.user.JwtAuthenticationRequest;
import org.example.admin.modules.auth.util.user.JwtTokenUtil;
import eu.bitwalker.useragentutils.UserAgent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Value("${jwt.expire}")
    private int expire;


    @Override
    public Map login(JwtAuthenticationRequest authenticationRequest) throws Exception {
        UserInfo info = permissionService.validate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        if (!StringUtils.isEmpty(info.getId())) {
            JWTInfo jwtInfo = new JWTInfo(info.getUsername(), info.getId() + "", info.getName());
            String token = jwtTokenUtil.generateToken(jwtInfo);
            Map<String, String> result = new HashMap<>();
            result.put("accessToken", token);
            result.put("id", info.id);
            WebUtils.getRequest();
            writeOnlineLog(jwtInfo);
            return result;
        }
        throw new UserInvalidException("用户不存在或账户密码错误!");
    }

    @Override
    public void validate(String token) throws Exception {
        jwtTokenUtil.getInfoFromToken(token);
    }

    @Override
    public String refresh(String oldToken) throws Exception {
        return jwtTokenUtil.generateToken(jwtTokenUtil.getInfoFromToken(oldToken));
    }

    @Override
    public void logout(String token) throws Exception {
        IJWTInfo infoFromToken = jwtTokenUtil.getInfoFromToken(token);
        String tokenId = infoFromToken.getTokenId();
        stringRedisTemplate.delete(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + tokenId);
        stringRedisTemplate.opsForZSet().remove(RedisKeyConstant.REDIS_KEY_TOKEN, tokenId);
    }

    @Async
    public void writeOnlineLog(JWTInfo jwtInfo) {
        final UserAgent userAgent = UserAgent.parseUserAgentString(WebUtils.getRequest().getHeader("User-Agent"));
        final String ip = IpUtils.getRemoteIP(WebUtils.getRequest());
        String address = AddressUtils.getRealAddressByIP(ip);

        OnlineLog onlineLog = new OnlineLog();
        // 获取客户端操作系统
        String os = userAgent.getOperatingSystem().getName();
        // 获取客户端浏览器
        String browser = userAgent.getBrowser().getName();
        onlineLog.setBrowser(browser);
        onlineLog.setIpaddr(ip);
        onlineLog.setTokenId(jwtInfo.getTokenId());
        onlineLog.setLoginTime(System.currentTimeMillis());
        onlineLog.setUserId(jwtInfo.getId());
        onlineLog.setUserName(jwtInfo.getName());
        onlineLog.setLoginLocation(address);
        onlineLog.setOs(os);
        stringRedisTemplate.opsForValue().set(RedisKeyConstant.REDIS_KEY_TOKEN + ":" + jwtInfo.getTokenId(), JSON.toJSONString(onlineLog, false), expire, TimeUnit.MINUTES);
        stringRedisTemplate.opsForZSet().add((RedisKeyConstant.REDIS_KEY_TOKEN), jwtInfo.getTokenId(), 0);
    }
}
