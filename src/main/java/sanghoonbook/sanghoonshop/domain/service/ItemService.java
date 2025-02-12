package sanghoonbook.sanghoonshop.domain.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sanghoonbook.sanghoonshop.domain.Item;
import sanghoonbook.sanghoonshop.repository.ItemRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ItemService {
    private final ItemRepository itemRepository;

    public Item saveItem(Item item){
        return itemRepository.save(item);
    }

    public void upDateItem(Item item){
        Item findItem = itemRepository.findById(item.getId())
                .orElseThrow(() -> new IllegalStateException("아이템을 찾을 수 없습니다."));

        findItem.update(item.getName(), item.getPrice(), item.getStockQuantity());

        itemRepository.save(findItem);
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public Item findOne(Long id){
        return itemRepository.findById(id).orElse(null);
    }
}
