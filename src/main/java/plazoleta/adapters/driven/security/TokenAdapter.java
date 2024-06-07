package plazoleta.adapters.driven.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Service;
import plazoleta.domain.api.ITokenValidatorServicePort;
import plazoleta.domain.spi.ITokenValidatorPort;

@Service
public class TokenAdapter implements ITokenValidatorPort {
    private static final String SECRET_KEY = "586E3272357538782F413F4428472B4B6250655368566B597033733676397924";

    @Override
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    @Override
    public String extractRole(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);

        return claims.getBody().get("rol", String.class);
    }

    @Override
    public int getUserIdFromToken(String token) {
        Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
        Integer userIdInt = claims.getBody().get("userId", Integer.class);
        return userIdInt != null ? userIdInt.intValue() : 0;
    }


}
