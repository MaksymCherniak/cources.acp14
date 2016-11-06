package week4.home.study.interceptors.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import week4.home.study.dao.interfaces.IGroupDao;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UpdateGroup extends HandlerInterceptorAdapter {
    @Autowired
    private IGroupDao iGroupDao;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return true;
    }
}
