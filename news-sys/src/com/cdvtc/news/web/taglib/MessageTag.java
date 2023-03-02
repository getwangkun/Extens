package com.cdvtc.news.web.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;
import java.io.StringWriter;

public class MessageTag extends SimpleTagSupport {

    private String value;

    public void setValue(String value) {
        this.value = value;
    }

    StringWriter sw = new StringWriter();

    public void doTag()
            throws JspException, IOException
    {
        if (value != null) {
            /* 从属性中使用消息 */
            JspWriter out = getJspContext().getOut();
            out.println( value );
        }
        else {
            /* 从内容体中使用消息 */
            getJspBody().invoke(sw);
            getJspContext().getOut().println(sw.toString());
        }
    }
}
