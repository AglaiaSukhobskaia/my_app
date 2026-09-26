package com.aglaya.service;

import com.aglaya.dto.request.ItemRq;
import com.aglaya.dto.response.ItemRs;
import com.aglaya.model.Item;
import com.aglaya.repository.ItemRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemService {
    ItemRepository itemRepository;

    public ItemRs createItem(ItemRq itemRq) {
        var item = new Item();
        item.setName(itemRq.name());
        item = itemRepository.save(item);
        return ItemRs.builder()
                .id(item.getId())
                .name(item.getName())
                .build();
    }

    public ItemRs getItem(Integer id) {
        var item = itemRepository.findById(id).orElseThrow();
        return ItemRs.builder()
                .id(item.getId())
                .name(item.getName())
                .build();
    }
}
