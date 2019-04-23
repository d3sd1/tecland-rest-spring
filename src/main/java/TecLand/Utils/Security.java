package TecLand.Utils;

import TecLand.Logger.LogService;
import TecLand.ORM.Model.DashUserLogin;
import TecLand.ORM.Repository.DashUserLoginRepository;
import com.lambdaworks.crypto.SCryptUtil;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.security.MessageDigest;
import java.util.Date;

@Service("security")
public class Security {

    @Autowired
    private DashUserLoginRepository dashUserLoginRepository;

    @Autowired
    private LogService logger;

    public boolean checkPassword(String pwd, String hash) {
        return SCryptUtil.check(pwd, hash);
    }

    public String encodePassword(String pwd) {

        return SCryptUtil.scrypt(pwd, 16, 16, 16);
    }


    private Claims decodeJWT(String jwt, String hash) {
        if (hash == null) {
            hash = "emptyHashPreventNullPointer";
        }
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(hash))
                    .parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
        } catch (SignatureException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    public boolean isJWTExpired(String jwt, String hash) {
        Claims claims = this.decodeJWT(jwt, hash);
        return claims.getExpiration().getTime() < new Date().getTime();
    }

    public boolean validateSession(String jwt) {
        DashUserLogin dbUserLogin = dashUserLoginRepository.findByJwt(jwt);
        if (null == dbUserLogin) {
            return false;
        }
        if (this.isJWTExpired(dbUserLogin.getJwt(), dbUserLogin.getHash())) {
            return false;
        }
        return true;
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

    public String buildSessionHash(Long userId) {
        String hexStr = "";
        try {

            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.reset();
            byte[] buffer = userId.toString().getBytes("UTF-8");
            md.update(buffer);
            byte[] digest = md.digest();
            for (int i = 0; i < digest.length; i++) {
                hexStr += Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1);
            }
        } catch (Exception e) {
            logger.error("Error making session hash for userId " + userId + ": " + e.getMessage());
        }
        return hexStr;
    }
}
