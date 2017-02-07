package nl.avans.C3.Domain;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Tinne
 */

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No insurance contracts have been found.")
public class InsuranceContractNotFoundException extends Exception
{
    private String message = "";

    public InsuranceContractNotFoundException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}