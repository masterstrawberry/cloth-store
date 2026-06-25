package org.yearup.service;

import org.springframework.stereotype.Service;
import org.yearup.models.CartItem;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.repository.ShoppingCartRepository;

import java.util.List;

@Service
public class ShoppingCartService
{
    // a shopping cart is built from cart rows plus a product lookup for each row
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductService productService;

    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository, ProductService productService)
    {
        this.shoppingCartRepository = shoppingCartRepository;
        this.productService = productService;
    }

    public ShoppingCart getByUserId(int userId)
    {
        // load the user's cart rows, look up each product, and build the ShoppingCart
        ShoppingCart shoppingCart = new ShoppingCart();
        List<CartItem> CartItems = shoppingCartRepository.findByUserId(userId);

        for (CartItem cartItem : CartItems) {
            Product product = productService.getById(cartItem.getProductId());
            ShoppingCartItem item = new ShoppingCartItem();
            item.setProduct(product);
            item.setQuantity(cartItem.getQuantity());
            shoppingCart.add(item);
        }

//        items.stream().map(ShoppingItem ->{
//            ShoppingCartItem item = new ShoppingCartItem();
//            item.setProduct(productService.getById(ShoppingItem.getProductId()));
//            item.setQuantity(ShoppingItem.getQuantity());
//            return item;
//        }).forEach(shoppingCart::add);
        return shoppingCart;
    }

    // add additional methods here
    public ShoppingCart addProductToCart(int userId,int productId){
        CartItem cartItem = shoppingCartRepository.findByUserIdAndProductId(userId,productId);
        if (cartItem == null){
            CartItem newCartItem = new CartItem();
            newCartItem.setUserId(userId);
            newCartItem.setProductId(productId);
            newCartItem.setQuantity(1);
            shoppingCartRepository.save(newCartItem);
        }else {
            cartItem.setQuantity(cartItem.getQuantity()+1);
            shoppingCartRepository.save(cartItem);
        }
        return getByUserId(userId);

    }

    public ShoppingCart editProduct(int userId,int productId, int quantity){
        CartItem cartItem = shoppingCartRepository.findByUserIdAndProductId(userId,productId);
        if (cartItem != null){
            cartItem.setQuantity(quantity);
            shoppingCartRepository.save(cartItem);
        }

        return getByUserId(userId);

    }

    public void clearCart(int userId){
        shoppingCartRepository.deleteByUserId(userId);
    }
}
