package com.sun.webui.jsf.example.textfield;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

public class CreditCardValidator implements Validator {
    public void validate(FacesContext context, UIComponent component, 
            Object value) {
        if(value == null) {
            return;
        }

        String cardNumber;
        if (value instanceof CreditCard) {
            cardNumber = value.toString();
        } else {
            cardNumber = getDigitsOnly(value.toString());
        }
      
        if (cardNumber == null || cardNumber.length()==0) {
            FacesMessage message = Messages.getMessage(
                Messages.RESOURCE_BUNDLE, "badCardNumber", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
            
        }
        
        if(!luhnCheck(cardNumber)) {
            FacesMessage message = Messages.getMessage(
                Messages.RESOURCE_BUNDLE, "badLuhnCheck", null);
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
    }

    private static boolean luhnCheck(String cardNumber) {
        int sum = 0;

        for(int i = cardNumber.length() - 1; i >= 0; i -= 2) {
            sum += Integer.parseInt(cardNumber.substring(i, i + 1));
            if(i > 0) {
                int d = 2 * Integer.parseInt(cardNumber.substring(i - 1, i));
                if(d > 9) d -= 9;
                sum += d;
            }
        }
        return sum % 10 == 0;
    }

    private static String getDigitsOnly(String s) {
        StringBuffer digitsOnly = new StringBuffer ();
        char c;
        for(int i = 0; i < s.length (); i++) {
            c = s.charAt (i);
            if (Character.isDigit(c)) {
                digitsOnly.append(c);
            }
        }
        return digitsOnly.toString ();
    }
}
