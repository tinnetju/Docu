package nl.avans.C3.DataStorage;

import java.math.BigDecimal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import nl.avans.C3.Domain.InsuranceCompany;

/**
 *
 * @author Thom
 */
public class InsuranceCompanyRowMapper implements RowMapper<InsuranceCompany>
{
    @Override
    public InsuranceCompany mapRow(ResultSet rs, int rowNum) throws SQLException {
        InsuranceCompany insuranceCompany = new InsuranceCompany();
          
        insuranceCompany.setName(rs.getString("Name"));
        insuranceCompany.setCity(rs.getString("City"));
        insuranceCompany.setPostalCode(rs.getString("PostalCode"));
        insuranceCompany.setAddress(rs.getString("Address"));
        insuranceCompany.setKVK(rs.getInt("KVK"));
        return insuranceCompany;
    }
}
