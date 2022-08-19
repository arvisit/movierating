package com.company.movierating.controller.command.impl;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.BanService;
import com.company.movierating.service.dto.BanDto;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class EditBanCommand implements Command {
    private final BanService service;
    private final ParametersPreparer preparer;

    public EditBanCommand(BanService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String banIdStr = req.getParameter("id");
        String unbanStr = req.getParameter("unban");
        String durationStr = req.getParameter("duration");
        String startDateStr = req.getParameter("start_date");

        BanDto changed = new BanDto();

        changed.setId(preparer.getLong(banIdStr));
        if (Boolean.parseBoolean(unbanStr)) {
            changed.setEndDate(ZonedDateTime.now());
        } else {
            long duration = preparer.getLong(durationStr);
            log.debug(startDateStr);
            ZonedDateTime startDate = ZonedDateTime.parse(startDateStr,
                    DateTimeFormatter.ISO_ZONED_DATE_TIME);
            changed.setEndDate(startDate.plusDays(duration));
        }

        req.setAttribute(JspConstants.LAST_PAGE_ATTRIBUTE_NAME,
                "redirect:controller?command=edit_ban_form&id=" + banIdStr);
        BanDto updated = service.update(changed);
        req.setAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME, "Parameters were updated successfully");
        req.setAttribute("user", updated.getUser());
        return JspConstants.VIEW_USER;
    }

}
