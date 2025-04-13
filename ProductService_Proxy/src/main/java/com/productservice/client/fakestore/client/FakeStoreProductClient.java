package com.productservice.client.fakestore.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.productservice.client.fakestore.DTO.FakeStoreProductDTO;
import com.productservice.dtos.RatingDTO;
import com.productservice.models.Categories;
import com.productservice.models.Product;
import com.productservice.models.Rating;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Service
public class FakeStoreProductClient implements FakeStoreClient{
	private RestTemplateBuilder restTemplateBuilder;

    @Autowired
    public FakeStoreProductClient(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplateBuilder = restTemplateBuilder;
	}

	
	@Override
	public List<Product> getAllProducts() {
		RestTemplate restTemplate=restTemplateBuilder.build();
		HttpEntity<FakeStoreProductDTO>entity=new HttpEntity<>(getHttpHeaders());
		ResponseEntity<FakeStoreProductDTO[]>response=restTemplate.exchange("https://fakestoreapi.com/products", HttpMethod.GET,entity,FakeStoreProductDTO[].class);
		FakeStoreProductDTO[] fakeStoreProductDTOs=response.getBody();
		List<Product>products=new ArrayList<>();
		for(FakeStoreProductDTO fakeStoreProductDTO: fakeStoreProductDTOs) {
			Product product=convertToProduct(fakeStoreProductDTO);
			products.add(product);
		}
		
		return products;
	}

	@Override
	public Product getSingleProduct(Long productId) {
		String url="https://fakestoreapi.com/products/";
		StringBuilder url1=new StringBuilder(url);
		url1.append(productId);
		url=url1.toString();
		RestTemplate restTemplate=restTemplateBuilder.build();
		HttpEntity<FakeStoreProductDTO>entity=new HttpEntity<>(getHttpHeaders());
		ResponseEntity<FakeStoreProductDTO>fakeStoreProductDTO=restTemplate.exchange(url, HttpMethod.GET,entity,FakeStoreProductDTO.class);
		
		Product product=convertToProduct(fakeStoreProductDTO.getBody());
		return product;
	}

	@Override
	public Product addNewProduct(Product product) {
		FakeStoreProductDTO fakeStoreProductDTO = convertToFakeStoreProductDTO(product);
		HttpEntity<FakeStoreProductDTO>entity=new HttpEntity<>(fakeStoreProductDTO,getHttpHeaders());
		RestTemplate restTemplate=restTemplateBuilder.build();
		ResponseEntity<FakeStoreProductDTO>response=restTemplate.exchange("https://fakestoreapi.com/products", HttpMethod.POST,entity,FakeStoreProductDTO.class);
		
		FakeStoreProductDTO fakeStoreProductDTO1=response.getBody();
		fakeStoreProductDTO1.setRating(new RatingDTO());
        Product product1 = convertToProduct(fakeStoreProductDTO1);
        return product1;
	}
	
	public Product updateProduct(Long productId, Product product) {
		String url="https://fakestoreapi.com/products/";
		StringBuilder url1=new StringBuilder(url);
		url1.append(productId);
		url=url1.toString();
		RestTemplate restTemplate=restTemplateBuilder.build();
        FakeStoreProductDTO fakeStoreProductDTO =  convertToFakeStoreProductDTO(product);
        HttpEntity<FakeStoreProductDTO>entity=new HttpEntity<>(fakeStoreProductDTO,getHttpHeaders());
        ResponseEntity<FakeStoreProductDTO>responseEntity=restTemplate.exchange(url, HttpMethod.PATCH,entity,FakeStoreProductDTO.class);
        FakeStoreProductDTO DBfakeStoreProductDTO  = responseEntity.getBody();
        DBfakeStoreProductDTO.setRating(new RatingDTO());
        Product dbproduct = convertToProduct(DBfakeStoreProductDTO);
        return dbproduct;
    }
	
	
	@Override
	public Product deleteProduct(Long productId) {
		String url="https://fakestoreapi.com/products/";
		StringBuilder url1=new StringBuilder(url);
		url1.append(productId);
		url=url1.toString();
		RestTemplate restTemplate=restTemplateBuilder.build();
		HttpEntity<FakeStoreProductDTO>entity=new HttpEntity<>(getHttpHeaders());
		ResponseEntity<FakeStoreProductDTO>responseEntity=restTemplate.exchange(url, HttpMethod.DELETE,entity,FakeStoreProductDTO.class);

        FakeStoreProductDTO DBfakeStoreProductDTO  = responseEntity.getBody();
        DBfakeStoreProductDTO.setRating(new RatingDTO());
        Product dbproduct = convertToProduct(DBfakeStoreProductDTO);
        dbproduct.setImageUrl("product is deleted");
        return dbproduct;
	}

