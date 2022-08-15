package com.company.movierating.controller.command.impl;

import java.time.ZonedDateTime;

import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.BanService;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.BanDto;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.http.HttpServletRequest;

public class BanUserCommand implements Command {
    private final BanService banService;
    private final UserService userService;
    private final ParametersPreparer preparer;

    public BanUserCommand(BanService banService, UserService userService, ParametersPreparer preparer) {
        this.banService = banService;
        this.userService = userService;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {
        String userIdStr = req.getParameter("id");
        String durationStr = req.getParameter("duration");
        String reason = req.getParameter("reason");

        BanDto ban = new BanDto();

        UserDto banned = userService.getById(preparer.getLong(userIdStr));
        ban.setUser(banned);
        ban.setAdmin((UserDto) req.getSession().getAttribute("user"));

        ZonedDateTime current = ZonedDateTime.now();
        ban.setStartDate(current);
        long duration = preparer.getLong(durationStr);
        ban.setEndDate(current.plusDays(duration));
        ban.setReason(reason);

        banService.create(ban);
        req.setAttribute("successMessage", "Ban was successfully applied");
        req.setAttribute("user", banned);
        return JspConstants.VIEW_USER;
    }

}
