package plazoleta.adapters.driven.utils.consumer;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.domain.model.restaurant.User;
import plazoleta.domain.spi.IExternalPersistenceApi;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExternalAdapterApi implements IExternalPersistenceApi {
    private final RestTemplate restTemplate;

    @Override
    public User getRolByIdUser (int id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ token);

        return restTemplate.exchange("http://localhost:8080/auth/get/user?id=" + id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                User.class).getBody();
    }

    @Override
    public void sendTraceability (User client,
                                  String previousState,
                                  String stateNew,
                                  User employeer,
                                  LocalDate date,
                                  int idOrder) {

   /*     TraceabilityData data = new TraceabilityData();
        HttpEntity<TraceabilityData> requestEntity = new HttpEntity<>(data);

        restTemplate.exchange("http://localhost:8085/trazabilidad",
                HttpMethod.POST,
                requestEntity,
                Void.class
                );
    } */
    }

}
