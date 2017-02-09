/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import nl.avans.C3.Domain.Company;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Stefan
 */
public class CompanyRowMapper implements RowMapper<Company>{
    @Override
    public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
        Company company = new Company();
        company.setName(rs.getString("Name"));
        company.setCity(rs.getString("City"));
        company.setPostalCode(rs.getString("PostalCode"));
        company.setAddress(rs.getString("Address"));
        company.setCountry(rs.getString("Country"));
        company.setKVK(rs.getInt("KVK"));
        return company;
    }
}