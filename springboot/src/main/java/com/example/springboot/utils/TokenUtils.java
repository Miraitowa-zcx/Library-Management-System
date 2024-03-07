package com.example.springboot.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springboot.entity.Admin;
import com.example.springboot.service.IAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

/**
 * Token 工具类
 *
 * @author <a href="mailto:2038322151@qq.com">Miraitowa_zcx</a>
 */
@Component
@Slf4j
public class TokenUtils {

    /**
     * 管理员服务
     */
    private static IAdminService staticAdminService;

    /**
     * 注入管理员服务
     */
    @Resource
    private IAdminService adminService;

    /**
     * 初始化
     */
    @PostConstruct
    public void setUserService() {
        staticAdminService = adminService;
    }

    /**
     * 生成 Token
     *
     * @param adminId 管理员 ID
     * @param sign    签名
     * @return token
     */
    public static String genToken(String adminId, String sign) {

        return
                // 将 user id 保存到 token 里面,作为载荷
                JWT.create().withAudience(adminId)
                        // 2小时后token过期
                        .withExpiresAt(DateUtil.offsetHour(new Date(), 2))
                        // 以 password 作为 token 的密钥
                        .sign(Algorithm.HMAC256(sign));
    }

    /**
     * 生成 Token
     *
     * @param adminId 管理员 ID
     * @param sign    签名
     * @param days    Token 过期时间
     * @return Token
     */
    public static String genToken(String adminId, String sign, int days) {
        return JWT.create().withAudience(adminId)
                .withExpiresAt(DateUtil.offsetDay(new Date(), days))
                .sign(Algorithm.HMAC256(sign));
    }

    /**
     * 获取当前登录的管理员信息
     *
     * @return 管理员信息
     */
    public static Admin getCurrentAdmin() {
        String token = null;
        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            token = request.getHeader("token");
            if (StrUtil.isBlank(token)) {
                token = request.getParameter("token");
            }
            if (StrUtil.isBlank(token)) {
                log.error("获取当前登录的token失败， token: {}", token);
                return null;
            }
            String adminId = JWT.decode(token).getAudience().get(0);
            return staticAdminService.getById(Integer.valueOf(adminId));
        } catch (Exception e) {
            log.error("获取当前登录的管理员信息失败, token={}", token, e);
            return null;
        }
    }
}
