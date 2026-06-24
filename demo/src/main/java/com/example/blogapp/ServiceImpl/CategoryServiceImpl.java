package com.example.blogapp.ServiceImpl;

import com.example.blogapp.Dto.CategoryDto;
import com.example.blogapp.Entity.Category;
import com.example.blogapp.Exception.ResourceNotFoundException;
import com.example.blogapp.Repository.CategoryRepository;
import com.example.blogapp.ServiceInterfaces.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        CategoryDto cat3 = null;
        Optional<Category> category =
                categoryRepository.findById(categoryDto.getCategoryId());

        if(category.isEmpty()) {
           Category cat = modelMapper.map(categoryDto,Category.class);
          Category cat2= categoryRepository.save(cat);
            cat3= modelMapper.map(cat2,CategoryDto.class);
        }
    return cat3;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "id", categoryId));

        category.setCategoryTitle(categoryDto.getCategoryTitle());
        category.setCategoryDescription(categoryDto.getCategoryDescription());

        Category updatedCategory = categoryRepository.save(category);

        return modelMapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto getCategoryById(Integer categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("categoryId","Id",categoryId));
        return modelMapper.map(category,CategoryDto.class);
    }

    @Override
    public List<CategoryDto> getAllCategories() {

        return categoryRepository.findAll()
                .stream()
                .map(category ->modelMapper.map(category,CategoryDto.class))
                .toList();
    }

    @Override
    public void deleteCategory(Integer categoryId) {
        categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("CategoryId","Id",categoryId));
        categoryRepository.deleteById(categoryId);
    }
}
