package com.productservice.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.productservice.dtos.ProductDTO;
import com.productservice.dtos.RatingDTO;
import com.productservice.models.Categories;
import com.productservice.models.Product;
import com.productservice.models.Rating;
import com.productservice.services.FakeStoreProductService;
import com.productservice.services.IProductService;

@RestController
@RequestMapping("/products")
public class ProductController {
	
	private FakeStoreProductService productService;
	
	public ProductController(FakeStoreProductService productService) {
		this.productService=productService;
	}
	
	@GetMapping("")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
		List<Product> products =  productService.getAllProducts();

        List<ProductDTO> productDTOS = new ArrayList<>();

        for (Product product: products) {
            ProductDTO productDTO = convertToProductDTO(product);
            productDTOS.add(productDTO);
        }
		
		return new ResponseEntity<>(productDTOS, HttpStatus.OK);
    }
	
	@GetMapping("/{id}")
	public ResponseEntity<ProductDTO>getSingleProduct(@PathVariable("id") Long productId){
		Product product =  productService.getSingleProduct(productId);
	    ProductDTO productDTO = convertToProductDTO(product);
	    return new ResponseEntity<>(productDTO,HttpStatus.OK);
	}
	
	@PostMapping()
    public ResponseEntity<ProductDTO> addNewProduct(@RequestBody ProductDTO productDTO) {
		Product product = convertToProduct(productDTO);
        Product  DBproduct = productService.addNewProduct(product);
        ProductDTO dbproductDTO = convertToProductDTO(DBproduct);
        return new ResponseEntity<>(dbproductDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable("productId") long id, @RequestBody ProductDTO productDTO) {
    	Product product = convertToProduct(productDTO);
        Product DBproduct = productService.updateProduct(id,product);
        ProductDTO DBproductDTO = convertToProductDTO(DBproduct);
        ResponseEntity<ProductDTO> responseEntity = new ResponseEntity<>(DBproductDTO, HttpStatus.OK);
        return responseEntity;
    }


    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(@PathVariable("productId") Long productId) {
    	Product BDproduct = productService.deleteProduct(productId);
        ProductDTO DBproductDTO = convertToProductDTO(BDproduct);
        return new ResponseEntity<>(DBproductDTO, HttpStatus.NO_CONTENT);    
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
        rating.setRate(productDTO.getRating().getRate());
        rating.setCount(productDTO.getRating().getCount());
        product.setRating(rating);
        return product;
    }
}
