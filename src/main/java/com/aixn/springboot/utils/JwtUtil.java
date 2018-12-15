package com.aixn.springboot.utils;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zxx
 * @date 2018/12/15 21:13
 */
public class JwtUtil {

    private static String SECRET = "mysecret";

    public static String createToken( String info) throws UnsupportedEncodingException {
        Date iatDate = new Date();
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(Calendar.MINUTE,100);
        Date expiresDate = nowTime.getTime();

        Map<String,Object> map = new HashMap<>();
        String token = JWT.create().withHeader(map)
                .withExpiresAt(expiresDate)
                .withClaim("info",info)
                .withIssuedAt(iatDate)
                .sign(Algorithm.HMAC256(SECRET));
        return token;
    }


    public static String verifyToken(String token) throws Exception {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;
        jwt = verifier.verify(token);
        Map<String,Claim> result = jwt.getClaims();
        jwt.getKeyId() ;
        jwt.getToken();
        jwt.getClaim("info").asString();

        return result.get("info").asString();
    }


}
