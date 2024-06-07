package plazoleta.domain.api;

public interface ITokenValidatorServicePort {
    public boolean validateToken(String token);
    public String extractRole(String token);
    public int getUserIdFromToken(String token);
}
