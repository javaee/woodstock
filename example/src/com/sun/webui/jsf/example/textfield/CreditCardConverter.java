package com.sun.webui.jsf.example.textfield;

import java.io.Serializable;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

public class CreditCardConverter implements Converter, Serializable {
    private String separator;

    // Get separator.
    public void setSeparator(String newValue) {
        separator = newValue;
    }

    public Object getAsObject(FacesContext context, UIComponent component,
            String newValue) throws ConverterException {
        StringBuffer buffer = new StringBuffer(newValue);
        int i = 0;
        while (i < buffer.length()) {
            if (Character.isDigit(buffer.charAt(i)))
                i++;
            else
                buffer.deleteCharAt(i);
        }
        return new CreditCard(buffer.toString());
    }

    public String getAsString(FacesContext context, UIComponent component,
            Object value) throws ConverterException {
        // length 13: xxxx xxx xxx xxx
        // length 14: xxxxx xxxx xxxxx
        // length 15: xxxx xxxxxx xxxxx
        // length 16: xxxx xxxx xxxx xxxx
        // length 22: xxxxxx xxxxxxxx xxxxxxxx
        if (!(value instanceof CreditCard)) {
            throw new ConverterException();
        }
            
        String v = ((CreditCard) value).toString();
        String sep = separator;
        if (sep == null) {
            sep = " ";
        }

        int[] boundaries = null;
        int length = v.length();
        if (length == 13) {
            boundaries = new int[] { 4, 7, 10 };
        } else if (length == 14) {
            boundaries = new int[] { 5, 9 };
        } else if (length == 15) {
            boundaries = new int[] { 4, 10 };
        } else if (length == 16) {
            boundaries = new int[] { 4, 8, 12 };
        } else if (length == 22) {
            boundaries = new int[] { 6, 14 };
        } else {
            return v;
        }
      
        StringBuffer result = new StringBuffer();
        int start = 0;
        for (int i = 0; i < boundaries.length; i++) {
            int end = boundaries[i];
            result.append(v.substring(start, end));
            result.append(sep);
            start = end;
        }
        result.append(v.substring(start));
        return result.toString();
    }
}
