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

package dp.example.shoppingcart.domain.impl;

import dp.example.shoppingcart.dto.Cart;
import dp.example.shoppingcart.dto.Item;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Entity implementation of cart.
 * 
 * @author DPavlov
 */
public class CartEntity implements Cart
{

    private long cartId;
	private Set<ItemEntity> itemsInternal = new HashSet<ItemEntity>();
	
	public void addItem(String item) {
        final ItemEntity toUpdate = findItem(item);
		if (toUpdate == null) {
			itemsInternal.add(new ItemEntity(item, new BigDecimal(Math.random(), new MathContext(2, RoundingMode.HALF_EVEN)), BigDecimal.ONE));
		} else {
            toUpdate.setQuantity(BigDecimal.ONE.add(toUpdate.getQuantity()));
		}
	}

    private ItemEntity findItem(final String item) {
        ItemEntity toUpdate = null;
        for (final ItemEntity existing : itemsInternal) {
            if (existing.getArticleNo().equals(item)) {
                toUpdate = existing;
            }
        }
        return toUpdate;
    }

    public List<Item> getItems() {
		return new ArrayList<Item>(itemsInternal);
	}

    public Set<ItemEntity> getItemsInternal() {
        return itemsInternal;
    }

    public void setItemsInternal(final Set<ItemEntity> itemsInternal) {
        this.itemsInternal = itemsInternal;
    }

    public void removeItem(String item) {
        final ItemEntity toRemove = findItem(item);
		if (toRemove != null) {
			itemsInternal.remove(toRemove);
		}		
	}

    public long getCartId() {
        return cartId;
    }

    public void setCartId(final long cartId) {
        this.cartId = cartId;
    }
}
