package plazoleta.adapters.driving.http.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import plazoleta.adapters.driving.http.dto.request.plate.AddPlateRequest;
import plazoleta.adapters.driving.http.mapper.IPlateResquestMapper;
import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.model.plate.Category;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.model.restaurant.Restaurant;
import plazoleta.domain.model.restaurant.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PlateRestControllerTest {

    @Mock
    IPlateServicePort plateServicePort;
    @Mock
    IPlateResquestMapper plateRequestMapper;
    @InjectMocks
    PlateRestController plateRestController;
    private MockMvc mockMcv;
    @BeforeEach
    void setUp() {
        mockMcv = MockMvcBuilders.standaloneSetup(plateRestController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void save() throws Exception {
        String plateJPA = "{\n" +
                "    \"name\": \"Plato de ejemplo\",\n" +
                "    \"categoryId\": {\n" +
                "        \"id\":1\n" +
                "    },\n" +
                "    \"description\": \"Descripción del plato de ejemplo\",\n" +
                "    \"price\": 628,\n" +
                "    \"restaurantId\": {\n" +
                "        \"id\": 1\n" +
                "    },\n" +
                "    \"imageUrl\": \"https://ejemplo.com/imagen.jpg\"\n" +
                "}";

        Restaurant restaurant = new Restaurant(1,"rest", "any",
                new User(), "+573104922805", "logo", "ni"
        );
        Plate plateInput =
                new Plate(1, "Hamburguesa",
                        new Category(), "Deliciosa hamburguesa",
                        1500, restaurant, "https://ejemplo.com/hamburguesa.jpg");

        when(plateServicePort.create(plateRequestMapper.toPlate(any(AddPlateRequest.class)))).thenReturn(plateInput);

        mockMcv.perform(post("/plate/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(plateJPA))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("retornar status 400 al recibir valores nulos")
    void saveError() throws Exception {
        String restaurantJSON = "{\\n\" +\n" +
                "                \"    \\\"name\\\": null,\\n\" +\n" +
                "                \"    \\\"categoryId\\\": {\\n\" +\n" +
                "                \"        \\\"id\\\":1\\n\" +\n" +
                "                \"    },\\n\" +\n" +
                "                \"    \\\"description\\\": \\\"Descripción del plato de ejemplo\\\",\\n\" +\n" +
                "                \"    \\\"price\\\": 628,\\n\" +\n" +
                "                \"    \\\"restaurantId\\\": {\\n\" +\n" +
                "                \"        \\\"id\\\": 1\\n\" +\n" +
                "                \"    },\\n\" +\n" +
                "                \"    \\\"imageUrl\\\": \\\"https://ejemplo.com/imagen.jpg\\\"\\n\" +\n" +
                "                \"}";


        mockMcv.perform(post("/plate/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(restaurantJSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("retornar status 400 al recibir precios negativos")
    void saveErrorPrice() throws Exception {
        String plateJPA = "{\n" +
                "    \"name\": \"Plato de ejemplo\",\n" +
                "    \"categoryId\": {\n" +
                "        \"id\":1\n" +
                "    },\n" +
                "    \"description\": \"Descripción del plato de ejemplo\",\n" +
                "    \"price\": -628,\n" +
                "    \"restaurantId\": {\n" +
                "        \"id\": 1\n" +
                "    },\n" +
                "    \"imageUrl\": \"https://ejemplo.com/imagen.jpg\"\n" +
                "}";


        mockMcv.perform(post("/plate/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(plateJPA))
                .andExpect(status().isBadRequest());
    }
}