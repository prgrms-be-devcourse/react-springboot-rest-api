package com.example.myfirstfullstackproject.service;

import com.example.myfirstfullstackproject.model.Contract;
import com.example.myfirstfullstackproject.model.Option;

import java.util.List;

public interface ContractService {

    Contract createContract(long cloudId, String email, String userName, List<Option> options);

}
