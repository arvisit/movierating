package com.company.movierating.controller.command.impl.ban;

import java.time.temporal.ChronoUnit;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.BanService;
import com.company.movierating.service.dto.BanDto;

import jakarta.servlet.http.HttpServletRequest;

public class EditBanFormCommand implements Command {
    private final BanService banService;
    private final ParametersPreparer preparer;

    public EditBanFormCommand(BanService banService, ParametersPreparer preparer) {
        this.banService = banService;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String banIdStr = req.getParameter("id");
        Long banId = preparer.getLong(banIdStr);

        BanDto ban = banService.getById(banId);
        req.setAttribute("ban", ban);
        long duration = ChronoUnit.DAYS.between(ban.getStartDate(), ban.getEndDate());
        req.setAttribute("duration", duration);

        return JspConstants.EDIT_BAN_FORM;
    }

}
