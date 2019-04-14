package com.autohub.web.controllers;

import com.autohub.domain.entity.User;
import com.autohub.domain.enums.AdvertisementStatus;
import com.autohub.domain.enums.CarType;
import com.autohub.domain.enums.FuelType;
import com.autohub.domain.model.binding.AdvertisementBindingModel;
import com.autohub.domain.model.binding.CarAdvertisementBindingModel;
import com.autohub.domain.model.binding.PartAdvertisementBindingModel;
import com.autohub.domain.model.service.*;
import com.autohub.domain.model.view.CarAdvertisementViewModel;
import com.autohub.domain.model.view.PartAdvertisementViewModel;
import com.autohub.service.interfaces.CarAdvertisementService;
import com.autohub.service.interfaces.PartAdvertisementService;
import com.autohub.service.interfaces.UserService;
import com.autohub.util.Util;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/marketplace/parts")
public class PartAdvertisementsController {
    private final PartAdvertisementService partAdvertisementService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public PartAdvertisementsController(PartAdvertisementService partAdvertisementService, UserService userService, ModelMapper modelMapper) {
        this.partAdvertisementService = partAdvertisementService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/publish")
    public ModelAndView addPart(@ModelAttribute(name = "advert") PartAdvertisementBindingModel model, ModelAndView modelAndView) {
        modelAndView.addObject("carTypes", CarType.values());
        modelAndView.addObject("fuelTypes", FuelType.values());
        modelAndView.setViewName("add-part");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/publish")
    public ModelAndView confirmAddPart(@RequestParam(value = "file", required = false) MultipartFile file,
                                       @Valid @ModelAttribute(name = "advert") PartAdvertisementBindingModel advert,
                                       BindingResult bindingResult, ModelAndView modelAndView, Authentication authentication) throws IOException {
        advert.setUser(((User) authentication.getPrincipal()).getUsername());
        if (bindingResult.hasErrors()) {
            modelAndView.addObject("carTypes", CarType.values());
            modelAndView.addObject("fuelTypes", FuelType.values());
            modelAndView.addObject("advert", advert);
            modelAndView.setViewName("add-part");
            return modelAndView;
        }
        PartServiceModel partServiceModel = Util.initPartServiceModel(advert);
        AddressServiceModel addressServiceModel = Util.initAddressServiceModel(advert);
        PartAdvertisementServiceModel partAdvertisementServiceModel = Util.initPartAdvertisementServiceModel(advert, partServiceModel, addressServiceModel, this.userService);
        PartAdvertisementServiceModel savedModel = this.partAdvertisementService.save(partAdvertisementServiceModel);
        if (savedModel == null) {
            modelAndView.addObject("carTypes", CarType.values());
            modelAndView.addObject("fuelTypes", FuelType.values());
            modelAndView.setViewName("add-part");
            return modelAndView;
        }
        if (file != null) {
            try {
                String filePath = "C:\\Users\\Lenovo\\autohub\\images\\part_images";
                String fullPath = filePath + "\\" + savedModel.getId() + "." + file.getOriginalFilename().split("\\.")[1];
                File f1 = new File(fullPath);
                file.transferTo(f1);
                savedModel.setImageFileName(fullPath.substring(fullPath.lastIndexOf("\\") + 1));
                savedModel = this.partAdvertisementService.save(savedModel);
            } catch (Exception ignored) {
            }
        }
        modelAndView.setViewName("redirect:/marketplace");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/approve/{id}")
    public ModelAndView approvePart(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.partAdvertisementService.changeAdvertisementStatus(id, AdvertisementStatus.APPROVED);
        modelAndView.setViewName("redirect:/marketplace/pending");
        return modelAndView;
    }

    @PreAuthorize("hasRole('ADMIN') OR hasRole('ROOT')")
    @GetMapping("/decline/{id}")
    public ModelAndView declinePart(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.partAdvertisementService.changeAdvertisementStatus(id, AdvertisementStatus.DECLINED);
        modelAndView.setViewName("redirect:/marketplace/pending");
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/details/{id}")
    public ModelAndView detailsPart(@PathVariable("id") String id, ModelAndView modelAndView) {
        PartAdvertisementServiceModel part = this.partAdvertisementService.findById(id);
        if (part == null) {
            modelAndView.setViewName("redirect:/marketplace");
        } else {
            modelAndView.setViewName("part-advertisement-details");
            modelAndView.addObject("advert", this.modelMapper.map(part, PartAdvertisementViewModel.class));
        }
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/edit/{id}")
    public ModelAndView confirmEditPart(@PathVariable("id") String id,
                                        @ModelAttribute(name = "advert") PartAdvertisementBindingModel model,
                                        ModelAndView modelAndView) {
        PartAdvertisementServiceModel part = this.partAdvertisementService.findById(id);
        if (part == null) {
            modelAndView.setViewName("redirect:/marketplace");
        } else {
            PartAdvertisementBindingModel partAdvertisementBindingModel = this.modelMapper.map(part, PartAdvertisementBindingModel.class);
            Util.setUpBindingModel(partAdvertisementBindingModel, part);
            modelAndView.setViewName("edit-part-advertisement");
            modelAndView.addObject("advert", partAdvertisementBindingModel);
            modelAndView.addObject("id", id);
        }
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/edit/{id}")
    public ModelAndView editPart(@PathVariable("id") String id,
                                 @Valid @ModelAttribute(name = "advert") PartAdvertisementBindingModel advert,
                                 BindingResult bindingResult, Authentication authentication, ModelAndView modelAndView) {
        advert.setUser(((User) authentication.getPrincipal()).getUsername());
        if (bindingResult.hasErrors()) {
            modelAndView.setViewName("edit-part-advertisement" + id);
            modelAndView.addObject("advert", this.modelMapper.map(this.partAdvertisementService.findById(id), PartAdvertisementViewModel.class));
            return modelAndView;
        }
        PartServiceModel partServiceModel = Util.initPartServiceModel(advert);
        AddressServiceModel addressServiceModel = Util.initAddressServiceModel(advert);
        PartAdvertisementServiceModel partAdvertisementServiceModel = Util.initPartAdvertisementServiceModel(advert, partServiceModel, addressServiceModel, this.userService);
        partAdvertisementServiceModel.setId(id);
        partAdvertisementServiceModel.setImageFileName(this.partAdvertisementService.findById(id).getImageFileName());
        this.partAdvertisementService.save(partAdvertisementServiceModel);
        modelAndView.setViewName("redirect:/marketplace/parts/details/" + id);
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public ModelAndView deletePart(@PathVariable("id") String id, ModelAndView modelAndView) {
        modelAndView.setViewName("delete-part-advertisement");
        modelAndView.addObject("advert", this.modelMapper.map(this.partAdvertisementService.findById(id), PartAdvertisementViewModel.class));
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete/{id}")
    public ModelAndView confirmDeletePart(@PathVariable("id") String id, ModelAndView modelAndView) {
        this.partAdvertisementService.deleteById(id);
        modelAndView.setViewName("redirect:/marketplace");
        return modelAndView;
    }
}
