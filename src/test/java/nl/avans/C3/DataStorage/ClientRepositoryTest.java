package nl.avans.C3.DataStorage;

import java.util.ArrayList;
import nl.avans.C3.Domain.Client;
import org.junit.*;
import org.junit.runner.*;
import org.springframework.boot.test.autoconfigure.orm.jpa.*;

import static org.assertj.core.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import static org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase.Replace.NONE;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author Tinne
 */

/*
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class ClientRepositoryTest {
    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepositoryIF clientRepository;
    
    
    @Test
    public void testExample() throws Exception {
        System.out.println("testClientRepository");
        this.entityManager.persist(new Client(482931827, "De Test", "Tester", new java.sql.Date(4324325), "Test stad", "4281AP", "Testlaan 24", "NL75 INGB 0283 8271 23", true, "Tester@test.nl", "0416-392832", null, null));
        Client client = this.clientRepository.findClientByBSN(482931827);
        
        assertThat(client.getFirstName()).isEqualTo("Tester");
        assertThat(client.getEmailAddress()).isEqualTo("Tester@test.nl");
        assertThat(client.getContracts()).isEqualTo(new ArrayList<>());
    }
}
*/