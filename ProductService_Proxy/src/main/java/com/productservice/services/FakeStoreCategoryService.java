package com.productservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.productservice.client.fakestore.client.FakeStoreProductClient;
import com.productservice.models.Product;

@Service
public class FakeStoreCategoryService  implements ICategoryService{
	@Autowired
	private FakeStoreProductClient client;
	
	@Override
	public List<String> getAllCategory() {
		List<String> categories=client.getAllCategory();
		return categories;
	}

	@Override
	public List<Product> getInCategory(String categoryName) {
		List<Product>products=client.getInCategory(categoryName);
		return products;
	}

}
