package nl.avans.C3.DataStorage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.sql.DataSource;
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
import nl.avans.C3.Domain.InsuranceContract;

/**
 *
 * @author Tinne
 */
@Repository
public class InsuranceContractRepository implements InsuranceContractRepositoryIF {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public InsuranceContractRepository(DataSource dataSource) 
    { 
        this.jdbcTemplate = new JdbcTemplate(dataSource); 
    }

    @Transactional(readOnly=true)
    public List<InsuranceContract> findAll() {
        List<InsuranceContract> result = jdbcTemplate.query("SELECT * FROM insurancecontract", new InsuranceContractRowMapper());
        return result;
    }
    
    @Transactional(readOnly=true)
    @Override
    public List<InsuranceContract> findInsuranceContractsByBSN(int BSN) {
        return jdbcTemplate.query("SELECT * FROM insurancecontract WHERE BSN=?", new Object[]{BSN}, new InsuranceContractRowMapper());
    }
    
    @Override
    public InsuranceContract create(final InsuranceContract insuranceContract) {
        final String sql = "INSERT INTO insurancecontract(ContractID, BSN, InsuranceID, InsuranceTypeID, StartDate, EndDate, Excess, RemainingReimbursements) " +
                "VALUES(?,?,?,?,?,?,?,?)";
        
        // KeyHolder gaat de auto increment key uit de database bevatten.
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, insuranceContract.getInsuranceContractID());
                ps.setInt(2, insuranceContract.getBSN());
                ps.setInt(3, insuranceContract.getInsuranceID());
                ps.setInt(4, insuranceContract.getInsuranceTypeID());
                ps.setDate(5, insuranceContract.getStartDate());
                ps.setDate(6, insuranceContract.getEndDate());
                ps.setDouble(7, insuranceContract.getExcess());
                ps.setInt(8, insuranceContract.getRemainingReimbursements());
                return ps;
            }
        }, holder);

        return insuranceContract;
    }

    @Override
    public void update(final InsuranceContract insuranceContract) {
        final String sql = "UPDATE insurancecontract SET BSN = ?, InsuranceID = ?, InsuranceTypeID = ?, StartDate = ?, EndDate = ?, Excess = ?, RemainingReimbursements = ? WHERE ContractID = ?";

        // KeyHolder gaat de auto increment key uit de database bevatten.
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setInt(1, insuranceContract.getBSN());
                ps.setInt(2, insuranceContract.getInsuranceID());
                ps.setInt(3, insuranceContract.getInsuranceTypeID());
                ps.setDate(4, insuranceContract.getStartDate());
                ps.setDate(5, insuranceContract.getEndDate());
                ps.setDouble(6, insuranceContract.getExcess());
                ps.setInt(7, insuranceContract.getRemainingReimbursements());
                ps.setInt(8, insuranceContract.getInsuranceContractID());
                return ps;
            }
        }, holder);
    }
    
    @Override
    public void deleteInsuranceContractByID(int insuranceContractID) {
        jdbcTemplate.update("DELETE FROM insurancecontract WHERE ContractID=?", new Object[]{insuranceContractID});
    }
}
