package com.example.myfirstfullstackproject.service;

import com.example.myfirstfullstackproject.model.Category;
import com.example.myfirstfullstackproject.model.Cloud;
import com.example.myfirstfullstackproject.repository.CloudRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringJUnitConfig
class DefaultCloudServiceTest {

    @Mock
    private CloudRepository cloudRepository;

    @InjectMocks
    private DefaultCloudService defaultCloudService;

    private Cloud testItem;

    @BeforeEach
    void prepare() {
        testItem = Cloud.builder()
                .cloudName("TestLamda")
                .category(Category.COMPUTING)
                .price(10)
                .users(10)
                .storage(20)
                .img("lamda.png")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("주어진 조건에 따라 클라우드 객체를 생성하여 cloudRepository.insert()를 호출한다.")
    void testCreateCloud() {
        // given
        when(cloudRepository.insert(any(Cloud.class)))
                .thenReturn(testItem);

        // when
        Cloud insertedCloud = defaultCloudService.createCloud(
                "TestLamda", Category.COMPUTING, 10, 10, 20, "lamda.png");

        // then
        assertThat(insertedCloud).isEqualTo(testItem);
        verify(cloudRepository, times(1)).insert(any(Cloud.class));
    }

    @Test
    @DisplayName("주어진 카테고리를 이용해서 cloudRepository.findCloudsByCategory()를 호출한다.")
    void testGetCloudsByCategory() {
        // given
        when(cloudRepository.findCloudsByCategory(any(Category.class)))
                .thenReturn(List.of(testItem));

        // when
        List<Cloud> cloudsByCategory = defaultCloudService.getCloudsByCategory(Category.DRIVE);

        // then
        assertThat(cloudsByCategory).containsOnly(testItem);
        verify(cloudRepository, times(1)).findCloudsByCategory(any(Category.class));
    }

    @Test
    @DisplayName("저장된 모든 클라우드를 반환하는 cloudRepository.findAll()을 호출한다.")
    void testGetAllClouds() {
        // given
        when(cloudRepository.findAll())
                .thenReturn(List.of(testItem));

        // when
        List<Cloud> allClouds = defaultCloudService.getAllClouds();

        //then
        assertThat(allClouds).containsOnly(testItem);
        verify(cloudRepository, times(1)).findAll();
    }

}