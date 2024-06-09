/*
package com.nolan.musicsheetsportal.controllers;

import com.nolan.musicsheetsportal.models.MyUser;
import com.nolan.musicsheetsportal.sevices.CommentService;
import com.nolan.musicsheetsportal.sevices.LikeService;
import com.nolan.musicsheetsportal.sevices.MyUserDetailService;
import com.nolan.musicsheetsportal.sevices.SheetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class Thymeleaf {


    @Autowired
    private SheetService sheetService;

    @Autowired
    private MyUserDetailService userDetailService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private LikeService likeService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("sheets", sheetService.getAllSheets());
        return "index";
    }

    @GetMapping("/user")
    public String userPage(@AuthenticationPrincipal Principal principal, Model model) {
        MyUser user = (MyUser) userDetailService.loadUserByUsername(principal.getName());
        model.addAttribute("user", user);
        model.addAttribute("userSheets", sheetService.getSheetsByUser(user.getId()));
        model.addAttribute("userComments", commentService.getCommentsByUser(user.getId()));
        model.addAttribute("userLikes", likeService.getLikesByUser(user.getId()));
        return "user";
    }
}
*/
