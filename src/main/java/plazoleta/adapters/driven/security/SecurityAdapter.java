package plazoleta.adapters.driven.security;

import org.springframework.stereotype.Service;
import plazoleta.domain.spi.ISecurityPersistencePort;

import java.security.SecureRandom;

@Service
public class SecurityAdapter implements ISecurityPersistencePort {
    public  String generateSecurityPin() {
        SecureRandom random = new SecureRandom();
        int pin = random.nextInt(9000) + 1000;
        return String.valueOf(pin);
    }
}
