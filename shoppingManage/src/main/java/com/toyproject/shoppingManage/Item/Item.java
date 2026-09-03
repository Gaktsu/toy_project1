package com.toyproject.shoppingManage.Item;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Entity
@Table(name = "Item")
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private Integer price;

    private Integer stock;

    protected Item() {}

    public Item(String name, Integer price, Integer stock){
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public static Item from(ItemRequestDTO request){
        return new Item(
                request.name(),
                request.price(),
                request.stock()
        );
    }
}
