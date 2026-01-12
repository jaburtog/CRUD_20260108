package com.inventory.rest;

import com.inventory.model.Product;
import com.inventory.service.ProductService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Path("/products")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProductResource {
    
    @Inject
    private ProductService productService;
    
    @GET
    public Response getAllProducts() {
        List<Product> products = productService.getAllProducts();
        return Response.ok(products).build();
    }
    
    @GET
    @Path("/{id}")
    public Response getProduct(@PathParam("id") Long id) {
        Optional<Product> product = productService.getProduct(id);
        if (product.isPresent()) {
            return Response.ok(product.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Product not found\"}").build();
    }
    
    @GET
    @Path("/sku/{sku}")
    public Response getProductBySku(@PathParam("sku") String sku) {
        Optional<Product> product = productService.getProductBySku(sku);
        if (product.isPresent()) {
            return Response.ok(product.get()).build();
        }
        return Response.status(Response.Status.NOT_FOUND)
                .entity("{\"error\": \"Product not found\"}").build();
    }
    
    @GET
    @Path("/category/{category}")
    public Response getProductsByCategory(@PathParam("category") String category) {
        List<Product> products = productService.getProductsByCategory(category);
        return Response.ok(products).build();
    }
    
    @POST
    public Response createProduct(Product product) {
        try {
            Product created = productService.createProduct(product);
            return Response.status(Response.Status.CREATED).entity(created).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to create product\"}").build();
        }
    }
    
    @PUT
    @Path("/{id}")
    public Response updateProduct(@PathParam("id") Long id, Product product) {
        try {
            Product updated = productService.updateProduct(id, product);
            return Response.ok(updated).build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to update product\"}").build();
        }
    }
    
    @DELETE
    @Path("/{id}")
    public Response deleteProduct(@PathParam("id") Long id) {
        try {
            productService.deleteProduct(id);
            return Response.noContent().build();
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("{\"error\": \"" + e.getMessage() + "\"}").build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("{\"error\": \"Failed to delete product\"}").build();
        }
    }
    
    @GET
    @Path("/count")
    public Response getCount() {
        long count = productService.getProductCount();
        return Response.ok("{\"count\": " + count + "}").build();
    }
}
