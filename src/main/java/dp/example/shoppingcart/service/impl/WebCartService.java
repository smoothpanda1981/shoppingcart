/*
 * Copyright (C) Denis Pavlov 2013 www.inspire-software.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dp.example.shoppingcart.service.impl;

import dp.example.shoppingcart.dao.CartDao;
import dp.example.shoppingcart.dto.Cart;
import dp.example.shoppingcart.dto.Item;
import dp.example.shoppingcart.service.CartService;

import java.util.Date;
import java.util.List;

/**
 * Web implementation of cart service
 * 
 * @author DPavlov
 */
public class WebCartService implements CartService
{

    private final CartDao cartDao;

	public WebCartService(final CartDao cartDao) {
		this.cartDao = cartDao;
	}

	public void addToCart(final long pk, String item) {
        final Cart cart = findOrCreateCart(pk);
		cart.addItem(item);
        cartDao.save(cart);
	}

    public List<Item> getItemsInCart(final long pk) {
        final Cart cart = findOrCreateCart(pk);
        return cart.getItems();
	}

	public void removeFromCart(final long pk, String item) {
        final Cart cart = findOrCreateCart(pk);
        cart.removeItem(item);
        cartDao.save(cart);
    }


    private Cart findOrCreateCart(final long pk) {
        //doBadStuff();
        Cart cart = cartDao.findCart(pk);
        if (cart == null) {
            cart = cartDao.createCart();
        }
        return cart;
    }


    private void doBadStuff() {
        final Date now = new Date();
        do { } while ((new Date().getTime() - now.getTime()) < 200L);
    }

}
