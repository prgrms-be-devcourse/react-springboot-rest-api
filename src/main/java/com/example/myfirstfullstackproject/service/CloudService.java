package com.example.myfirstfullstackproject.service;

import com.example.myfirstfullstackproject.model.Category;
import com.example.myfirstfullstackproject.model.Cloud;

import java.util.List;

public interface CloudService {

    Cloud createCloud(String cloudName, Category category, long price, int users, int storage, String src);

    List<Cloud> getCloudsByCategory(Category category);

    List<Cloud> getAllClouds();

}
