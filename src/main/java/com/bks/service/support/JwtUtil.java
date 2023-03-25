package com.bks.service.support;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static com.bks.service.support.ServiceConstants.SECRET_KEY;

@Service
public class JwtUtil {


	/*
	* если claims sub и exp не были внесены в JWT body,
	* то и извлекать их не потребуется первыми двумя методами
	* также как и валидировать их  в isTokenExpired и validateToken
	* */


    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        //claims.put("USER_ID", ...)
		//return createToken(claims);

		//username играет роль 'subject' claim - в JSON payload будет поле sub, registered but not required
        return createToken(claims, userDetails.getUsername());
    }

    /*
    * поскольку необходимый Claim только USER_ID
    * то claim sub [subject] не является  необходимым - setSubject() СЛЕДУЕТ УБРАТЬ,Ы И ПРОЧИЕ
    * */
	/* setClaims формирует JWT payload (body) из пользовательских claims в виде  JSON ,
	 * если предпочтительно иметь payload  строкой, следует использовать setPayload(String payload);
	 * +
	 * setSubject формирует claim sub [subject]
	 * setIssuedAt -  claim iat [issued at time]
	 * setExpiration -  claim exp [expiration time]
	 * */

    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

	private String createToken(Map<String, Object> claims) {
		return Jwts.builder().setClaims(claims).setIssuedAt(new Date(System.currentTimeMillis()))
			.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
			.signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
	}

	/* валиден ли токен для данного пользователя */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}