package com.javatechie.service;

import com.javatechie.entity.Item;
import com.javatechie.repository.ItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ItemService {

    private final ItemRepository itemRepository;

    public ItemService(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // Create or Update Item
    @Transactional
    public Item saveItem(Item item) {
        return itemRepository.save(item);
    }

    // Get all Items
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    // Get single Item by ID
    public Optional<Item> getItemById(long id) {
        return itemRepository.findById(id);
    }

    // Delete Item by ID
    @Transactional
    public void deleteItem(long id) {
        itemRepository.deleteById(id);
    }
}
