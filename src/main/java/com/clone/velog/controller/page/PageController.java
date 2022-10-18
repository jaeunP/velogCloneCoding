package com.clone.velog.controller.page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.clone.velog.models.network.response.UserRes;
import com.clone.velog.service.user.UserService;

// PageController : UI에서 사용자가 요청하는 페이지를 띄움
@Controller
@RequestMapping("/")
public class PageController {

    @Autowired
    private UserService userService;
    
    @RequestMapping("")
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response, Model model){
        HttpSession session = request.getSession();
        if(session.getAttribute("userRes") != null){
            model.addAttribute("loginURL", "/fragment/header_login");
        }else{
            model.addAttribute("loginURL", "/fragment/header");
        }
        return new ModelAndView("index")
            .addObject("code", "index");
    }

    // login Success API
    @RequestMapping("{id}")
    public ModelAndView login_ok(HttpServletRequest request, HttpServletResponse response, @PathVariable(name = "id") String id, Model model) throws Exception{
        HttpSession session = request.getSession();
        UserRes userRes = userService.login(id).getData();
        session.setAttribute("userRes", userRes);
        model.addAttribute("userRes", session.getAttribute("userRes"));
        model.addAttribute("loginURL", "/fragment/header_login");

        return new ModelAndView("index")
            .addObject("code", "index");
    }

    // logout Success API
    @RequestMapping("logout")
    public ModelAndView logout(HttpServletRequest request, HttpServletResponse response, Model model){
        HttpSession session = request.getSession();
        session.invalidate();; // session delete
        model.addAttribute("loginURL", "/fragment/header");
        return new ModelAndView("index")
            .addObject("code", "index");
    }
}