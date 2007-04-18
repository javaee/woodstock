package com.sun.webui.jsf.example.textfield;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

import java.io.Serializable;

import java.text.MessageFormat;

import java.util.Locale;
import java.util.regex.Pattern;

public class RegexValidator implements Validator, Serializable {
   private String expression;
   private Pattern pattern;
   private String errorSummary;
   private String errorDetail;

   public void validate(FacesContext context, UIComponent component, 
         Object value) {
      if (value == null) return;
      if (pattern == null) return;
      if(!pattern.matcher(value.toString()).matches()) {

         Object[] params = new Object[] { expression, value };
         Locale locale = context.getViewRoot().getLocale();
         String summary;
         if (errorSummary == null)
            summary = Messages.getString("com.sun.webui.jsf.example.textField.Resources",
                "badRegex", params);
         else
            summary = new MessageFormat(errorSummary, locale).format(params);
         String detail;
         if (errorDetail == null)
            detail = Messages.getString("com.sun.webui.jsf.example.textField.Resources",
                "badRegex_detail", params);
         else
            detail = new MessageFormat(errorDetail, locale).format(params);
         FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
            summary, detail);
         throw new ValidatorException(message);
      }
   }

   // Set expression.
   public void setExpression(String newValue) {
      expression = newValue;
      pattern = Pattern.compile(expression);
   }

   // Set errorSummary.
   public void setErrorSummary(String newValue) {
      errorSummary = newValue;
   }
   
   // Set errorDetail.
   public void setErrorDetail(String newValue) {
      errorDetail = newValue;
   }
}
