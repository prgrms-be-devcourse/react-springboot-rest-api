package com.example.myfirstfullstackproject.service;

import com.example.myfirstfullstackproject.model.Contract;
import com.example.myfirstfullstackproject.model.ContractStatus;
import com.example.myfirstfullstackproject.model.Option;
import com.example.myfirstfullstackproject.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DefaultContractService implements ContractService {

    private static final Logger logger = LoggerFactory.getLogger(DefaultContractService.class);

    private final ContractRepository contractRepository;

    @Autowired
    public DefaultContractService(ContractRepository contractRepository) {
        this.contractRepository = contractRepository;
    }

    public Contract createContract(long cloudId, String email, String userName, List<Option> options) {
        return contractRepository.insert(Contract.builder()
                        .cloudId(cloudId)
                        .userName(userName)
                        .email(email)
                        .contractStatus(ContractStatus.ACCEPTED)
                        .options(options)
                        .createdAt(LocalDateTime.now())
                        .updatedAt(LocalDateTime.now())
                        .build());
    }

    private boolean isPresentByCloudIdAndEmail(long cloudId, String email) {
        return contractRepository.findByCloudIdAndEmail(cloudId, email).isPresent();
    }

}
