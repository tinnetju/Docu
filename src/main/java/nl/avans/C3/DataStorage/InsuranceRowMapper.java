package nl.avans.C3.DataStorage;

import java.math.BigDecimal;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import nl.avans.C3.Domain.Insurance;

/**
 *
 * @author Tinne
 */
public class InsuranceRowMapper implements RowMapper<Insurance>
{
    @Override
    public Insurance mapRow(ResultSet rs, int rowNum) throws SQLException {
        Insurance insurance = new Insurance();
       
        insurance.setID(rs.getInt("ID"));
        insurance.setName(rs.getString("name"));
        insurance.setInsuranceCompany(null);
        insurance.setPrice(rs.getBigDecimal("price"));
        insurance.setSessionsReimbursed(rs.getInt("sessionsReimbursed"));

        return insurance;
    }
}
