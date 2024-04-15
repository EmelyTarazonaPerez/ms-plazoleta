package plazoleta.adapters.driving.http;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import plazoleta.adapters.driven.jpa.msql.entity.restaurant.UserEntity;

@Service
@RequiredArgsConstructor
public class ConsumerUser {
    private final RestTemplate restTemplate;

    public UserEntity getRolByIdUser (int id, String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer "+ token);

        return restTemplate.exchange("http://localhost:8080/auth/get/user?id=" + id,
                HttpMethod.GET,
                new HttpEntity<>(headers),
                UserEntity.class).getBody();
    }
}
