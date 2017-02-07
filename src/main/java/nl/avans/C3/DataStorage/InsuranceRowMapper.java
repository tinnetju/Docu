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
       
        insurance.setID(rs.getInt("InsuranceID"));
        insurance.setName(rs.getString("InsuranceName"));
        insurance.setDescription(rs.getString("InsuranceDescription"));
        insurance.setPrice(rs.getBigDecimal("InsurancePrice"));


        return insurance;
    }
}
