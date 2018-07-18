package com.hengxunda.pc.oauth2;



import com.hengxunda.common.Enum.StatusCodeEnum;
import com.hengxunda.common.exception.ServiceException;
import com.hengxunda.dao.po.ShiroUserPo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * Shiro工具类
 */
public class ShiroUtils {


	public static Subject getSubject() {
		return SecurityUtils.getSubject();
	}


	public static Session getSession() {
		return getSubject().getSession();
	}

    /**
     * 获取用户信息，未处理了空对象
     * @return
     */
	public static ShiroUserPo getShiroUser() {
		return (ShiroUserPo) getSubject().getPrincipal();
	}

    /**
     * 获取用户id
     * @return
     */
	public static String getShiroUserId() {
		ShiroUserPo shiroUserPo = getShiroUser();
		if(shiroUserPo == null)
			throw new ServiceException(1,"请先登录");
		return shiroUserPo.getId();
	}

    /**
     * 获取用户信息，处理了空对象
     * @return
     */
	public static ShiroUserPo getUserChecked(){
		ShiroUserPo shiroUserPo = getShiroUser();
        if(shiroUserPo==null)
            throw new ServiceException(StatusCodeEnum.NotLogin.getCode(),StatusCodeEnum.NotLogin.getValue());

        return shiroUserPo;
    }


	
	public static void setSessionAttribute(Object key, Object value) {
		getSession().setAttribute(key, value);
	}

	public static Object getSessionAttribute(Object key) {
		return getSession().getAttribute(key);
	}

	public static boolean isLogin() {
		return getSubject().getPrincipal() != null;
	}

	public static void logout() {
		getSubject().logout();
	}


}
