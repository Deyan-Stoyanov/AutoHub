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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
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
    public ModelAndView createArticle(ModelAndView modelAndView) {
        modelAndView.setViewName("create-article");
        return modelAndView;
    }

    @PostMapping("/admin/articles/create")
    public ModelAndView confirmCreateArticle(@RequestParam(value = "file", required = false) MultipartFile file,
                                             @ModelAttribute(name = "model") ArticleCreateBindingModel model,
                                             ModelAndView modelAndView) throws IOException {
        ArticleServiceModel registeredArticle = this.articleService.save(this.modelMapper.map(model, ArticleServiceModel.class));
        if (file != null) {
            String filePath = "D:\\Програмиране\\СофтУни\\Java Web\\AutoHub\\src\\main\\resources\\static\\images\\blog_images";
            File f1 = new File(filePath + "\\" + registeredArticle.getId() + ".jpg");
            file.transferTo(f1);
        }
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @GetMapping("/articles/news")
    public ModelAndView news(ModelAndView modelAndView) {
        initArticles(modelAndView, "news", ArticleType.NEWS);
        return modelAndView;
    }

    @GetMapping("/articles/sports")
    public ModelAndView sports(ModelAndView modelAndView) {
        initArticles(modelAndView, "sports", ArticleType.SPORTS);
        return modelAndView;
    }

    @GetMapping("/articles/tips-and-tricks")
    public ModelAndView tipsAndTricks(ModelAndView modelAndView) {
        initArticles(modelAndView, "tips-and-tricks", ArticleType.TIPS_AND_TRICKS);
        return modelAndView;
    }

    private void initArticles(ModelAndView modelAndView, String viewName, ArticleType type) {
        modelAndView.setViewName(viewName);
        List<ArticleViewModel> articles = this.articleService.findAllByType(type)
                .stream()
                .map(a -> this.modelMapper.map(a, ArticleViewModel.class))
                .collect(Collectors.toList());
        modelAndView.addObject("articles", articles);
    }
}