package nl.avans.C3.DataStorage;

import java.util.List;
import java.util.Map;
import nl.avans.C3.Domain.InsuranceContract;

/**
 *
 * @author Tinne
 */
public interface InsuranceContractRepositoryIF {
    public List<InsuranceContract> findAll();

    public List<InsuranceContract> findInsuranceContractsByBSN(int BSN);

    public InsuranceContract create(final InsuranceContract insuranceContract);
    
    public void update(final InsuranceContract insuranceContract);
    
    public void deleteInsuranceContractByID(int insuranceContractID);
}
