package com.sun.webui.jsf.example.textfield;

import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.webapp.UIComponentTag;
import javax.faces.webapp.ValidatorTag;
import javax.servlet.jsp.JspException;

public class RegexValidatorTag extends ValidatorTag {
    private String expression;
    private String errorSummary;
    private String errorDetail;

    public RegexValidatorTag() {
        setValidatorId("textField.Regex");
    }

    // Set expression.
    public void setExpression(String newValue) { 
        expression = newValue;
    } 

    // Set errorSummary
    public void setErrorSummary(String newValue) { 
        errorSummary = newValue;
    } 

    // Set errorDetail
    public void setErrorDetail(String newValue) { 
        errorDetail = newValue;
    } 

    public Validator createValidator() throws JspException {
        RegexValidator validator = (RegexValidator) super.createValidator();
        validator.setExpression(eval(expression));
        validator.setErrorSummary(eval(errorSummary));
        validator.setErrorDetail(eval(errorDetail));
        return validator;
    }

    public void release() {
        expression = null;
        errorSummary = null;
        errorDetail = null;
    }

    public static String eval(String expression) {
//        if (expression != null && UIComponentTag.isValueReference(expression)) {
            FacesContext context = FacesContext.getCurrentInstance();
            Application app = context.getApplication();
            return "" + app.createValueBinding(expression).getValue(context);
//        } else {
//            return expression;
//        }      
    }
}
