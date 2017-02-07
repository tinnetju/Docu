package nl.avans.C3.Domain;

/**
 *
 * @author Tinne
 */
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "This client is not found in the system")
public class ClientNotFoundException extends Exception
{
    private String message = "";

    public ClientNotFoundException(String message){
        this.message = message;
    }

    @Override
    public String getMessage(){
        return message;
    }
}
