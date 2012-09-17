package com.tophermiller.servlet;

/**
 */

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.net.*;


public abstract class TophermillerServletBase extends HttpServlet { 
 
	
	 public void init (ServletConfig config)
     throws ServletException
     {
		 super.init(config);
     }
	
 
	protected String urlEncode(String url)
    {
        String ret = "";
        StringTokenizer st = new StringTokenizer (url, " ",true);
        while (st.hasMoreTokens()) {
            String t = st.nextToken();
            if (t.equals(" "))	ret += "%20";
            else				ret += t;
        }
        return ret;
    }

    protected String getParameter(HttpServletRequest req, String param, String defalt) {
        String ret = req.getParameter(param);
        if (ret == null) ret = defalt;
        return ret;
    }

    protected boolean getParameter(HttpServletRequest req, String param, boolean defalt) {
        String value = req.getParameter(param);
        if (value == null) return defalt;
        boolean ret = defalt;
        if (value.trim().equalsIgnoreCase("true")) ret = true;
        if (value.trim().equalsIgnoreCase("1")) ret = true;
        if (value.trim().equalsIgnoreCase("yes")) ret = true;
        if (value.trim().equalsIgnoreCase("y")) ret = true;
        return ret;
    }

    protected int getParameter(HttpServletRequest req, String param, int defalt) {
        String value = req.getParameter(param);
        if (value == null) return defalt;
        int ret = defalt;
        try {ret = Integer.parseInt(value);}
        catch (NumberFormatException nfe) {}
        return ret;
    }
    
    protected File fileForURLPath (String urlPath) {
    	if (urlPath == null) return null;
    	if (!urlPath.startsWith("/")) return null;
    	ServletContext context = getServletContext();
    	if (context == null) return null;
    	return fileForURLPath (context, urlPath);
    }
    
    public static File fileForURLPath (ServletContext ctx, String urlPath) {
    	ServletContext pathContext = ctx.getContext(urlPath);
    	if (pathContext == null) return null;
    	String contextPath = pathContext.getContextPath();
    	String realPath = pathContext.getRealPath(urlPath.substring(contextPath.length()+1));
    	if (realPath == null) return null;
    	return new File(realPath);
    }

}
