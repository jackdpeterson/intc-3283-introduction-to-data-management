package edu.northwestu.intc3283.datasourcestarter.controller;

import edu.northwestu.intc3283.datasourcestarter.entity.Entry;
import edu.northwestu.intc3283.datasourcestarter.repository.EntryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Optional;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IndexController.class)
@AutoConfigureMockMvc
class IndexControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EntryRepository repository;

    @Test
    public void testIndexAction() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("entries")));

    }

    @Test
    public void testViewOneEntryAction() throws Exception {
        Entry entryResponse = new Entry();
        entryResponse.setId(1L);
        entryResponse.setName("test");
        entryResponse.setEmail("test@example.com");
        when(this.repository.findById(1L)).thenReturn(Optional.of(entryResponse));
        this.mockMvc.perform(MockMvcRequestBuilders.get("/entries/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("entry"))
                .andExpect(content().string(containsString("Entry Details")));

    }

    @Test
    public void testCreatingEntryFailsValidation() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/entries")
                        .param("name", "test")
                        .param("email", "badEmail")
                )
                .andExpect(status().isBadRequest())
                .andExpect(view().name("form"))
                .andExpect(content().string(containsString("must be a well-formed email address")));
    }

    @Test
    public void testCreatingEntrySucceedsAndCallsDatabaseForPersistence() throws Exception {
        final Entry pretendSavedEntity = new Entry();
        pretendSavedEntity.setId(1L);
        when(this.repository.save(new Entry())).thenReturn(pretendSavedEntity);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/entries")
                        .param("name", "test5")
                        .param("email", "test@example.com")
                        .param("favoriteColor", "blue")
                        .param("singleSelect", "1")
                        .param("date", "2024-01-01")
                        .param("selectedItems", "1")
                        .param("selectedItems", "2")
                        .param("datetime", "2024-01-01T00:00:00")
                )
                .andExpect(status().is3xxRedirection());
    }

}