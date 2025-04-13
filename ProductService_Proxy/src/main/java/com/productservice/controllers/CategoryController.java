package com.productservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productservice.dtos.ProductDTO;
import com.productservice.dtos.RatingDTO;
import com.productservice.models.Categories;
import com.productservice.models.Product;
import com.productservice.models.Rating;
import com.productservice.services.FakeStoreCategoryService;
import com.productservice.services.ICategoryService;

@RestController
public class CategoryController {
	
	@Autowired
    private FakeStoreCategoryService categoryService;

    @GetMapping("/products/category/{categoryName}")
    public List<ProductDTO> getInCategory(@PathVariable("categoryName") String categoryName){
        List<Product> products = categoryService.getInCategory(categoryName);

        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product: products) {
            ProductDTO productDTO = convertToProductDTO(product);
            productDTOS.add(productDTO);
        }

        return productDTOS;
    }

    @GetMapping("/products/categories")
    public List<String> getAllCategories(){
        List<String> categories = categoryService.getAllCategory();
        return categories;
    }

	
	//helper methods
    private ProductDTO convertToProductDTO(Product product){
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setTitle(product.getTitle());
        productDTO.setPrice(product.getPrice());
        productDTO.setCategory(product.getCategory().getName());
        productDTO.setImage(product.getImageUrl());
        productDTO.setDescription(product.getDescription());
        RatingDTO ratingDto = new RatingDTO();
        ratingDto.setRate(product.getRating().getRate());
        ratingDto.setCount(product.getRating().getCount());
        productDTO.setRating(ratingDto);
        return productDTO;
    }

    private Product convertToProduct(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setTitle(productDTO.getTitle());
        product.setPrice(productDTO.getPrice());
        Categories category = new Categories();
        category.setName(productDTO.getCategory());
        product.setCategory(category);
        product.setImageUrl(productDTO.getImage());
        product.setDescription(productDTO.getDescription());
        Rating rating = new Rating();
//        rating.setRate(productDTO.getRating().getRate());
//        rating.setCount(productDTO.getRating().getCount());
        product.setRating(rating);
        return product;
    }

}
