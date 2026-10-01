package tn.esprit.karimgazzah4cce10;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class KarimGazzah4Cce10ApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void createsAllExpectedEntityTables() {
        Integer tableCount = jdbcTemplate.queryForObject("""
                SELECT COUNT(*)
                FROM information_schema.tables
                WHERE table_schema = DATABASE()
                  AND table_name IN (
                    'agence', 'client', 'employe', 'equipement', 'reservation',
                    'contrat', 'paiement', 'maintenance', 'vehicule'
                  )
                """, Integer.class);

        Assertions.assertEquals(9, tableCount);
    }
}
