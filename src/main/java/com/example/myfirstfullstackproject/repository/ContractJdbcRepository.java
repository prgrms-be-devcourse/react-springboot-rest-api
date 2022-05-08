package com.example.myfirstfullstackproject.repository;

import com.example.myfirstfullstackproject.model.Contract;
import com.example.myfirstfullstackproject.model.ContractStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ContractJdbcRepository implements ContractRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;

    private static final Logger logger = LoggerFactory.getLogger(ContractJdbcRepository.class);
    private static final RowMapper<Contract> contractRowMapper = (rs, rowNum) -> {
        return Contract.builder()
                .contractId(rs.getLong("contract_id"))
                .cloudId(rs.getLong("cloud_id"))
                .email(rs.getString("email"))
                .contractStatus(ContractStatus.valueOf(rs.getString("contract_status")))
                .createdAt(rs.getTimestamp("created_at").toLocalDateTime())
                .updatedAt(rs.getTimestamp("updated_at").toLocalDateTime())
                .build();
    };

    @Autowired
    public ContractJdbcRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.simpleJdbcInsert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName("contracts")
                .usingGeneratedKeyColumns("contract_id");
    }

    @Override
    @Transactional
    public Contract insert(Contract contract) {
        Map<String, Object> params = new HashMap<>();
        params.put("cloud_id", contract.getCloudId());
        params.put("email", contract.getEmail());
        params.put("user_name", contract.getUserName());
        params.put("contract_status", contract.getContractStatus());
        params.put("created_at", Timestamp.valueOf(contract.getCreatedAt()));
        params.put("updated_at", Timestamp.valueOf(contract.getUpdatedAt()));

        long contractId = Long.parseLong(String.valueOf(simpleJdbcInsert.executeAndReturnKey(params)));
        contract.setContractId(contractId);

        String INSERT_OPTION = "INSERT INTO options(contract_id, cloud_id, title, detail) "
                + "VALUES (?, ?, ?, ?)";
        contract.getOptions().forEach(option ->
                jdbcTemplate.update(INSERT_OPTION, contractId, option.getCloudId(), option.getTitle(), option.getDetail()));

        return contract;
    }

    @Override
    public Optional<Contract> findByCloudIdAndEmail(long cloudId, String email) {
        String SELECT_BY_CLOUDID_AND_EMAIL =
                "SELECT * FROM contracts WHERE cloud_id = ? AND email = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_CLOUDID_AND_EMAIL, contractRowMapper, cloudId, email));
        } catch (DataAccessException e) {
            logger.info("cloudId와 email을 이용해 contract 레코드를 찾는 쿼리 실패");
            return Optional.empty();
        }
    }

}
