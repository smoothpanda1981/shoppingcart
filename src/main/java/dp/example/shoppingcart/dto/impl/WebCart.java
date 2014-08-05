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

package dp.example.shoppingcart.dto.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import dp.example.shoppingcart.dto.Cart;
import dp.example.shoppingcart.dto.Item;

/**
 * Web app implementation of cart.
 * 
 * @author DPavlov
 */
public class WebCart implements Cart
{

    private long cartId;
	private SortedMap<String, Item> items = new TreeMap<String, Item>();

    public long getCartId() {
        return cartId;
    }

    public void setCartId(final long cartId) {
        this.cartId = cartId;
    }

    public void addItem(String item) {
		if (!items.containsKey(item)) {
			items.put(item, new WebItem(item, new BigDecimal(Math.random(), new MathContext(2, RoundingMode.HALF_EVEN)), BigDecimal.ONE));
		} else if (items.containsKey(item)) {
			final Item old = items.get(item);
			items.put(item, new WebItem(old, BigDecimal.ONE.add(old.getQuantity())));
		}
	}

	public List<Item> getItems() {
		return new ArrayList<Item>(items.values());
	}

	public void removeItem(String item) {
		if (items.containsKey(item)) {
			items.remove(item);
		}		
	}

	
	
}
