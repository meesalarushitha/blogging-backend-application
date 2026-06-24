package com.example.blogapp.Controllers;

import com.example.blogapp.Dto.ApiResponse;
import com.example.blogapp.Dto.CategoryDto;
import com.example.blogapp.ServiceInterfaces.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // Create Category
    @PostMapping
    public CategoryDto createCategory(
            @RequestBody CategoryDto categoryDto) {

        return categoryService.createCategory(categoryDto);
    }

    // Update Category
    @PutMapping("/{categoryId}")
    public CategoryDto updateCategory(
            @RequestBody CategoryDto categoryDto,
            @PathVariable Integer categoryId) {

        return categoryService.updateCategory(
                categoryDto,
                categoryId
        );
    }

    // Get Category By Id
    @GetMapping("/{categoryId}")
    public CategoryDto getCategoryById(
            @PathVariable Integer categoryId) {

        return categoryService.getCategoryById(categoryId);
    }

    // Get All Categories
    @GetMapping
    public List<CategoryDto> getAllCategories() {

        return categoryService.getAllCategories();
    }

    // Delete Category
    @DeleteMapping("/{categoryId}")
    public ApiResponse deleteCategory(
            @PathVariable Integer categoryId) {

        categoryService.deleteCategory(categoryId);

        return new ApiResponse(
                "Category deleted successfully",
                true
        );
    }
}
