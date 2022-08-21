package com.company.movierating.controller.filter;

import java.io.IOException;

import com.company.movierating.controller.util.JspConstants;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class MessageFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        HttpSession session = req.getSession(false);
        if (session != null) {
            req.setAttribute(JspConstants.ERROR_MESSAGE_ATTRIBUTE_NAME,
                    session.getAttribute(JspConstants.ERROR_MESSAGE_ATTRIBUTE_NAME));
            session.removeAttribute(JspConstants.ERROR_MESSAGE_ATTRIBUTE_NAME);
            req.setAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME,
                    session.getAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME));
            session.removeAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME);
        }
        chain.doFilter(req, res);
    }

}
