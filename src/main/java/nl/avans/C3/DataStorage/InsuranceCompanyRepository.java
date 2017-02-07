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
import nl.avans.C3.Domain.InsuranceCompany;

/**
 *
 * @author Thom
 */
@Repository
public class InsuranceCompanyRepository implements InsuranceCompanyRepositoryIF {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InsuranceCompanyRepository(DataSource dataSource) 
    { 
        this.jdbcTemplate = new JdbcTemplate(dataSource); 
    }
   

    @Transactional(readOnly=true)
    @Override
    public List<InsuranceCompany> findAll() {
        List<InsuranceCompany> result = jdbcTemplate.query("SELECT * FROM insurancecompany", new InsuranceCompanyRowMapper());
        return result;
    }
    
    @Transactional(readOnly=true)
    @Override
    public InsuranceCompany findInsuranceCompanyByKvk(int KVK) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM insurancecompany WHERE KVK=?",
                new Object[]{KVK}, new InsuranceCompanyRowMapper());
    }


    @Override
    public InsuranceCompany create(final InsuranceCompany insuranceCompany) {
        final String sql = "INSERT INTO insurancecompany(name, city, postalCode, address, kvk) " +
                "VALUES(?,?,?,?,?)";

        // KeyHolder gaat de auto increment key uit de database bevatten.
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, insuranceCompany.getName()); 
                ps.setString(2, insuranceCompany.getCity());
                ps.setString(3, insuranceCompany.getPostalCode());     
                ps.setString(4, insuranceCompany.getAddress());                
                ps.setInt(5, insuranceCompany.getKVK());
                return ps;
            }
        }, holder);

        return insuranceCompany;
    }
    
    @Override
    public void edit(final InsuranceCompany insuranceCompany, int KVK) {
        final String sql = "UPDATE insuranceCompany SET`Name` = IFNULL(?, Name),`City` = IFNULL(?, City),`PostalCode` = IFNULL(?, PostalCode),`Address` = IFNULL(?, Address),`KVK` = IFNULL(?, KVK)" +
                "WHERE KVK = ?";
        jdbcTemplate.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, insuranceCompany.getName());
            ps.setString(2, insuranceCompany.getCity());
            ps.setString(3, insuranceCompany.getPostalCode());            
            ps.setString(4, insuranceCompany.getAddress()); 
            ps.setInt(5, insuranceCompany.getKVK());            
            ps.setInt(6, KVK);
            return ps;
        });

    }

    public void deleteInsuranceCompanyByKvk(int KVK) {
        jdbcTemplate.update("DELETE FROM insurancecompany WHERE KVK=?", new Object[]{KVK});
    }
}