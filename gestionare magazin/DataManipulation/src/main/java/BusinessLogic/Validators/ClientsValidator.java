package BusinessLogic.Validators;

import Model.Clients;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ClientsValidator implements Validator<Clients>{
    private static String emailPattern="^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
    private Pattern pattern;
    public ClientsValidator(){
        pattern = Pattern.compile(emailPattern, Pattern.CASE_INSENSITIVE);
    }

    public boolean validateEmail(String email) {
        Matcher matcher= pattern.matcher(email);
        if(matcher.find()==false)
            return false;
        return true;

    }

    public boolean validateId(int id)
    {
        if(id<=0)return false;
        return true;
    }
    @Override
    public boolean validate(Clients clients) {
        return validateId(clients.getId_client())&validateEmail(clients.getEmail());
    }
}
