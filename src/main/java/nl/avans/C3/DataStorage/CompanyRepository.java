/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import java.util.List;
import javax.sql.DataSource;
import nl.avans.C3.Domain.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Stefan
 */
@Repository
public class CompanyRepository implements CompanyRepositoryIF{
    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @Autowired
    public CompanyRepository(DataSource dataSource){ 
        this.jdbcTemplate = new JdbcTemplate(dataSource); 
    }
    
    @Transactional(readOnly=true)
    public List<Company> getCompany() {
        List<Company> result = jdbcTemplate.query("SELECT * FROM insurancecompany", new CompanyRowMapper());
        return result;
    }
    
    public void editCompany(String name, String city, String postalCode, String address, String country, int kVK) {
        jdbcTemplate.update("UPDATE insurancecompany SET Name = ?, City = ?, PostalCode = ?, Address = ?, Country = ?, KVK = ?", new Object[] {name, city, postalCode, address, country, kVK});
    }
}