	@Override
	public List<String> getAllCategory() {
		RestTemplate restTemplate = restTemplateBuilder.build();
		String url="https://fakestoreapi.com/products/categories";
		
		HttpEntity<String> entity=new HttpEntity<>(getHttpHeaders());
		ResponseEntity<String[]>response=restTemplate.exchange(url, HttpMethod.GET,entity,String[].class);
		String[] categories=response.getBody();
        List<String> categoryList = new ArrayList<>();
        for(String category: categories) {
            categoryList.add(category);
        }
        return  categoryList;
	}

	@Override
	public List<Product> getInCategory(String categoryName) {
		RestTemplate restTemplate = restTemplateBuilder.build();
		String url="https://fakestoreapi.com/products/category/";
		StringBuilder url1=new StringBuilder(url);
		url1.append(categoryName);
		url=url1.toString();
		
		HttpEntity<FakeStoreProductDTO>entity=new HttpEntity<>(getHttpHeaders());
		ResponseEntity<FakeStoreProductDTO[]>responseEntity = restTemplate.exchange(url, HttpMethod.GET,entity,FakeStoreProductDTO[].class);
        FakeStoreProductDTO[] fakeStoreProductDTOs =responseEntity.getBody();
               
        List<Product> products = new ArrayList<>();

        for (FakeStoreProductDTO fakeStoreProductDTO: fakeStoreProductDTOs) {
            Product product = convertToProduct(fakeStoreProductDTO);
            products.add(product);
        }
        return products;
	}

	//Helper methods
	
	public FakeStoreProductDTO convertToFakeStoreProductDTO(Product product) {
        FakeStoreProductDTO fakeStoreProductDTO = new FakeStoreProductDTO();
        fakeStoreProductDTO.setId(product.getId());
        fakeStoreProductDTO.setTitle(product.getTitle());
        fakeStoreProductDTO.setPrice(product.getPrice());
        fakeStoreProductDTO.setCategory(product.getCategory().getName());
        fakeStoreProductDTO.setImage(product.getImageUrl());
        fakeStoreProductDTO.setDescription(product.getDescription());
        RatingDTO ratingDto = new RatingDTO();
        ratingDto.setRate(product.getRating().getRate());
        ratingDto.setCount(product.getRating().getCount());

        return fakeStoreProductDTO;

    }
	
	private Product convertToProduct(FakeStoreProductDTO fakeStoreProductDTO) {
		Product product =new Product();
		product.setId(fakeStoreProductDTO.getId());
		product.setTitle(fakeStoreProductDTO.getTitle());
		product.setPrice(fakeStoreProductDTO.getPrice());
		
		Categories categories=new Categories();
		categories.setName(fakeStoreProductDTO.getCategory());
		
		product.setCategory(categories);
		product.setImageUrl(fakeStoreProductDTO.getImage());
		product.setDescription(fakeStoreProductDTO.getDescription());
		
		Rating rating=new Rating();
		rating.setRate(fakeStoreProductDTO.getRating().getRate());
		rating.setCount(fakeStoreProductDTO.getRating().getCount());
		
		product.setRating(rating);
		
		return product;
		
	}
	
	private HttpHeaders getHttpHeaders() {
		HttpHeaders headers=new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		headers.setContentType(MediaType.APPLICATION_JSON);
		
		return headers;
	}

}
