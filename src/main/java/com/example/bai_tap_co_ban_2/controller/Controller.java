package com.example.bai_tap_co_ban_2.controller;

import com.example.bai_tap_co_ban_2.model.Category;
import com.example.bai_tap_co_ban_2.model.Food;
import com.example.bai_tap_co_ban_2.service.IServiceCategory;
import com.example.bai_tap_co_ban_2.service.IServiceFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Controller
@RequestMapping("/food")
public class Controller {
    @Autowired
    private IServiceFood serviceFood;

    @Autowired
    private IServiceCategory serviceCategory;

    @Value("${file-upload}")
    private String fileUpload;

    @ModelAttribute("categories")
    public Page<Category> categoryPage() {
        return serviceCategory.findAll(Pageable.unpaged());
    }

//    @ModelAttribute("categories_page")
//    public Category category_Page() {
//        return new Category();
//    }

    @ModelAttribute("foods")
    public Page<Food> foodPage(@PageableDefault(value = 5) @SortDefault(sort = {"price"}, direction = Sort.Direction.ASC) Pageable pageable) {
        return serviceFood.findAll(pageable);
    }

    @GetMapping
    public ModelAndView findAllFood(@PageableDefault(value = 5) @SortDefault(sort = {"price"}, direction = Sort.Direction.ASC) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("display");
        Page<Food> foodPage = serviceFood.findAll(pageable);
        modelAndView.addObject("foods", foodPage);
        return modelAndView;
    }

    @GetMapping("/create")
    public ModelAndView createFood() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("food", new Food());
        return modelAndView;
    }

    @PostMapping("/create")
    public String creat(@ModelAttribute("food") Optional<Food> food,
                        RedirectAttributes redirectAttributes) {
        setImage(food, redirectAttributes);
        return "redirect:http://localhost:8080/food/create";
    }

    @GetMapping("/detail/{id}")
    public ModelAndView detail(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Optional<Food> food = serviceFood.findById(id);
        modelAndView.addObject("food", food.get());
        return modelAndView;
    }

    @GetMapping("/detail/update/{id}")
    public ModelAndView updateFood(@PathVariable("id") Long id) {
        ModelAndView modelAndView = new ModelAndView("update");
        Optional<Food> food = serviceFood.findById(id);
        if (food.isPresent()) {
            modelAndView.addObject("food", food);
        }
        return modelAndView;
    }

    @PostMapping("/detail/update/{id}")
    public String update(@ModelAttribute("food") Optional<Food> food,
                         RedirectAttributes redirectAttributes) {
        setImage(food, redirectAttributes);
        return "redirect:http://localhost:8080/food/detail/" + food.get().getId();
    }

    private void setImage(@ModelAttribute("food") Optional<Food> food, RedirectAttributes redirectAttributes) {
        if (food.isPresent()) {
            MultipartFile multipartFile = food.get().getImage();
            String imageUrl = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(multipartFile.getBytes(), new File(fileUpload
                        + multipartFile.getOriginalFilename()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            food.get().setImageUrl(imageUrl);
            serviceFood.save(food.get());
        } else {
            redirectAttributes.addFlashAttribute("mess", "Tạo không thành công");
        }
        redirectAttributes.addFlashAttribute("mess", "tạo thành công");
    }

//

    @GetMapping("/detail/delete/{id}")
    public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        serviceFood.delete(id);
        redirectAttributes.addFlashAttribute("mess", "Xóa thành công");
        return "redirect:http://localhost:8080/food";
    }

    // tìm theo tên Food
    @PostMapping("/search")
    public ModelAndView searchByName(@RequestParam("search") Optional<String> name, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("display");
        if (name.isPresent()) {
            Page<Food> foodPage = serviceFood.findAllByNameContaining(name.get(), pageable);
            modelAndView.addObject("foods", foodPage);
        }
        return modelAndView;
    }


    @GetMapping("/find-max-price")
    public ModelAndView findMaxPrice() {
        List<Food> foods = serviceFood.findMaxPrice();
        ModelAndView modelAndView = new ModelAndView("display");
        modelAndView.addObject("foods", foods);
        return modelAndView;
    }

    @GetMapping("/find-min-price")
    public ModelAndView findMinPrice() {
        List<Food> foods = serviceFood.findMinPrice();
        ModelAndView modelAndView = new ModelAndView("display");
        modelAndView.addObject("foods", foods);
        return modelAndView;
    }

    @GetMapping("/between-price")
    public ModelAndView findBetweenPrice(@RequestParam("min") Optional<Double> min
            , @RequestParam("max") Optional<Double> max, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("display");
        double minPrice = min.orElse(Double.MIN_VALUE);
        double maxPrice = max.orElse(Double.MAX_VALUE);
        Page<Food> foods = serviceFood.findFoodByPriceBetween(minPrice, maxPrice, pageable);
        modelAndView.addObject("foods", foods);
        return modelAndView;
    }

    @PostMapping("/search-category")
    public ModelAndView searchByCategory(@RequestParam("search-by-category") Optional<String> name, Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("display");
        Page<Food> foodPage = serviceFood.findFoodByCategoryContaining(name.get(), pageable);
        modelAndView.addObject("foods", foodPage);
         return modelAndView;
    }

    @GetMapping("/search-avg-price")
    public ModelAndView findAvgPriceCategory(Pageable pageable) {
        Page<Food> foods = serviceFood.findFoodByAvgPrice(pageable);
        ModelAndView modelAndView = new ModelAndView("display");
        modelAndView.addObject("foods", foods);
        return modelAndView;
    }


}
