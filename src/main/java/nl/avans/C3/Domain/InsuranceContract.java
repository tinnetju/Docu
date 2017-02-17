package nl.avans.C3.Domain;

import java.util.Date;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

/**
 *
 * @author Tinne
 */

public class InsuranceContract {
    private int insuranceContractID;
    private int BSN;
    private int insuranceID;
    private int insuranceTypeID;
    
    @NotNull
    @Future
    private java.sql.Date startDate;
    
    @NotNull
    @Future
    private java.sql.Date endDate;
    
    @NotNull
    private Double excess;
    
    
    private int remainingReimbursements;
    
    private Insurance insurance;
    private InsuranceType insuranceType;
    
    public InsuranceContract(int contractID, int BSN, int insuranceID, int insuranceTypeID, java.sql.Date startDate, java.sql.Date endDate, Double excess, int remainingReimbursements) {
        this.insuranceContractID = contractID;
        this.BSN = BSN;
        this.insuranceID = insuranceID;
        this.insuranceTypeID = insuranceTypeID;
        this.startDate = startDate;
        this.endDate = endDate;
        this.excess = excess;
        this.remainingReimbursements = remainingReimbursements;
    }
    
    public InsuranceContract() {

    }
    
    public int getInsuranceContractID() {
        return insuranceContractID;
    }

    public void setInsuranceContractID(int insuranceContractID) {
        this.insuranceContractID = insuranceContractID;
    }

    public int getBSN() {
        return BSN;
    }

    public void setBSN(int BSN) {
        this.BSN = BSN;
    }

    public int getInsuranceID() {
        return insuranceID;
    }

    public void setInsuranceID(int insuranceID) {
        this.insuranceID = insuranceID;
    }

    public int getInsuranceTypeID() {
        return insuranceTypeID;
    }

    public void setInsuranceTypeID(int insuranceTypeID) {
        this.insuranceTypeID = insuranceTypeID;
    }

    public java.sql.Date getStartDate() {
        return startDate;
    }

    public void setStartDate(java.sql.Date startDate) {
        this.startDate = startDate;
    }

    public java.sql.Date getEndDate() {
        return endDate;
    }

    public void setEndDate(java.sql.Date endDate) {
        this.endDate = endDate;
    }

    public Double getExcess() {
        return excess;
    }

    public void setExcess(Double excess) {
        this.excess = excess;
    }

    public int getRemainingReimbursements() {
        return remainingReimbursements;
    }

    public void setRemainingReimbursements(int remainingReimbursements) {
        this.remainingReimbursements = remainingReimbursements;
    }

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }

    public InsuranceType getInsuranceType() {
        return insuranceType;
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }
    
    
}
