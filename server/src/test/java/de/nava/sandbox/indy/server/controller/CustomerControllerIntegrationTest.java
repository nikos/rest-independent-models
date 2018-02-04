package de.nava.sandbox.indy.server.controller;

import de.nava.sandbox.indy.server.IndyServerApplication;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IndyServerApplication.class)
public class CustomerControllerIntegrationTest {

  // @InjectMocks
  // private CustomerController controller;

  @Autowired
  private WebApplicationContext context;

  private MockMvc mvc;

  @Before
  public void initTests() {
    MockitoAnnotations.initMocks(this);
    mvc = MockMvcBuilders.webAppContextSetup(context).build();
  }

  @Test
  public void shouldRetrieveAllEntries() throws Exception {
    mvc.perform(get("/api/customers")
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$", hasSize(2)))
      .andExpect(jsonPath("$..firstname", contains("Ada", "Grace")));
  }

  @Test
  public void shouldRetrieveOneEntry() throws Exception {
    mvc.perform(get("/api/customers/42")
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(jsonPath("$.id", equalTo(42)))
      .andExpect(jsonPath("$.firstname", equalTo("Ada")))
      .andExpect(jsonPath("$.lastname", equalTo("Lovelace")));
  }

  @Test
  public void shouldNotRetrieveMissingEntry() throws Exception {
    mvc.perform(get("/api/customers/23")
      .accept(MediaType.APPLICATION_JSON))
      .andExpect(status().isNotFound());
  }

  @Test
  public void shouldCreateNewEntry() throws Exception {
    mvc.perform(post("/api/customers")
      .content("{\"id\": 44, \"firstname\": \"Foo\", \"lastname\": \"Bar\"}")
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isCreated())
      .andExpect(jsonPath("$.id", equalTo(44)));

    mvc.perform(delete("/api/customers/44")
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isOk());
  }

  @Test
  public void tryToCreateIncompleteEntry() throws Exception {
    mvc.perform(post("/api/customers")
      .content("{\"id\": 45, \"firstname\": \"Foo\"}")  // last name missing
      .contentType(MediaType.APPLICATION_JSON)
      .accept(MediaType.APPLICATION_JSON))
      .andDo(print())
      .andExpect(status().isBadRequest());
  }

  @Test
  public void tryToDeleteMissingEntry() throws Exception {
    mvc.perform(delete("/api/customers/4711"))
      .andExpect(status().isNotFound());
  }

}