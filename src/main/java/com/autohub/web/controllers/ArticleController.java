package com.autohub.web.controllers;

import com.autohub.domain.enums.ArticleType;
import com.autohub.domain.model.binding.ArticleCreateBindingModel;
import com.autohub.domain.model.binding.UserRegisterBindingModel;
import com.autohub.domain.model.service.ArticleServiceModel;
import com.autohub.domain.model.view.ArticleViewModel;
import com.autohub.service.interfaces.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ArticleController {
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/admin/articles/create")
    public ModelAndView createArticle(ModelAndView modelAndView, HttpSession session) {
        if (session.getAttribute("username") == null || !((boolean) session.getAttribute("isAdmin"))) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName("create-article");
        }
        return modelAndView;
    }

    @PostMapping("/admin/articles/create")
    public ModelAndView confirmCreateArticle(@ModelAttribute(name = "model") ArticleCreateBindingModel model,
                                             ModelAndView modelAndView) {
        this.articleService.save(this.modelMapper.map(model, ArticleServiceModel.class));
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @GetMapping("/articles/news")
    public ModelAndView news(ModelAndView modelAndView, HttpSession session) {
        initArticles(modelAndView, session, "news", ArticleType.NEWS);
        return modelAndView;
    }

    @GetMapping("/articles/sports")
    public ModelAndView sports(ModelAndView modelAndView, HttpSession session) {
        initArticles(modelAndView, session, "sports", ArticleType.SPORTS);
        return modelAndView;
    }

    @GetMapping("/articles/tips-and-tricks")
    public ModelAndView tipsAndTricks(ModelAndView modelAndView, HttpSession session) {
        initArticles(modelAndView, session, "tips-and-tricks", ArticleType.TIPS_AND_TRICKS);
        return modelAndView;
    }

    private void initArticles(ModelAndView modelAndView, HttpSession session, String viewName, ArticleType type) {
        if (session.getAttribute("username") == null) {
            modelAndView.setViewName("redirect:/home");
        } else {
            modelAndView.setViewName(viewName);
            List<ArticleViewModel> articles = this.articleService.findAllByType(type)
                    .stream()
                    .map(a -> this.modelMapper.map(a, ArticleViewModel.class))
                    .collect(Collectors.toList());
            modelAndView.addObject("articles", articles);
        }
    }

}
