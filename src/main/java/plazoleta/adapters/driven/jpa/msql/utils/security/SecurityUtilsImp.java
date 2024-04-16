package plazoleta.adapters.driven.jpa.msql.utils.security;

import java.security.SecureRandom;
public class SecurityUtilsImp {

    private SecurityUtilsImp() {
        throw new IllegalStateException("Utility class");
    }

    public static String generateSecurityPin() {
        SecureRandom random = new SecureRandom();
        int pin = random.nextInt(9000) + 1000;
        return String.valueOf(pin);
    }
}
