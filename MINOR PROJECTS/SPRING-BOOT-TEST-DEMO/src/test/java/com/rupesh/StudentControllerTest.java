package com.rupesh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rupesh.entity.Student;
import com.rupesh.service.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
public class StudentControllerTest {

    /**
     * @Mock -> If our test case does not dependent on Spring container Bean then use @Mock
     * @MockMvc ->  If our test case dependent Spring container then use @MockBean
     */

    @MockBean
    private StudentService studentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Student student;

    @BeforeEach
    void init(){
        student = new Student();student.setFirstName("rupesh");
        student.setLastName("dulal");
        student.setEmail("sharma.rupesh75@gmail.com");

    }

    @Test
    @DisplayName("save student controller test")
    void shouldCreateNewStudent() throws Exception {
        Mockito.when(studentService.save(any(Student.class))).thenReturn(student);
        this.mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isCreated());
//                .andExpect(jsonPath("$.firstName", is(student.getFirstName())));
    }

    @Test
    @DisplayName("get all student controller tes")
    void shouldDisplayAllStudents() throws Exception {
        Mockito.when(studentService.save(any(Student.class))).thenReturn(student);
        this.mockMvc.perform(get("/users"))
                .andExpect(status().isOk());

    }

}
