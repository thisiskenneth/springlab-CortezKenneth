package edu.espe.springlab.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.swing.plaf.basic.BasicSplitPaneUI;

@Component
public class RequestLoggingInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // Token simulado
        final String VALID_TOKEN = "eltoken19";

        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.equals(VALID_TOKEN)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Acceso no autorizado o token inválido");
            System.out.println("❌ Bloqueado: Token inválido o ausente");
            return false;
        }
        request.setAttribute("t0", System.currentTimeMillis());
        System.out.println("✅ Autorizado -> " + request.getMethod() + " " + request.getRequestURI());
        return true;
    }
















    //posthandle




    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Long t0 = (Long)request.getAttribute("t0");
        long elapsed = (t0 == null ? 0 : System.currentTimeMillis() - t0);
        System.out.println("afterCompletion -> status = " + response.getStatus() + "tiempo = " + elapsed + "ms");
    }
}
