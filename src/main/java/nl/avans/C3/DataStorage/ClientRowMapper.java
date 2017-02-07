package nl.avans.C3.DataStorage;

import nl.avans.C3.Domain.Client;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Tinne
 */
public class ClientRowMapper implements RowMapper<Client>
{
    @Override
    public Client mapRow(ResultSet rs, int rowNum) throws SQLException {
        Client client = new Client();
        client.setBSN(rs.getInt("BSN"));
        client.setFirstName(rs.getString("FirstName"));
        client.setLastName(rs.getString("LastName"));
        client.setDateOfBirth(rs.getDate("DateOfBirth"));
        client.setCity(rs.getString("City"));
        client.setPostalCode(rs.getString("PostalCode"));
        client.setAddress(rs.getString("Address"));
        client.setIBAN(rs.getString("IBAN"));
        client.setIncasso(rs.getBoolean("Incasso"));
        client.setEmailAddress(rs.getString("EmailAddress"));
        client.setTelephoneNumber(rs.getString("TelephoneNumber"));
        return client;
    }
}
