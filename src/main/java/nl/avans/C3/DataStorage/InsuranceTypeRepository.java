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
import nl.avans.C3.Domain.InsuranceType;

/**
 *
 * @author Thom
 */
@Repository
public class InsuranceTypeRepository implements InsuranceTypeRepositoryIF {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InsuranceTypeRepository(DataSource dataSource) 
    { 
        this.jdbcTemplate = new JdbcTemplate(dataSource); 
    }
   

    @Transactional(readOnly=true)
    @Override
    public List<InsuranceType> findAll() {
        List<InsuranceType> result = jdbcTemplate.query("SELECT * FROM insurancetype", new InsuranceTypeRowMapper());
        return result;
    }
    
    @Transactional(readOnly=true)
    @Override
    public InsuranceType findInsuranceTypeById(int ID) {
        return jdbcTemplate.queryForObject(
                "SELECT * FROM insuranceType WHERE InsuranceTypeID=?",
                new Object[]{ID}, new InsuranceTypeRowMapper());
    }


    @Override
    public InsuranceType create(final InsuranceType insuranceType) {
        final String sql = "INSERT INTO insurancetype(InsuranceTypeName, InsuranceTypeDescription,ReimbursementDescription,ReimbursementAmount) " +
                "VALUES(?,?,?,?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, insuranceType.getName());
                ps.setString(2, insuranceType.getDescription());
                ps.setString(3, insuranceType.getReimbursementDescription());               
                ps.setInt(4, insuranceType.getReimbursementAmount());                             
                return ps;
            }
        }, holder);

        return insuranceType;
    }
    
    @Override
    public void edit(final InsuranceType insuranceType, int ID) {
//        final String sql = "UPDATE insurance SET(ID, name, price, sessionsReimbursed) " +
//                "VALUES(?,?,?,?)WHERE ID = ?";
		final String sql = "UPDATE insurancetype SET`InsuranceTypeName` = IFNULL(?, InsuranceTypeName),`InsuranceTypeDescription` = IFNULL(?, InsuranceTypeDescription),`ReimbursementDescription` = IFNULL(?, InsuranceTypeDescription),`ReimbursementAmount` = IFNULL(?, ReimbursementAmount)" +
                "WHERE InsuranceTypeID = ?";
        jdbcTemplate.update((Connection connection) -> {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, insuranceType.getName());
            ps.setString(2, insuranceType.getDescription());
            ps.setString(3, insuranceType.getReimbursementDescription());
            ps.setInt(4, insuranceType.getReimbursementAmount());
            ps.setInt(5, ID);
            return ps;
        });
		
      
    }    
    

    @Override
    public void deleteInsuranceTypeById(int ID) {
        jdbcTemplate.update("DELETE FROM insurancetype WHERE InsuranceTypeID=?", ID);
    }
}
