package ua.Nazar.Rep;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import ua.Nazar.Rep.controller.RecordController;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/messages-list-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/messages-list-after.sql", "/create-user-after.sql"},
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@WithUserDetails(value = "main_admin_test")
public class RecordControllerTest {
    @Autowired
    private RecordController controller;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mainPageTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(
                        xpath("//*[@id='navbarSupportedContent']/div")
                                .string("main_admin_test"));
    }

    @Test
    public void messageListTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='records-list']/tr")
                        .nodeCount(4));
    }

    @Test
    public void filterMessageTest() throws Exception {
        this.mockMvc.perform(get("/").param("filter", "0"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(
                        xpath("//*[@id='records-list']/tr")
                                .nodeCount(3))
                .andExpect(
                        xpath("//*[@id='records-list']/tr[@data-id='1']/td[@data-type='date']")
                                .exists())
                .andExpect(
                        xpath("//*[@id='records-list']/tr[@data-id='1']/td[@data-type='temperature']")
                                .exists())
                .andExpect(xpath("//*[@id='records-list']/tr[@data-id='1']/td[@data-type='windSpeed']").exists())
                .andExpect(xpath("//*[@id='records-list']/tr[@data-id='1']/td[@data-type='windAngle']").exists())
                .andExpect(xpath("//*[@id='records-list']/tr[@data-id='1']/td[@data-type='pressure']").exists());
    }

    @Test
    public void addMessageToListTest() throws Exception {
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='records-list']/tr").nodeCount(4));

        MockHttpServletRequestBuilder multipart = multipart("/user-records/1")
                .param("date", "1")
                .param("temperature", "2")
                .param("windSpeed", "3")
                .param("windAngle", "4")
                .param("pressure", "5")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(redirectedUrl("/user-records/1"));

        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[@id='records-list']/tr").nodeCount(5))
                .andExpect(xpath("//*[@id='records-list']/tr[@data-id='10']").exists())
                .andExpect(xpath("//*[@id='records-list']/tr[@data-id='10']/td[@data-type='date']/a").string("1"))
                .andExpect(xpath("//*[@id='records-list']/tr[@data-id='10']/td[@data-type='temperature']/a").string("2"))
                .andExpect(xpath("//*[@id='records-list']/tr[@data-id='10']/td[@data-type='windSpeed']/a").string("3"))
                .andExpect(xpath("//*[@id='records-list']/tr[@data-id='10']/td[@data-type='windAngle']/a").string("4"))
                .andExpect(xpath("//*[@id='records-list']/tr[@data-id='10']/td[@data-type='pressure']/a").string("5"));

    }
}
