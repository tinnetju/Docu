package nl.avans.C3.DataStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import nl.avans.C3.Domain.Client;
import nl.avans.C3.Domain.Insurance;

/**
 *
 * @author Thom
 */
@Repository
public class InsuranceRepository implements InsuranceRepositoryIF {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InsuranceRepository(DataSource dataSource) 
    { 
        this.jdbcTemplate = new JdbcTemplate(dataSource); 
    }
   

    @Transactional(readOnly=true)
    @Override
    public List<Insurance> findAll() {
        List<Insurance> result = jdbcTemplate.query("SELECT * FROM insurance", new InsuranceRowMapper());
        return result;
    }
    
    @Transactional(readOnly=true)
    @Override
    public Insurance findInsuranceById(int ID) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM insurance WHERE ID=?",
                new Object[]{ID}, new InsuranceRowMapper());
    }


    public Insurance create(final Insurance insurance) {
        final String sql = "INSERT INTO insurance('ID', 'name', 'price', 'sessionsReimbursed', 'insuranceCompany') " +
                "VALUES(?,?,?,?,?)";

        // KeyHolder gaat de auto increment key uit de database bevatten.
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, insurance.getID());
                ps.setString(2, insurance.getName());
                ps.setBigDecimal(4, insurance.getPrice());
                ps.setInt(3, insurance.getSessionsReimbursed());
                //ps.setString(5, insurance.getInsuranceCompany());
                return ps;
            }
        }, holder);

        return insurance;
    }

    public void deleteInsuranceById(int ID) {
        jdbcTemplate.update("DELETE FROM insurance WHERE ID=?", new Object[]{ID});
    }
}
