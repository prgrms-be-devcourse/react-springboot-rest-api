package com.example.myfirstfullstackproject.repository;

import com.example.myfirstfullstackproject.model.Contract;

import java.util.Optional;

public interface ContractRepository {

    Contract insert(Contract contract);

    Optional<Contract> findByCloudIdAndEmail(long cloudId, String email);

}
