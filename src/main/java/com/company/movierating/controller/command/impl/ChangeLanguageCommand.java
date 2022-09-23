package com.company.movierating.controller.command.impl;

import com.company.movierating.AppConstants;
import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {

    public ChangeLanguageCommand() {
    }

    @Override
    public String execute(HttpServletRequest req) {
        String lastPage = req.getParameter("lastPage");
        String language = req.getParameter("language");
        
        if (AppConstants.AVAILABLE_LANGUAGES.contains(language)) {
            HttpSession session = req.getSession();
            session.setAttribute("language", language);
        }
        
        if ("controller?".equals(lastPage) || lastPage == null) {
            lastPage = ".";
        } else if (!lastPage.startsWith("controller?")) {
            req.setAttribute(JspConstants.ERROR_MESSAGE_ATTRIBUTE_NAME, "No such page found");
        }
        
        return "redirect:" + lastPage;
    }

}
