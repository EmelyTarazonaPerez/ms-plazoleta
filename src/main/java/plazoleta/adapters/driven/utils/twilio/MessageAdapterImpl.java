package plazoleta.adapters.driven.utils.twilio;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.springframework.stereotype.Service;
import plazoleta.domain.spi.IMessagePersistencePort;

@Service
public class MessageAdapterImpl implements IMessagePersistencePort {
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN ="";
    public static final String NUMBER = "";
    @Override
    public String sendMSM (String numberClient, String pin) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+57" + numberClient),
                        new com.twilio.type.PhoneNumber(NUMBER),
                        "Su pedido ya esta listo, puede reclamar con el siguiente pin " + pin)
                .create();

        return message.getSid();
    }
}
