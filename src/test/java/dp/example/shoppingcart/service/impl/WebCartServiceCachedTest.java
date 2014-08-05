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

import dp.example.shoppingcart.dto.Item;
import dp.example.shoppingcart.service.CartService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Basic example of integration test. This is a very simplistic version but shows all
 * the big concepts such as how to tests against actual DB with full spring context up
 * and hows how to assert caching is working correctly.
 *
 * THIS IS NOT REPLACEMENT FOR UNIT TESTS. This tutorial assumes that you are familiar
 * with it!
 *
 * This kind of test is the final frontier! Before doing such tests appropriate
 * unit test needs to be done! Please do not substitute these kind of tests for unit tests
 * as they are considerably slower to run.
 *
 * LAST NOTE: There is no DB clean up in tests - you have to do this manually after each
 * test so that DB state does not influence your tests. Remember JUNIT does not guarantee
 * the order of execution for tests! If you do need to preserve state - use TestNG!
 *
 * User: denispavlov
 * Date: 29/11/2013
 * Time: 13:17
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/spring/testContext.xml" })
public class WebCartServiceCachedTest {

    @Autowired
    private CartService cartService;

    /**
     * This is NOT unit test but rather an integration suite that proves that
     * our business logic works as expected using actual db and all the wiring.
     */
    @Test
    public void testCartCRUDOperationsIntegrationTest() throws Exception {

        List<Item> items;

        // Check empty initially
        items = cartService.getItemsInCart(1L);

        assertNotNull(items);
        assertTrue(items.isEmpty());

        // Add one item and check contents of cart
        cartService.addToCart(1L, "ABC");
        items = cartService.getItemsInCart(1L);

        assertNotNull(items);
        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
        assertEquals("ABC", items.get(0).getArticleNo());
        assertEquals(new BigDecimal(1).compareTo(items.get(0).getQuantity()), 0);

        // Add same item and check contents of cart
        cartService.addToCart(1L, "ABC");
        items = cartService.getItemsInCart(1L);

        assertNotNull(items);
        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
        assertEquals("ABC", items.get(0).getArticleNo());
        assertEquals(new BigDecimal(2).compareTo(items.get(0).getQuantity()), 0);

        // Add another item and check contents of cart
        cartService.addToCart(1L, "DEF");
        items = cartService.getItemsInCart(1L);

        assertNotNull(items);
        assertFalse(items.isEmpty());
        assertEquals(2, items.size());
        // we re-map the items in list to have deterministic assertions
        final Map<String, Item> byArticle = new HashMap<String, Item>();
        for (final Item item : items) {
            byArticle.put(item.getArticleNo(), item);
        }
        // deterministic assertions by article no
        assertTrue(byArticle.containsKey("ABC"));
        assertEquals(new BigDecimal(2).compareTo(byArticle.get("ABC").getQuantity()), 0);
        assertTrue(byArticle.containsKey("DEF"));
        assertEquals(new BigDecimal(1).compareTo(byArticle.get("DEF").getQuantity()), 0);

        // Remove one item
        cartService.removeFromCart(1L, "DEF");
        items = cartService.getItemsInCart(1L);

        assertNotNull(items);
        assertFalse(items.isEmpty());
        assertEquals(1, items.size());
        assertEquals("ABC", items.get(0).getArticleNo());
        assertEquals(new BigDecimal(2).compareTo(items.get(0).getQuantity()), 0);

        // Remove last item
        cartService.removeFromCart(1L, "ABC");
        items = cartService.getItemsInCart(1L);

        assertNotNull(items);
        assertTrue(items.isEmpty());

    }

    /**
     * Since we already established that the CRUD works assertions in this test are specific
     * to making sure that we get the cached list back, so we do not need to check individual elements.
     *
     * @throws Exception
     */
    @Test
    public void testCachingIsWorking() throws Exception {

        // Check empty initially
        List<Item> items1st = cartService.getItemsInCart(2L);
        assertEquals(0, items1st.size());

        // add cart item
        cartService.addToCart(2L, "ABC");
        List<Item> items2nd = cartService.getItemsInCart(2L);
        assertEquals(1, items2nd.size());

        // now we can ask for the cart contents again and assert that is it same instance of list
        List<Item> items3rd = cartService.getItemsInCart(2L);
        assertSame(items3rd, items2nd);

        // we expect that adding items will evict cache
        cartService.addToCart(2L, "ABC");
        List<Item> items4th = cartService.getItemsInCart(2L);
        assertEquals(1, items4th.size());
        // but these list should be different now
        assertNotSame(items2nd, items4th);

        // now check the cart contents again which should be cached object from 4th call
        List<Item> items5th = cartService.getItemsInCart(2L);
        assertSame(items4th, items5th);

        // make sure that after adding to a different cart we still have cached version of list for 2L
        cartService.addToCart(1L, "ABC");
        List<Item> items6th = cartService.getItemsInCart(2L);
        assertSame(items4th, items6th);


    }
}
