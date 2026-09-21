package com.toyproject.shoppingManage.Item;

import com.toyproject.shoppingManage.EmptyBodyRequestException;
import com.toyproject.shoppingManage.ErrorCode;
import com.toyproject.shoppingManage.Item.Exception.ItemNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.function.Predicate;

@Service
public class ItemService {
    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository){
        this.itemRepository = itemRepository;
    }

    // ----------------------- RESTAPI : GET --------------------------//

    public ItemResponseDTO requestGetItem(Long id){
        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ErrorCode.ITEM_NOT_FOUND));

        return ItemResponseDTO.from(item);
    }

    // ----------------------- RESTAPI : POST --------------------------//

    public ItemResponseDTO requestRegisterItem(ItemRequestDTO request){
        Item item = itemRepository.save(Item.from(request));

        return ItemResponseDTO.from(item);
    }

    // ----------------------- RESTAPI : PATCH --------------------------//

    @Transactional
    public ItemResponseDTO requestUpdateItem_PATCH(Long id, ItemUpdateRequestDTO request) {
        ObjectMapper objectMapper = new ObjectMapper();

        if(objectMapper.valueToTree(request).isEmpty())
            throw new EmptyBodyRequestException(ErrorCode.EMPTY_REQUEST_BODY);

        Item item = itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ErrorCode.ITEM_NOT_FOUND));

        if(request.price() != null)
            item.updatePrice(request.price());

        if(request.stock() != null)
            item.updateStock(request.stock());

        return ItemResponseDTO.from(item);
    }

    // ----------------------- RESTAPI : PUT --------------------------//

    // ----------------------- METHOD --------------------------//

    /*
    public Item getItem(Long id){
        return itemRepository.findById(id).orElseThrow(() -> new ItemNotFoundException(ErrorCode.ITEM_NOT_FOUND));
    }

    public void updateItem(Item item){
        itemRepository.save(item);
    }
    */
}
