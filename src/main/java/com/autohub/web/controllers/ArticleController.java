package com.autohub.web.controllers;

import com.autohub.domain.enums.ArticleType;
import com.autohub.domain.model.binding.ArticleCreateBindingModel;
import com.autohub.domain.model.binding.UserRegisterBindingModel;
import com.autohub.domain.model.service.ArticleServiceModel;
import com.autohub.domain.model.view.ArticleViewModel;
import com.autohub.service.interfaces.ArticleService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final ModelMapper modelMapper;

    @Autowired
    public ArticleController(ArticleService articleService, ModelMapper modelMapper) {
        this.articleService = articleService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/create")
    public ModelAndView createArticle(@ModelAttribute(name = "article") ArticleCreateBindingModel model, ModelAndView modelAndView) {
        modelAndView.setViewName("create-article");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @PostMapping("/create")
    public ModelAndView confirmCreateArticle(@RequestParam(value = "file", required = false) MultipartFile file,
                                             @Valid @ModelAttribute(name = "article") ArticleCreateBindingModel article,
                                             BindingResult bindingResult, ModelAndView modelAndView) throws IOException {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("create-article");
            return modelAndView;
        }
        ArticleServiceModel registeredArticle = this.articleService.save(this.modelMapper.map(article, ArticleServiceModel.class));
        if (file != null) {
            String filePath = "D:\\Програмиране\\СофтУни\\Java Web\\AutoHub\\src\\main\\resources\\static\\images\\blog_images";
            File f1 = new File(filePath + "\\" + registeredArticle.getId() + ".jpg");
            file.transferTo(f1);
        }
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/news")
    public ModelAndView news(ModelAndView modelAndView) {
        initArticles(modelAndView, "news", ArticleType.NEWS);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/sports")
    public ModelAndView sports(ModelAndView modelAndView) {
        initArticles(modelAndView, "sports", ArticleType.SPORTS);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/tips-and-tricks")
    public ModelAndView tipsAndTricks(ModelAndView modelAndView) {
        initArticles(modelAndView, "tips-and-tricks", ArticleType.TIPS_AND_TRICKS);
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/edit/{id}")
    public ModelAndView editArticle(@PathVariable("id") String id,
                                    @ModelAttribute(name = "article") ArticleCreateBindingModel article,
                                    ModelAndView modelAndView) {
        modelAndView.addObject("article", this.articleService.findById(id));
        modelAndView.setViewName("edit-article");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @PostMapping("/edit/{id}")
    public ModelAndView confirmEditArticle(@PathVariable("id") String id,
                                           @Valid @ModelAttribute(name = "article") ArticleCreateBindingModel article,
                                           BindingResult bindingResult, ModelAndView modelAndView) {
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("edit-article");
            modelAndView.addObject("article", this.articleService.findById(id));
            return modelAndView;
        }
        ArticleServiceModel articleServiceModel = this.modelMapper.map(article, ArticleServiceModel.class);
        articleServiceModel.setId(id);
        this.articleService.update(articleServiceModel);
        modelAndView.setViewName("redirect:/home");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/delete/{id}")
    public ModelAndView deleteArticle(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.addObject("article", this.articleService.findById(id));
        modelAndView.setViewName("delete-article");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @PostMapping("/delete/{id}")
    public ModelAndView confirmDeleteArticle(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.articleService.deleteById(id);
        modelAndView.setViewName("redirect:/home");
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