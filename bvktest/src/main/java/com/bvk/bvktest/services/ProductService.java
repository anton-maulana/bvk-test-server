package com.bvk.bvktest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.bvk.bvktest.model.Product;
import com.bvk.bvktest.repository.ProductRepository;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public Page<List<Product>> findAll(Pageable pageable, String keyword) {
        return productRepository.findProducts(pageable, keyword);
    }

    public Product addOrUpdateProduct(Product requesProduct){
        productRepository.save(requesProduct);
        return requesProduct;
    }

    public Product deleteProduct(Product requesProduct){
        productRepository.deleteById(requesProduct.getId());
        return requesProduct;
    }
}
