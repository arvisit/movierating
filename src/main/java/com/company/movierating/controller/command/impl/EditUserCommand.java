package com.company.movierating.controller.command.impl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import com.company.movierating.AppConstants;
import com.company.movierating.controller.command.Command;
import com.company.movierating.controller.util.JspConstants;
import com.company.movierating.controller.util.ParametersPreparer;
import com.company.movierating.service.UserService;
import com.company.movierating.service.dto.UserDto;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

public class EditUserCommand implements Command {
    private final UserService service;
    private final ParametersPreparer preparer;

    public EditUserCommand(UserService service, ParametersPreparer preparer) {
        this.service = service;
        this.preparer = preparer;
    }

    @Override
    public String execute(HttpServletRequest req) {

        String idStr = req.getParameter("id");
        String roleStr = req.getParameter("role");
        String reputationStr = req.getParameter("reputation");
        String info = req.getParameter("info");
        String email = req.getParameter("email");
        String avatar = req.getParameter("avatarForm");

        UserDto changed = new UserDto();

        changed.setId(preparer.getLong(idStr));
        changed.setRole(preparer.getRole(roleStr));
        changed.setReputation(preparer.getInt(reputationStr));
        changed.setInfo(info);
        changed.setEmail(email);

        try {
            Part part = req.getPart("imgUploaded");
            if (part != null && part.getSize() != 0) {
                String initialFileName = part.getSubmittedFileName();
                String extension = initialFileName.substring(initialFileName.lastIndexOf('.'));
                String newFileName = UUID.randomUUID() + "_" + changed.getId() + extension;
                String filePath = AppConstants.IMAGE_STORAGE_AVATAR + "/" + newFileName;
                Path path = Paths.get(filePath);
                if (Files.notExists(path)) {
                    path = Files.createDirectories(path);
                }
                part.write(path.toString());
                changed.setAvatar(filePath);
            }
        } catch (IOException | ServletException e) {
            throw new RuntimeException(e);
        }

        if (changed.getAvatar() == null) {
            changed.setAvatar(avatar);
        }

        req.setAttribute(JspConstants.LAST_PAGE_ATTRIBUTE_NAME,
                "redirect:controller?command=edit_user_form&id=" + idStr);
        UserDto updated = service.update(changed);
        req.setAttribute(JspConstants.SUCCESS_MESSAGE_ATTRIBUTE_NAME, "Parameters were updated successfully");
        req.setAttribute("user", updated);
        return JspConstants.VIEW_USER;
    }

}
