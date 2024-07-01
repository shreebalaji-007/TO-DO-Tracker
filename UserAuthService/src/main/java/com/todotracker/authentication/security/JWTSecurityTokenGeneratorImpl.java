package com.todotracker.authentication.security;


import com.todotracker.authentication.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTSecurityTokenGeneratorImpl implements SecurityTokenGenerator {
    public String createToken(User user) {
        // multiple claims for a token - 3 types - registered, public, and private
        String tokenSubjct = (String.valueOf(user.getUserId())).concat("|").concat(user.getEmail());
        String jwtToken = Jwts.builder().setIssuer("CapStone")
                .setSubject(tokenSubjct)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "capstoneproject")
                //mysecret is the key that has to be shared everytime you do encrypt and decrypt process
                .compact();
        return jwtToken;
    }

    public Map<String, String> generateToken(User user) {
        // multiple claims for a token - 3 types - registered, public, and private
        String tokenSubjct = (String.valueOf(user.getUserId())).concat("|").concat(user.getEmail());
        String jwtToken = Jwts.builder().setIssuer("CapStone")
                .setSubject(tokenSubjct)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, "capstoneproject")
                //mysecret is the key that has to be shared everytime you do encrypt and decrypt process
                .compact();
        Map<String, String> map = new HashMap<>();
        map.put("token", jwtToken);
        map.put("message", "Authentication Successful");
        return map;
    }

}