package plazoleta.domain.spi;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public interface ITokenValidatorPort {
    public boolean validateToken(String token);
    public String extractRole(String token);
    public int getUserIdFromToken(String token);
}
