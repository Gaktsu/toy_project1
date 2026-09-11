package com.toyproject.shoppingManage.Item;

import com.toyproject.shoppingManage.ErrorCode;
import com.toyproject.shoppingManage.Order.Exception.NotEnoughStockException;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Item")
@Getter
public class Item {

    // ----------------------- FIELD --------------------------//

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Integer price;

    private Integer stock;

    // ----------------------- CONSTRUCTOR --------------------------//

    protected Item() {}

    public Item(String name, Integer price, Integer stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    // ----------------------- FACTORY METHOD --------------------------//

    public static Item from(ItemRequestDTO request){
        return new Item(
                request.name(),
                request.price(),
                request.stock()
        );
    }

    // ----------------------- METHOD --------------------------//

    public void decreaseStock(int quantity){
        if(stock < quantity)
            throw new NotEnoughStockException(ErrorCode.NOT_ENOUGH_STOCK);

        stock -= quantity;
    }
    public void increaseStock(int value) { stock += value; }
}
