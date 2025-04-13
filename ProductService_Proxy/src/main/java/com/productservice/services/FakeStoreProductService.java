package com.productservice.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.productservice.client.IClientProductDTO;
import com.productservice.client.fakestore.DTO.FakeStoreProductDTO;
import com.productservice.client.fakestore.client.FakeStoreClient;
import com.productservice.client.fakestore.client.FakeStoreProductClient;
import com.productservice.dtos.ProductDTO;
import com.productservice.models.Categories;
import com.productservice.models.Product;
import com.productservice.repository.ProductRepository;

import jakarta.annotation.Nullable;

@Service
public class FakeStoreProductService implements IProductService{
	
	private FakeStoreProductClient fakeStoreClient;
	private final ProductRepository productRepository;
	
	@Autowired
	public FakeStoreProductService(FakeStoreProductClient fakeStoreClient, ProductRepository productRepository) {
		this.fakeStoreClient = fakeStoreClient;
		this.productRepository = productRepository;
	}

	@Override
	public List<Product> getAllProducts() {
		
		List<Product> products=fakeStoreClient.getAllProducts();
		//save in DB
        //convert to productDTO
		return products;
	}

	@Override
	public Product getSingleProduct(Long productId) {
		Product product=fakeStoreClient.getSingleProduct(productId);
		//save in DB
		
		return product;
	}

	
	@Override
    public Product addNewProduct(Product product) {
		 Product dbproduct = fakeStoreClient.addNewProduct(product);
	     //save in DB
	     return  dbproduct;
    }
	
	@Override
	public Product updateProduct(Long productId,Product product) {
		Product dbproduct = fakeStoreClient.updateProduct(productId, product);
        return dbproduct;
	}

	@Override
	public Product deleteProduct(Long productId) {
		Product dbproduct = fakeStoreClient.deleteProduct(productId);
        return dbproduct;
	}
	
}
