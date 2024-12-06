package edu.northwestu.intc3283.datasourcestarter.tasks.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.northwestu.intc3283.datasourcestarter.tasks.entity.Task;
import edu.northwestu.intc3283.datasourcestarter.tasks.repository.TasksRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;


@WebMvcTest
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs(outputDir = "target/generated-snippets")
class TasksApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TasksRepository taskRepository;

    @Test
    public void getTaskProvides200OkWithaValidId() throws Exception {
        Task mockedTaskResponse = new Task();
        mockedTaskResponse.setId(1L);
        mockedTaskResponse.setTitle("title");
        mockedTaskResponse.setDescription("description");
        mockedTaskResponse.setStatus("PENDING");
        mockedTaskResponse.setCreatedAt(Instant.now());
        when(this.taskRepository.findById(1L)).thenReturn(Optional.of(mockedTaskResponse));
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/tasks/1")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        resultActions.andDo(document("tasks/get-one-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }

    @Test
    public void getTasksProvides200Ok() throws Exception {
        Task mockedTaskResponse = new Task();
        mockedTaskResponse.setId(1L);
        mockedTaskResponse.setTitle("title");
        mockedTaskResponse.setDescription("description");
        mockedTaskResponse.setStatus("PENDING");
        mockedTaskResponse.setCreatedAt(Instant.now());

        // fix the types here.
        Page<Task> expectedPageResponse = new PageImpl<>(List.of(mockedTaskResponse));
        when(this.taskRepository.findAll(any(Pageable.class))).thenReturn(expectedPageResponse);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/tasks?page=0&size=1")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        resultActions.andDo(document("tasks/get-collection-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }


    @Test
    public void createANewTask200Ok() throws Exception {
        // HTTP POST to /tasks
        // body will be a JSON payload shaped like a Task.
        // We expect a 200 OK

        Task taskRequest = new Task();
        taskRequest.setTitle("title");
        taskRequest.setDescription("description");

        ObjectMapper objectMapper = new ObjectMapper();
        String taskRequestJson = objectMapper.writeValueAsString(taskRequest);

        Task taskResponse = new Task();
        taskResponse.setId(1L);
        taskResponse.setTitle(taskRequest.getTitle());
        taskResponse.setDescription(taskRequest.getDescription());
        taskResponse.setStatus("PENDING");
        taskResponse.setCreatedAt(Instant.now());

        when(this.taskRepository.save(any(Task.class)))
                .thenReturn(taskResponse);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/tasks")
                        .contentType("application/json")
                        .content(taskRequestJson)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        resultActions.andDo(document("tasks/create-200",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));

    }


    @Test
    public void createANewTask400BadRequestWhenTitleIsTooShort() throws Exception {
        // HTTP POST to /tasks
        // body will be a JSON payload shaped like a Task.
        // We expect a 200 OK

        Task taskRequest = new Task();
        taskRequest.setTitle("one");
        taskRequest.setDescription("description");

        ObjectMapper objectMapper = new ObjectMapper();
        String taskRequestJson = objectMapper.writeValueAsString(taskRequest);

        Task taskResponse = new Task();
        taskResponse.setId(1L);
        taskResponse.setTitle(taskRequest.getTitle());
        taskResponse.setDescription(taskRequest.getDescription());
        taskResponse.setStatus("PENDING");
        taskResponse.setCreatedAt(Instant.now());

        when(this.taskRepository.save(any(Task.class)))
                .thenReturn(taskResponse);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.post("/tasks")
                        .contentType("application/json")
                        .content(taskRequestJson)
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

        resultActions.andDo(document("tasks/create-400",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));
    }

    @Test
    public void deleteTask() throws Exception {
        when(this.taskRepository.existsById(any())).thenReturn(true);

        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.delete("/tasks/1")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
        resultActions.andDo(document("tasks/delete-task-204",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));

        // verify that this was called exactly 1 time
        verify(this.taskRepository, times(1)).deleteById(1L);

    }


    @Test
    public void getTaskProvides404NotFoundWhenRepositoryReturnsEmptyOptional() throws Exception {
        when(this.taskRepository.findById(1L)).thenReturn(Optional.empty());
        ResultActions resultActions = mockMvc.perform(RestDocumentationRequestBuilders.get("/tasks/1")
                        .accept("application/json"))
                .andExpect(MockMvcResultMatchers.status().isNotFound());

        resultActions.andDo(document("tasks/get-one-404",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint())
        ));

    }


}