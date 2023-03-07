package com.codecampn.nv.poc.databaserest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class CustomerConsentController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/customer-consents")
    public List<Map<String, Object>> getData() {
        return jdbcTemplate.queryForList("SELECT * FROM channel_consents");
    }

}
