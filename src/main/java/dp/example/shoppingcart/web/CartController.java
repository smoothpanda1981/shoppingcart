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

package dp.example.shoppingcart.web;

import dp.example.shoppingcart.dto.Item;
import dp.example.shoppingcart.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

/**
 * Basic controller
 * 
 * @author DPavlov
 */
@Controller
public class CartController {
	
	private CartService cartService;
	
	private int counter = 1;

	@Autowired
	public CartController(CartService cartService) {
		this.cartService = cartService;
	}
	
	@RequestMapping(value = "/cart/{pk}", method = RequestMethod.GET)
	public ModelAndView getCart(@PathVariable final long pk) {
		final List<Item> list = cartService.getItemsInCart(pk);
		ModelAndView mav = new ModelAndView("cart");
		mav.addObject("pk", pk);
		mav.addObject("items", list);
		mav.addObject("add", "Item" + counter++);
		return mav;
		
	}
	
	@RequestMapping(value = "/cart/{pk}/addtocart/{item}", method = RequestMethod.GET)
	public String addToCart(@PathVariable final long pk, @PathVariable final String item) {
		
		cartService.addToCart(pk, item);
		return "redirect:/cart/" + pk;  // Do redirect to prevent double post
		
	}
	
	@RequestMapping(value = "/cart/{pk}/removefromcart/{item}", method = RequestMethod.GET)
	public String removeFromCart(@PathVariable final long pk, @PathVariable final String item) {
		
		cartService.removeFromCart(pk, item);
        return "redirect:/cart/" + pk;  // Do redirect to prevent double post
		
	}

}
