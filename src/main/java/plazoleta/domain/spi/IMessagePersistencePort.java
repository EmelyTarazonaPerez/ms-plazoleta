package plazoleta.domain.spi;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;

public interface IMessagePersistencePort {
     String sendMSM (String numberClient, String pin);
}
