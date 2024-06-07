package plazoleta.adapters.driving.http.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import plazoleta.adapters.driven.jpa.msql.entity.RolEntity;
import plazoleta.adapters.driven.jpa.msql.entity.UserEntity;
import plazoleta.adapters.driven.utils.consumer.ExternalAdapterApi;
import plazoleta.adapters.driving.http.dto.request.AddRestaurantRequest;
import plazoleta.adapters.driving.http.dto.response.RestaurantResponse;
import plazoleta.adapters.driving.http.mapper.IRestaurantRequestMapper;
import plazoleta.adapters.driving.http.mapper.IRestaurantResponseMapper;
import plazoleta.domain.api.IRestaurantServicePort;
import plazoleta.domain.model.restaurant.Restaurant;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RestaurantRestControllerTest {

    @Mock
    private IRestaurantServicePort restaurantServicePort;
    @Mock
    private IRestaurantRequestMapper restaurantRequestMapper;
    @Mock
    private IRestaurantResponseMapper restaurantResponseMapper;
    @Mock
    private ExternalAdapterApi consumerUser;

    @InjectMocks
    RestaurantRestController restaurantRestController;
    private MockMvc mockMcv;

    @BeforeEach
    void setUp() {
        mockMcv = MockMvcBuilders.standaloneSetup(restaurantRestController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void save() throws Exception {
        String restaurantJSON = "{\n" +
                "    \"name\": \"restaurant\",\n" +
                "    \"address\": \"calle 7 #17\",\n" +
                "    \"ownerId\": 1,\n" +
                "    \"phone\": \"57109056853\",\n" +
                "    \"urlLogo\": \"djasdnjasdhasd\",\n" +
                "    \"nit\": \"25468\"\n" +
                "}\n";

        Restaurant restaurant = new Restaurant(1,"rest", "any",
                1, "573104922805", "logo", "nit");

        UserEntity userEntity = new UserEntity(1, "name", "any", "1056",
                "+57893156", LocalDate.now(), "gmail", "s",new RolEntity(2,"propietario", "x"));


        when(consumerUser.getRolByIdUser(userEntity.getIdUser(), "TOKEN_DE_PRUEBA")).thenReturn(userEntity);
        when(restaurantRequestMapper.toRestaurant(any(AddRestaurantRequest.class))).thenReturn(restaurant);
        when(restaurantServicePort.createRestaurant(restaurant)).thenReturn(restaurant);

        mockMcv.perform(post("/restaurant/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(restaurantJSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer TOKEN_DE_PRUEBA"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("retornar status 400 al registrar un phone incorrectamente")
    void savePhoneError() throws Exception {
        String restaurantJSON = "{\n" +
                "    \"name\": \"restaurant\",\n" +
                "    \"address\": \"calle 7 #17\",\n" +
                "    \"ownerId\": {\n" +
                "        \"idUser\": 2\n" +
                "    },\n" +
                "    \"phone\": \"p571z09056853\",\n" +
                "    \"urlLogo\": \"djasdnjasdhasd\"\n" +
                "}\n";


        mockMcv.perform(post("/restaurant/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(restaurantJSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("retornar status 400 al recibir valores nulos")
    void saveError() throws Exception {
        String restaurantJSON = "{\n" +
                "    \"name\": null,\n" +
                "    \"address\": null,\n" +
                "    \"ownerId\": {\n" +
                "        \"idUser\": 2\n" +
                "    },\n" +
                "    \"phone\": \"p571z09056853\",\n" +
                "    \"urlLogo\": \"djasdnjasdhasd\"\n" +
                "}\n";


        mockMcv.perform(post("/restaurant/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(restaurantJSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getAll() throws Exception {
        List<RestaurantResponse> restaurantResponses = new ArrayList<>();
        List<Restaurant> restaurants = new ArrayList<>();

        when(restaurantServicePort.getAll(0,5,true)).thenReturn(restaurants);
        when(restaurantResponseMapper.toResponseList(restaurants)).thenReturn(restaurantResponses);

        mockMcv.perform(MockMvcRequestBuilders.get("/restaurant/getAll")
                        .param("sort", "true")
                        .param("size", "5")
                        .param("page", "0"))
                .andExpect(status().isOk());
    }
}