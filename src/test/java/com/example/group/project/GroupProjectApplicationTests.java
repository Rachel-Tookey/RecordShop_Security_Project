package com.example.group.project;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(properties = {
		"spring.datasource.url=jdbc:mysql://localhost:3306/recordShop?createDatabaseIfNotExist=true",
		"spring.datasource.username=root",
		"spring.datasource.password=YOURPASSWORDFABI",
		"spring.flyway.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl"
})
class GroupProjectApplicationTests {

	@Test
	void contextLoads() {
	}

}
