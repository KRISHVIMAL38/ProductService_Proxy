package com.productservice.services;

import java.util.List;

import com.productservice.models.Product;

public interface IProductService {
	List<Product> getAllProducts();
	
	Product getSingleProduct(Long productId);
	
	Product addNewProduct(Product product);
	
	Product updateProduct(Long productId,Product product);
	
	Product deleteProduct(Long productId);
}
