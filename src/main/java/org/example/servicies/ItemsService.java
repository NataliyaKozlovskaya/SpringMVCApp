package org.example.servicies;

import org.example.models.Item;
import org.example.models.Person;
import org.example.repositories.ItemsRepository;
import org.example.repositories.PeopleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class ItemsService {
    private final ItemsRepository itemsRepository;

    @Autowired
    public ItemsService(ItemsRepository itemsRepository) {
        this.itemsRepository = itemsRepository;
    }

    public List<Item> findByItemName(String item){
        return itemsRepository.findByItemName(item);
    }

    public List <Item> findByOwner(Person owner){
        return itemsRepository.findByOwner(owner);
    }



}
