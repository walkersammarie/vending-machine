package com.techelevator.view;

import com.techelevator.Products.Chip;
import org.junit.Assert;
import org.junit.Test;

public class ProductTests {

    @Test
    public void subtract_1_from_product_quantity() {
        Chip sut = new Chip("Doritos", "1.50");
        sut.subtractOne();
        sut.subtractOne();
        int result = sut.getQuantity();
        Assert.assertEquals(3, result);
    }

}
