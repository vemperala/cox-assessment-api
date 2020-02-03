package com.assessment.cox.resource;

/**
 * Created by akhilesh on 2/2/20.
 */
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import com.assessment.cox.dto.LocationDTO;
import com.assessment.cox.dto.StoreDTO;
import com.assessment.cox.service.StoresService;
import com.fasterxml.jackson.databind.ObjectMapper;
@RunWith(SpringRunner.class)
@WebMvcTest(StoreResource.class)
public class StoreResourceTest {
  @Autowired
  private MockMvc mvc;
  @Autowired
  private ObjectMapper objectMapper;
  @MockBean
  private StoresService storesService;
  @Test
  public void testGetAllStores()  throws Exception {
    List<StoreDTO> allStores = Arrays.asList(createStoreDTO());
    when(storesService.getAllStores()).thenReturn(allStores);
    mvc.perform(get("/store/list")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(1)))
        .andExpect(jsonPath("$[0].name", is("Stevens Point")))
        .andExpect(jsonPath("$[0].services",hasSize(2)));
  }
  @Test
  public void testStoryById()  throws Exception {
    when(storesService.findStoreByStoreId(100l)).thenReturn(createStoreDTO());
    mvc.perform(get("/store/id/100")
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.city", is("Plover")))
        .andExpect(jsonPath("$.name", is("Stevens Point")))
        .andExpect(jsonPath("$.services",hasSize(2)));
  }
  @Test
  public void testCreateStore()  throws Exception {
    StoreDTO storeDto = createStoreDTO();
    when(storesService.createNewStore(Mockito.any(StoreDTO.class))).thenReturn(storeDto);
    final String entityJson = objectMapper.writeValueAsString(storeDto);
    mvc.perform(post("/store/create").content(entityJson)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is("Stevens Point")))
        .andExpect(jsonPath("$.services",hasSize(2)));
  }
  @Test
  public void testUpdateStore()  throws Exception {
    StoreDTO storeDto = createStoreDTO();
    when(storesService.updateStore(Mockito.any(StoreDTO.class))).thenReturn(storeDto);
    final String entityJson = objectMapper.writeValueAsString(storeDto);
    mvc.perform(put("/store/update").content(entityJson)
        .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.city", is("Plover")))
        .andExpect(jsonPath("$.name", is("Stevens Point")))
        .andExpect(jsonPath("$.services",hasSize(2)));
  }
  public StoreDTO createStoreDTO() {
    StoreDTO store = new StoreDTO();
    store.setCity("Plover");
    store.setAddress("1145 Commons Cir");
    store.setName("Stevens Point");
    store.setType("BigBox");
    store.setState("WI");
    store.setZip("54467");
    LocationDTO location = new LocationDTO();
    location.setLat(new BigDecimal(44.522335));
    location.setLon(new BigDecimal(-89.515335));
    store.setLocation(location);
    store.setHours("Mon: 10-9:30; Tue: 10-9:30; Wed: 10-9:30; Thurs: 10-9:30; Fri: 10-9:30; Sat: 10-9:30; Sun: 11-8");
    String[] services = { "Best Buy Mobile", "Samsung Experience Shop"};
    store.setServices(Arrays.asList(services));
    return store;
  }
}
