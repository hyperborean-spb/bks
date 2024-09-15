package com.bks.filters;

import com.bks.service.support.JwtUtil;
import com.bks.service.BksUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
* Назначение - извлечь JWT из заголовка Authorization следующих запросов, установить  валидность, поместить  в SecurityContext  пользователя
* */
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final BksUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);
            username = jwtUtil.extractUsername(jwt);
        }

	/* роль этого фильтра заключается в проверке передачи токена, извлечении имени пользователя
        * и заполнении контекста безопасности токеном UsernamePasswordAuthenticationToken,
        * сформированном из UserDetails, поэтому UserDetails формируется лишь в случае пустого контекста
	* */
    
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            /*
            * зачем нам собственно userDetails?
            * -  валидация токена проводится путем
            * а) сравнения имени пользователя из токена и userDetails
            * б) проверки isExpired времени жизни токена
            * но userDetails формируется из username, передаваемого в userDetailsService.loadUserByUsername
            * в нашем случае полученного из токена
            * поэтому имя пользователя из токена и userDetails заведомо равны !
            * */
            //if (jwtUtil.validateToken(jwt, userDetails)) {
			if (!jwtUtil.isTokenExpired(jwt)) {

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        chain.doFilter(request, response);
    }
}
