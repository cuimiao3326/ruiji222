package org.example.ruiji.dto;


import lombok.Data;
import org.example.ruiji.entity.Dish;
import org.example.ruiji.entity.DishFlavor;

import java.util.ArrayList;
import java.util.List;

@Data
public class DishDto extends Dish {

    private List<DishFlavor> flavors = new ArrayList<>();

    private String categoryName;

    private Integer copies;
}
