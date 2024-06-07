package plazoleta.domain.spi;

import java.security.SecureRandom;

public interface ISecurityPersistencePort {
    public String generateSecurityPin();
}
