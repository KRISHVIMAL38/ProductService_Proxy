package com.productservice.services;

import java.util.List;

import com.productservice.models.Product;

public interface ICategoryService {
	public List<String>getAllCategory();
	
	public List<Product> getInCategory(String categoryName);
}
