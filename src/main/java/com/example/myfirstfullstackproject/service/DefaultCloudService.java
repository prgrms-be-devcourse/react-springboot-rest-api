package com.example.myfirstfullstackproject.service;

import com.example.myfirstfullstackproject.model.Category;
import com.example.myfirstfullstackproject.model.Cloud;
import com.example.myfirstfullstackproject.repository.CloudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultCloudService implements CloudService {

    private final CloudRepository cloudRepository;

    @Autowired
    public DefaultCloudService(CloudRepository cloudRepository) {
        this.cloudRepository = cloudRepository;
    }

    @Override
    public Cloud createCloud(String cloudName, Category category, long price, int users, int storage, String src) {
        return cloudRepository.insert(Cloud.builder()
                .cloudName(cloudName)
                .category(category)
                .price(price)
                .users(users)
                .storage(storage)
                .img(src)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build());
    }

    @Override
    public List<Cloud> getCloudsByCategory(Category category) {
        return cloudRepository.findCloudsByCategory(category);
    }

    @Override
    public List<Cloud> getAllClouds() {
        return cloudRepository.findAll();
    }

}
