package plazoleta.adapters.driven.jpa.msql.utils.twilio;


import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
public class TwilioServiceImpl {
    private TwilioServiceImpl() {throw new IllegalStateException("Utility class");}
    public static final String ACCOUNT_SID = "";
    public static final String AUTH_TOKEN ="";
    public static final String NUMBER = "";
    public static String sendMSM (String numberClient, String pin) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+57" + numberClient),
                        new com.twilio.type.PhoneNumber(NUMBER),
                        "Su pedido ya esta listo, puede reclamar con el siguiente pin " + pin)
                .create();

        return message.getSid();
    }
}
