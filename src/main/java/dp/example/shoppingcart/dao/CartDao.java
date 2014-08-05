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

package dp.example.shoppingcart.dao;

import dp.example.shoppingcart.dto.Cart;
import dp.example.shoppingcart.dto.Item;

import java.util.List;

/**
 * Cart service.
 * 
 * @author DPavlov
 */
public interface CartDao
{

    Cart createCart();

    Cart findCart(long pk);

    void save(Cart cart);

}
