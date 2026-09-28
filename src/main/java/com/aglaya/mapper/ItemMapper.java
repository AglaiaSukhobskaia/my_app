package com.aglaya.mapper;

import com.aglaya.dto.response.ItemRs;
import com.aglaya.model.Item;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ItemMapper {
    ItemRs toItemRs(Item item);
}
