package com.hellcaster.blogging.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hellcaster.blogging.entity.Blog;
import com.hellcaster.blogging.model.model_blog.UpdateBlogRequest;
import com.hellcaster.blogging.service.BlogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(value = BlogController.class)
public class BlogControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BlogService blogService;

    @Test
    public void testUpdateBlogValidationFail() throws Exception {
        UpdateBlogRequest updateBlogRequest = UpdateBlogRequest.builder()
                                                                .blogId("")
                                                                .title("Title")
                                                                .description("Description")
                                                                .publish(true)
                                                                .userId("1001")
                                                                .build();
        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/blogs/v1/update")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBlogRequest))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
        assertEquals("{\"message\":\"Blog Id cannot be blank\"}", response.getContentAsString());
    }
    @Test
    public void testUpdateBlogRecordNotFound() throws Exception {
        UpdateBlogRequest updateBlogRequest = UpdateBlogRequest.builder()
                                                                .blogId("101")
                                                                .title("Title")
                                                                .description("Description")
                                                                .publish(true)
                                                                .userId("1001")
                                                                .build();
        when(blogService.updateBlog(any(UpdateBlogRequest.class))).thenReturn(null);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/blogs/v1/update")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBlogRequest))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
        assertEquals("{\"message\":\"Blog with given ID not Found\"}", response.getContentAsString());
    }

    @Test
    public void testUpdateBlogSuccess() throws Exception {
        UpdateBlogRequest updateBlogRequest = UpdateBlogRequest.builder()
                                                                .blogId("101")
                                                                .title("Title")
                                                                .description("Description")
                                                                .publish(true)
                                                                .userId("1001")
                                                                .build();
        Blog bLog = Blog.builder().publish(true).blogId("101").title("Title").description("Description").userId("1001").build();
        when(blogService.updateBlog(any(UpdateBlogRequest.class))).thenReturn(bLog);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put("/blogs/v1/update")
                .accept(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateBlogRequest))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        assertEquals(HttpStatus.OK.value(), response.getStatus());

    }
}
