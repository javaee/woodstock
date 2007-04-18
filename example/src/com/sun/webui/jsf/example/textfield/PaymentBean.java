package com.sun.webui.jsf.example.textfield;

import java.util.Date;

import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

// Backing bean for table examples.
public class PaymentBean {
    private double amount;
    private CreditCard card = new CreditCard("");
    private Date date = new Date();

    // Get amount.
    public double getAmount() {
        return amount;
    }

    // Set amount.
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // Get card.
    public CreditCard getCard() {
        return card;
    }

    // Set card.
    public void setCard(CreditCard card) {
        this.card = card;
    }

    // Get date.
    public Date getDate() {
        return date;
    }

    // Set date.
    public void setDate(Date date) {
        this.date = date;
    }

    // Card validator
    public void cardValidator(FacesContext context, UIComponent component, 
            Object value) {
        CreditCardValidator validator = new CreditCardValidator();
        validator.validate(context, component, value);
    }
    
    // Render the alert if there is an error for the page.
    public boolean getAlertRendered() {
        FacesMessage.Severity severity = FacesContext.getCurrentInstance().
            getMaximumSeverity();
        if (severity != null && 
                severity.compareTo(FacesMessage.SEVERITY_ERROR) == 0) {
            return true;
        }
        return false;
    }
}
