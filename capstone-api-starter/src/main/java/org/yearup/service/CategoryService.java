package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.Category;
import org.yearup.models.Product;
import org.yearup.repository.CategoryRepository;

import java.util.List;

@Service
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository)
    {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAllCategories()
    {
        // get all categories
         return categoryRepository.findAll();
    }

    public Category getById(int categoryId)
    {
        // get category by id
        return categoryRepository.findById(categoryId).orElse(null);
    }

    public Category create(Category category)
    {
        // create a new category
        return categoryRepository.save(category);
    }

    public Category update(int categoryId, Category category)
    {
        return categoryRepository.findById(categoryId).map(existing -> {
            existing.setCategoryId(category.getCategoryId());
            existing.setName(category.getName());
            existing.setDescription(category.getDescription());
            return categoryRepository.save(existing);
        }).orElse(null);
    }

    public void delete(int categoryId)
    {
        categoryRepository.deleteById(categoryId);
    }
}
