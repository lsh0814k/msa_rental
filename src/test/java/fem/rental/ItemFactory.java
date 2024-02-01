package fem.rental;

import fem.rental.domain.model.vo.Item;

public class ItemFactory {
    public static Item create() {
        return Item.createItem(1L, "바다와 노인");
    }
}
