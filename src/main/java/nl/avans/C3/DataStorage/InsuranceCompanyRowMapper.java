/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nl.avans.C3.DataStorage;

import java.sql.ResultSet;
import java.sql.SQLException;
import nl.avans.C3.Domain.InsuranceCompany;
import org.springframework.jdbc.core.RowMapper;

/**
 *
 * @author Stefan
 */
public class InsuranceCompanyRowMapper implements RowMapper<InsuranceCompany>{
    @Override
    public InsuranceCompany mapRow(ResultSet rs, int rowNum) throws SQLException {
        InsuranceCompany insuranceCompany = new InsuranceCompany();
        insuranceCompany.setName(rs.getString("Name"));
        insuranceCompany.setCity(rs.getString("City"));
        insuranceCompany.setPostalCode(rs.getString("PostalCode"));
        insuranceCompany.setAddress(rs.getString("Address"));
        insuranceCompany.setCountry(rs.getString("Country"));
        insuranceCompany.setKVK(rs.getInt("KVK"));
        return insuranceCompany;
    }
}
