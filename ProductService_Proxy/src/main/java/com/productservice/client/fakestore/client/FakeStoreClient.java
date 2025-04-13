package com.productservice.client.fakestore.client;

import java.util.List;

import com.productservice.models.Product;


public interface FakeStoreClient {
	public List<Product>getAllProducts();
	
	public Product getSingleProduct(Long productId);
	
	public Product addNewProduct(Product product);
	
	public Product deleteProduct(Long productId);
	
	public List<String>getAllCategory();
	
	public List<Product>getInCategory(String categoryName);
}
