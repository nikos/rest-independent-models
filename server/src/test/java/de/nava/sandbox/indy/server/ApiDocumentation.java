package de.nava.sandbox.indy.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IndyServerApplication.class)
public class ApiDocumentation {

  @Rule
  public JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation(
    "target/generated-snippets");

  @Autowired
  private WebApplicationContext context;

  @Autowired
  private ObjectMapper objectMapper;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(context)
      .apply(documentationConfiguration(restDocumentation))
      //.alwaysDo(document("{method-name}",
      //  preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())
      //))
      .build();
  }

  @Test
  public void customersListExample() throws Exception {
    // this.customerRepository.deleteAll();

    // createCustomer(...
    // createCustomer (...

    mockMvc.perform(get("/api/customers"))
      .andExpect(status().isOk())
      .andDo(document("customers-list-example",
        //links(
        //  linkWithRel("self").description("Canonical link for this resource"),
        //  linkWithRel("profile").description("The ALPS profile for this resource")),

        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),

        relaxedResponseFields(
          fieldWithPath("[]").description("JSON Array of Customers")

          /*fieldWithPath("[].id").description("The mobile suit's id"),
          fieldWithPath("[].firstname").description("The mobile suit's model name"),
          fieldWithPath("[].lastname").description("Array of mobile suit's weapons")*/

        )
      ));
    //subsectionWithPath("_embedded.customers").description("An array of <<resources-customer, Customer resources>>"),
    //subsectionWithPath("_links").description("<<resources-tags-list-links, Links>> to other resources"))));
  }

  // ~~~ Baeldung

  @Test
  public void customerGetExample() throws Exception {

    mockMvc.perform(get("/api/customers/42"))
      .andExpect(status().isOk())
      .andDo(document("customer-get-example",
        preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        responseFields(
          fieldWithPath("id").description("The id of the input"),
          fieldWithPath("firstname").description("The title of the input"),
          fieldWithPath("lastname").description("The body of the input")
        )
      ));
  }

  @Test
  public void customerCreateExample() throws Exception {
    Map<String, Object> crud = new HashMap<>();
    crud.put("id", 2L);
    crud.put("firstname", "Sample Model");
    crud.put("lastname", "http://www.nava.de/");

    mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(crud)))
      .andExpect(status().isCreated())
      .andDo(document("customer-create-example", preprocessRequest(prettyPrint()),
        preprocessResponse(prettyPrint()),
        requestFields(fieldWithPath("id").description("The id of the input"),
          fieldWithPath("firstname").description("The title of the input"),
          fieldWithPath("lastname").description("The body of the input")
        )
      ));
  }

  @Test
  public void customerDeleteExample() throws Exception {
    Map<String, Object> customer = new HashMap<>();
    customer.put("id", 23L);
    customer.put("firstname", "Sample Model");
    customer.put("lastname", "http://www.nava.de/");

    mockMvc.perform(post("/api/customers").contentType(MediaType.APPLICATION_JSON)
      .content(objectMapper.writeValueAsString(customer)))
      .andExpect(status().isCreated());

    mockMvc.perform(delete("/api/customers/{id}", 23))
      .andExpect(status().isOk())
      .andDo(document("customer-delete-example",
        pathParameters(parameterWithName("id").description("The id of the input to delete"))
      ));
  }

}
