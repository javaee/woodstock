/*
 * AccordionBackingBean.java
 *
 * Created on June 17, 2007, 6:31 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.webui.jsf.example.accordion;

import java.util.TimeZone;
import java.util.Calendar;

import com.sun.webui.jsf.component.Accordion;
import com.sun.webui.jsf.component.AccordionTab;
import com.sun.webui.jsf.component.StaticText;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import com.sun.webui.jsf.example.index.IndexBackingBean;

/**
 *
 * @author Deep Bhattacharjee
 */
public class AccordionBackingBean {
    
    public final static String SHOW_ACCORDION = "showAccordionIndex";
    
    /** Creates a new instance of AccordionBackingBean */
    public AccordionBackingBean() {
    }
    
   
    
    public StaticText text1 = new StaticText();
    public StaticText text2 = new StaticText();
    public StaticText text3 = new StaticText();
    public StaticText text4 = new StaticText();
    public StaticText text5 = new StaticText();
    public StaticText text6 = new StaticText();
    public StaticText text7 = new StaticText();
    public StaticText text8 = new StaticText();
    
    /** Action handler when navigating to the accordion example */
    public String showAccordion() {
	return SHOW_ACCORDION;
    }
         
    /** Action handler when navigating to the main example index. */
    public String showExampleIndex() {
	_reset();
        return IndexBackingBean.INDEX_ACTION;
    }
    
    public StaticText getText1() {
        this.text1.setText(getTime());
        return text1;
    }    
    
    public void setText1(StaticText text1) {
        this.text1 = text1;
        this.text1.setText(getTime());
    }
    
    public StaticText getText2() {
        this.text2.setText(getTime());
        return text2;
    }    
    
    public void setText2(StaticText text2) {
        this.text2 = text2;
        this.text2.setText(getTime());
    }
    
    public StaticText getText3() {
        this.text3.setText(getTime());
        return text3;
    }    
    
    public void setText3(StaticText text3) {
        this.text3 = text3;
        this.text3.setText(getTime());
    }
    
    public StaticText getText4() {
        this.text4.setText(getTime());
        return text4;
    }    
    
    public void setText4(StaticText text4) {
        this.text4 = text4;
        this.text4.setText(getTime());
    }
    
    public StaticText getText5() {
        this.text5.setText(getTime());
        return text5;
    }    
    
    public void setText5(StaticText text5) {
        this.text5 = text5;
        this.text5.setText(getTime());
    }
    
    public StaticText getText6() {
        this.text6.setText(getTime());
        return text6;
    }    
    
    public void setText6(StaticText text6) {
        this.text6 = text6;
        this.text6.setText(getTime());
    }
    
    public StaticText getText7() {
        this.text7.setText(getTime());
        return text7;
    }    
    
    public void setText7(StaticText text7) {
        this.text7 = text7;
        this.text7.setText(getTime());
    }
    
    public StaticText getText8() {
        this.text8.setText(getTime());
        return text8;
    }    
    
    public void setText8(StaticText text8) {
        this.text8 = text8;
        this.text8.setText(getTime());
    }
    
    private String getTime() {
        Calendar calendar = Calendar.getInstance();
        return calendar.getTime().toString();
    }
    
     /** Initial all bean values to their defaults */
    private void _reset() {
	// nothing to add here for now.
    }
}
