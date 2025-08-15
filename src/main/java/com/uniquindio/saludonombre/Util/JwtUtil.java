package com.uniquindio.saludonombre.Util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import java.security.KeyFactory;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final RSAPublicKey publicKey;

    public JwtUtil() {
        try {
            this.publicKey = loadPublicKey();
        } catch (Exception e) {
            log.error("Error cargando clave pública", e);
            throw new RuntimeException("No se pudo cargar la clave pública", e);
        }
    }

    private RSAPublicKey loadPublicKey() throws Exception {
        var resource = new ClassPathResource("keys/public-key.pem");
        String key = new String(resource.getInputStream().readAllBytes())
                .replace("-----BEGIN PUBLIC KEY-----", "")
                .replace("-----END PUBLIC KEY-----", "")
                .replaceAll("\\s+", "");
        byte[] decoded = Base64.getDecoder().decode(key);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(decoded);
        return (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(spec);
    }

    public Claims validateToken(String token) {
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(publicKey)
                .requireIssuer("ingesis.uniquindio.edu.co")
                .build()
                .parseSignedClaims(token);

        return claims.getPayload();
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }
}
