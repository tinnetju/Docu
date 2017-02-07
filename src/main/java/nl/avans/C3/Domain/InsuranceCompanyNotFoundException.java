package nl.avans.C3.Domain;

/**
 *
 * @author Thom
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This insurance company is not found in the system")
public class InsuranceCompanyNotFoundException extends Exception
{
    private String message = "";

    public InsuranceCompanyNotFoundException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
