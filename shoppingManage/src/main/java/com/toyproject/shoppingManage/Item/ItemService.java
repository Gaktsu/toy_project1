package com.toyproject.shoppingManage.Item;

import com.toyproject.shoppingManage.ErrorCode;
import com.toyproject.shoppingManage.Item.Exception.ItemNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    @Transactional
    public ItemResponseDTO registerItem(ItemRequestDTO request){
        Item item = itemRepository.save(Item.from(request));

        return ItemResponseDTO.from(item);
    }

    public ItemResponseDTO getItem(Long id){
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ErrorCode.ITEM_NOT_FOUND));

        return ItemResponseDTO.from(item);
    }
}
