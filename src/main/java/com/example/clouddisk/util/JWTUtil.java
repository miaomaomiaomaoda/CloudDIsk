package com.example.clouddisk.util;

import com.example.clouddisk.config.jwt.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.DefaultClaims;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.Date;

/**
 * @author R.Q.
 * brief:JWT工具类
 */
@Component
public class JWTUtil {
    @Resource
    JwtProperties jwtProperties;

    /**
     * function:由字符串生成加密Key
     * @return 加密key
     */
    private SecretKey generalKey(){
        //本地密码解密
        byte[] encodedKey = Base64.decodeBase64(jwtProperties.getSecret());
        //根据字节数组使用AES算法构造密钥
        return new SecretKeySpec(encodedKey, 0, encodedKey.length, "AES");
    }

    /**
     * function:创建jwt
     * @param subject JWT主题
     * @return 生成的JWT
     * @throws Exception 异常
     */
    public String createJWT(String subject) throws Exception{
        //生成JWT的时间
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        //生成签名的时候使用的秘钥secret，切记这个秘钥不能外露，是你服务端的私钥，在任何场景都不应该流露出去，一旦客户端得知这个secret，那就意味着客户端是可以自我签发jwt的
        SecretKey key = generalKey();
        ScriptEngineManager manager = new ScriptEngineManager(null);
        ScriptEngine se = manager.getEngineByName("javascript");
        int expireTime = 0;
        try{
            expireTime = (int) se.eval(jwtProperties.getPayload().getRegisterdClaims().getExp());
        }catch(ScriptException e){
            e.printStackTrace();
        }
        //为payload添加各种标准声明和私有声明
        DefaultClaims defaultClaims = new DefaultClaims();
        defaultClaims.setIssuer(jwtProperties.getPayload().getRegisterdClaims().getIss());
        defaultClaims.setExpiration(new Date(System.currentTimeMillis()+expireTime));
        defaultClaims.setSubject(subject);
        defaultClaims.setAudience(jwtProperties.getPayload().getRegisterdClaims().getAud());

        //new一个JwtBuilder，设置jwt的body
        JwtBuilder builder = Jwts.builder()
                .setClaims(defaultClaims)
                .setIssuedAt(date)
                .signWith(SignatureAlgorithm.forName(jwtProperties.getHeader().getAlg()),key); //设置签名，使用的是签名算法和签名使用的秘钥
        return builder.compact();
    }

    /**
     * function:解密jwt
     * @param jwt JWT
     * @return JWT解析结果
     * @throws Exception 异常
     */
    public Claims parseJWT(String jwt) throws Exception{
        //签名秘钥，和生成的签名的秘钥一模一样
        SecretKey key = generalKey();
        Claims claims = Jwts.parser()   //得到DefaultJwtParser
                .setSigningKey(key)     //设置签名密钥
                .parseClaimsJws(jwt).getBody(); //设置需要解析的jwt
        return claims;
    }
}
