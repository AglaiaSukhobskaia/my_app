package com.aglaya.controller;

import com.aglaya.dto.request.ItemRq;
import com.aglaya.dto.response.ItemRs;
import com.aglaya.model.Item;
import com.aglaya.service.ItemService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

@RestController
@RequiredArgsConstructor
@RequestMapping("/items")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ItemController {
    ItemService itemService;

    @PostMapping("/")
    public ResponseEntity<ItemRs> createItem(@RequestBody ItemRq itemRq) {
        return ResponseEntity.ok(itemService.createItem(itemRq));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemRs> getItem(@PathVariable Integer id) {
        return ResponseEntity.ok(itemService.getItem(id));
    }
}
