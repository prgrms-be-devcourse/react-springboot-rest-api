package com.example.myfirstfullstackproject.controller;

import com.example.myfirstfullstackproject.controller.message.BusinessStatus;
import com.example.myfirstfullstackproject.model.Category;
import com.example.myfirstfullstackproject.model.Cloud;
import com.example.myfirstfullstackproject.service.CloudService;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitConfig
//@WebMvcTest
//@AutoConfigureMockMvc
class CloudRestControllerTest {

    @Mock
    private CloudService cloudService;

    @InjectMocks
    private CloudRestController cloudRestController;

    private MockMvc mockMvc;

//    private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(cloudRestController)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .build();
    }

    @Test
    @DisplayName("카테고리를 포함한 uri 요청시 해당 카테고리의 클라우드 상품을 반환한다")
    void testShowCloudList_1() throws Exception {
        // given
        List<Cloud> mockResults = cloudList();
        doReturn(mockResults).when(cloudService)
                .getCloudsByCategory(any(Category.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/clouds?category=DRIVE")
                        .accept("application/json"));

        // then
        MvcResult firstMvcResult = resultActions.andExpect(status().isOk()).andReturn();
        JSONObject jsonObject = new JSONObject(firstMvcResult.getResponse().getContentAsString());
        assertThat(BusinessStatus.valueOf(String.valueOf(jsonObject.get("status"))))
                .isEqualTo(BusinessStatus.CLOUDS_BY_CATEGORY_RETURNED);

        verify(cloudService, times(0)).getAllClouds();
        verify(cloudService, times(1)).getCloudsByCategory(Category.DRIVE);
    }

    @Test
    @DisplayName("카테고리가 없을 경우 모든 클라우드 상품을 반환한다.")
    void testShowCloudList_2() throws Exception {
        // given
        List<Cloud> mockResults = cloudList();
        doReturn(mockResults).when(cloudService)
                .getAllClouds();

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/clouds")
                        .accept("application/json"));

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertThat(BusinessStatus.valueOf(String.valueOf(jsonObject.get("status"))))
                .isEqualTo(BusinessStatus.ALL_CLOUDS_RETURNED);

        verify(cloudService, times(1)).getAllClouds();
        verify(cloudService, times(0)).getCloudsByCategory(any(Category.class));
    }

    @Test
    @DisplayName("존재하지 않는 카테고리에 대한 요청이 들어올시 정해진 예외 처리를 수행한다.")
    void testShowCloudList_3() throws Exception {
        // given
        List<Cloud> mockResult = cloudList();
        doReturn(mockResult).when(cloudService)
                .getCloudsByCategory(any(Category.class));

        // when
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders
                        .get("/api/v1/clouds?category=NON-EXISTENT-ITEM")
                        .accept("application/json"));

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isBadRequest()).andReturn();
        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());
        assertThat(BusinessStatus.valueOf(String.valueOf(jsonObject.get("status"))))
                .isEqualTo(BusinessStatus.ILLEGAL_CATEGORY_REQUEST);

        verify(cloudService, times(0)).getAllClouds();
        verify(cloudService, times(0)).getCloudsByCategory(any(Category.class));
    }

    private List<Cloud> cloudList() {
        List<Cloud> list = new ArrayList<>();

        for (int i = 1; i <= 5; i++)
            list.add(Cloud.builder()
                    .cloudId(i)
                    .cloudName("test" + i)
                    .category(Category.DRIVE)
                    .price(100)
                    .users(100)
                    .storage(100)
                    .img("test.png")
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build());

        return list;
    }

}