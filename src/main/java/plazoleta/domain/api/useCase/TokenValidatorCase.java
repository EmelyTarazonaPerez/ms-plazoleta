package plazoleta.domain.api.useCase;

import plazoleta.domain.api.ITokenValidatorServicePort;
import plazoleta.domain.spi.ITokenValidatorPort;

public class TokenValidatorCase implements ITokenValidatorServicePort {
    private final ITokenValidatorPort tokenValidatorPort;

    public TokenValidatorCase(ITokenValidatorPort tokenValidatorPort) {
        this.tokenValidatorPort = tokenValidatorPort;
    }
    @Override
    public boolean validateToken(String token) {
        return tokenValidatorPort.validateToken(token);
    }
    @Override
    public String extractRole(String token) {
        return tokenValidatorPort.extractRole(token);
    }

    @Override
    public int getUserIdFromToken(String token) {
        return tokenValidatorPort.getUserIdFromToken(token);
    }
}
