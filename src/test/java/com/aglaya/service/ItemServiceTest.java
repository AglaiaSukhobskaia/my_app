package com.aglaya.service;

import com.aglaya.BaseIntegrationTest;
import com.aglaya.dto.request.ItemRq;
import com.aglaya.dto.response.ItemRs;
import com.aglaya.model.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ItemServiceTest extends BaseIntegrationTest {
    @Autowired
    ItemService itemService;

    @Test
    void createItem() {
        var name = "Тестовый товар 3";
        var itemRq = new ItemRq(name);

        var actual = itemService.createItem(itemRq);

        assertThat(actual).isNotNull()
                .hasFieldOrPropertyWithValue("name", name)
                .hasFieldOrProperty("id");
    }

    @Test
    void getItem() {
        var id = 1;
        var name = "Тестовый товар 1";
        var expected = new ItemRs(id, name);

        var actual = itemService.getItem(id);

        assertEquals(expected, actual);
    }
}