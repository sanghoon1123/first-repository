package sanghoonbook.sanghoonshop.domain.service;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import sanghoonbook.sanghoonshop.domain.Item;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired ItemService itemService;

    @Test
    void save(){
        Item item = new Item("상훈", 10000, 5);
        Item saveItem = itemService.saveItem(item);

        assertThat(saveItem).isEqualTo(item);
    }

    @Test
    void update(){
        Item item1 = new Item("상훈", 10000, 5);
        Item saveItem = itemService.saveItem(item1);

        Item item2 = new Item(item1.getId(),"준혁", 20000, 10);
        itemService.upDateItem(item2);

        assertThat(saveItem).isEqualTo(item2);

        log.info("saveItem = {}",saveItem);

    }

    @Test
    void updateFail(){
        Item item1 = new Item("상훈", 10000, 5);
        Item saveItem = itemService.saveItem(item1);

        Item item2 = new Item(999L, "준혁", 20000, 10);


        assertThrows(IllegalStateException.class, () -> itemService.upDateItem(item2));

    }

    @Test
    void findAll(){
        Item item1 = new Item("상훈", 10000, 5);
        Item item2 = new Item("준혁", 20000, 10);

        itemService.saveItem(item1);
        itemService.saveItem(item2);

        List<Item> all = itemService.findAll();

        log.info("all = {}", all);
    }

    @Test
    void findOne(){
        Item item1 = new Item("상훈", 10000, 5);
        itemService.saveItem(item1);

        Item findItem = itemService.findOne(item1.getId());

        assertThat(findItem).isEqualTo(item1);
    }

}