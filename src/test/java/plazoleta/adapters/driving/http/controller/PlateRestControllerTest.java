package plazoleta.adapters.driving.http.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import plazoleta.adapters.driving.http.dto.request.plate.AddPlateRequest;
import plazoleta.adapters.driving.http.mapper.IPlateResquestMapper;
import plazoleta.domain.api.IPlateServicePort;
import plazoleta.domain.model.plate.Category;
import plazoleta.domain.model.plate.Plate;
import plazoleta.domain.model.restaurant.Restaurant;
import plazoleta.domain.model.restaurant.User;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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

        Plate plateInput =
                new Plate(1, "Hamburguesa",
                        new Category(), "Deliciosa hamburguesa",
                        1500, 1, "https://ejemplo.com/hamburguesa.jpg");

        when(plateServicePort.create(plateRequestMapper.toPlate(any(AddPlateRequest.class)))).thenReturn(plateInput);

        mockMcv.perform(post("/plate/create")
                        .contentType(MediaType.valueOf("application/json"))
                        .content(plateJPA))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("retornar status 400 al recibir valores nulos")
    void saveError() throws Exception {
        String platetJSON = "{\\n\" +\n" +
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
                        .content(platetJSON))
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

    @Test
    void testUpdatePlate_Success() throws Exception {
        String plateUpdateJson = "{\n" +
                "        \"name\": \" Otro Plato de ejemplo\",\n" +
                "        \"categoryId\": {\n" +
                "            \"id\": 2000\n" +
                "        },\n" +
                "        \"description\": \"Otra Descripción del plato de ejemplo\",\n" +
                "        \"price\": 1113,\n" +
                "        \"restaurantId\": 1,\n" +
                "        \"imageUrl\": \"Otro https://ejemplo.com/imagen.jpg\",\n" +
                "        \"active\": true\n" +
                "    }";

        Plate plateInput = new Plate(1, "Hamburguesa",
                new Category(), "Deliciosa hamburguesa",
                1500, 1, "https://ejemplo.com/hamburguesa.jpg");;
        // Puedes inicializar plateInput según tus necesidades

        when(plateServicePort.update(plateRequestMapper.toPlate(any(AddPlateRequest.class)), 15)).thenReturn(plateInput);

        // Act & Assert
        mockMcv.perform(put("/plate/15")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(plateUpdateJson))
                .andExpect(status().isOk());
    }


}