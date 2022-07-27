package com.bvk.bvktest.controller;
import com.bvk.bvktest.exception.BadRequestException;
import com.bvk.bvktest.exception.ResourceNotFoundException;
import com.bvk.bvktest.model.Product;
import com.bvk.bvktest.security.CurrentUser;
import com.bvk.bvktest.security.UserPrincipal;
import com.bvk.bvktest.services.ProductService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService productService;

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Page<List<Product>>> getAll(
        @CurrentUser UserPrincipal userPrincipal,
        Pageable pageable,
        @RequestParam(name = "keyword", defaultValue = "") String keyword
    ) {
        return ResponseEntity.ok(productService.findAll(pageable, keyword));
    }

    @PostMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> addProduct(
        @CurrentUser UserPrincipal userPrincipal,
        @RequestBody Product request
    ) {
        try {            
            return ResponseEntity.ok(productService.addOrUpdateProduct(request));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateProduct(
        @CurrentUser UserPrincipal userPrincipal,
        @RequestBody Product request
    ) {
        try {            
            return ResponseEntity.ok(productService.addOrUpdateProduct(request));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    @DeleteMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> deleteProduct(
        @CurrentUser UserPrincipal userPrincipal,
        @RequestBody Product request
    ) {
        try {            
            return ResponseEntity.ok(productService.deleteProduct(request));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (BadRequestException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
    
}

