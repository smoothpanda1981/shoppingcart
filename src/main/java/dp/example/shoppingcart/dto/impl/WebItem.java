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

import dp.example.shoppingcart.dto.Item;


public class WebItem implements Item
{

	private String articleNo;
	private BigDecimal quantity;
	private BigDecimal price;
	
	public WebItem(String articleNo, BigDecimal price, BigDecimal quantity) {
		this.articleNo = articleNo;
		this.price = price;
		this.quantity = quantity;
	}
	
	public WebItem(Item item, BigDecimal quantity) {
		this.articleNo = item.getArticleNo();
		this.price = item.getPrice();
		this.quantity = quantity;
	}

	public String getArticleNo() {
		return articleNo;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public BigDecimal getQuantity() {
		return quantity;
	}

}
