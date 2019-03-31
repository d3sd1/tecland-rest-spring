package TecLand.utils;

import com.lambdaworks.crypto.SCryptUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class Security {

    public boolean checkPassword(String pwd, String hash) {
        return SCryptUtil.check(pwd, hash);
    }

    public String encodePassword(String pwd) {

        return SCryptUtil.scrypt(pwd, 16, 16, 16);
    }


    private Claims decodeJWT(String jwt, String hash) {
        return Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(hash))
                .parseClaimsJws(jwt).getBody();
    }

    public boolean isJWTExpired(String jwt, String hash) {
        Claims claims = this.decodeJWT(jwt, hash);
        return claims.getExpiration().getTime() < new Date().getTime();
    }

    public String generateJWTToken(String id, String issuer, String subject, long ttlMillis, String hash) {

        //The JWT signature algorithm we will be using to sign the token
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        //We will sign our JWT with our ApiKey secret
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(hash);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        //Let's set the JWT Claims
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        //if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        //Builds the JWT and serializes it to a compact, URL-safe string
        return builder.compact();
    }
}
