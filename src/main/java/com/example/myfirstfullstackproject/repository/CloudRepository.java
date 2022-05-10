package com.example.myfirstfullstackproject.repository;

import com.example.myfirstfullstackproject.model.Category;
import com.example.myfirstfullstackproject.model.Cloud;

import java.util.List;

public interface CloudRepository {

    Cloud insert(Cloud cloud);

    List<Cloud> findCloudsByCategory(Category category);

    List<Cloud> findAll();

    int deleteById(long cloudId);

}
