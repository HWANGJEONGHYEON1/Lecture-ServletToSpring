package hello.itemservice.domain.item;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ItemRepositoryTest {

    ItemRepository repository = new ItemRepository();

    @AfterEach
    void afterEach() {
        repository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);
        //when
        final Item savedItem = repository.save(item);
        //then
        final Item findItem = repository.findById(item.getId());
        Assertions.assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {

        //given
        Item item1 = new Item("itemA", 10000, 10);
        Item item2 = new Item("itemA", 10000, 10);
        repository.save(item1);
        repository.save(item2);

        //when
        final List<Item> all = repository.findAll();

        //then
        Assertions.assertThat(all.size()).isEqualTo(2);
        Assertions.assertThat(all).contains(item1);

    }

    @Test
    void update() {
        Item item1 = new Item("itemA", 10000, 10);

        final Item save = repository.save(item1);
        final Long id = save.getId();

        final Item updateItem = new Item("item2", 20000, 30);
        repository.update(id, updateItem);

        final Item findItem = repository.findById(id);
        Assertions.assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());


    }

}