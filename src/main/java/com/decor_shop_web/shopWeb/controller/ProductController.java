package com.decor_shop_web.shopWeb.controller;

import com.decor_shop_web.shopWeb.model.Product;
import com.decor_shop_web.shopWeb.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("decorations")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping(path = "/list")
    public ResponseEntity<List<Product>> listAll(){
        return ResponseEntity.ok(productService.listAll());
    }

    @GetMapping(path = "/find/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable Long id){
        return ResponseEntity.ok(productService.findById(id));
    }

    @GetMapping(path = "/find/{name}")
    public ResponseEntity<List<Product>> getProductById(@PathVariable String name){
        return ResponseEntity.ok(productService.findByName(name));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id){
        productService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Product> saveProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @PutMapping(path = "/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.replace(product), HttpStatus.NO_CONTENT);
    }

}
