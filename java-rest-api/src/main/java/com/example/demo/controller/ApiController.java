package com.example.demo.controller;

import com.example.demo.InvalidItemDataException;
import com.example.demo.model.Item;
import com.example.demo.service.ItemService; // Importar
import org.springframework.beans.factory.annotation.Autowired; // Importar
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
public class ApiController {

    @Autowired
    private ItemService itemService; // Injetar o serviço

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<List<Item>> getAllItems() {
        List<Item> allItems = itemService.getAllItems();
        return ResponseEntity.ok(allItems);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        Item createdItem = itemService.createItem(item);
        return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemService.getItemById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @Valid @RequestBody Item item) {
        return itemService.updateItem(id, item)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (itemService.deleteItem(id)) {
            return ResponseEntity.noContent().build(); // Retorna 204 No Content, a melhor prática para DELETE
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // ENDPOINTS ADICIONAIS COM REGRAS DE NEGÓCIO - QA - VA 1 
    // NOVO ENDPOINT 1: Gerenciar Estoque
// Rota final: PATCH /api/items/{id}/stock
    @PatchMapping("/{id}/stock")
    public ResponseEntity<Item> updateStock(@PathVariable Long id, @RequestBody Integer quantity) {
    // REGRA DE NEGÓCIO: Se a quantidade for negativa, lança erro
    if (quantity < 0) {
        throw new InvalidItemDataException("Estoque não pode ser negativo");
    }
    
    return itemService.getItemById(id)
            .map(item -> {
                item.setQuantity(quantity);
                return ResponseEntity.ok(item);
            })
            .orElse(ResponseEntity.notFound().build());
}

// NOVO ENDPOINT 2: Validar Nome do Item
// Rota final: POST /api/items/validate
    @PostMapping("/validate")
    public ResponseEntity<String> validateItemName(@RequestBody String name) {
    // REGRA DE NEGÓCIO: Nome não pode ser nulo ou vazio
    if (name == null || name.trim().isEmpty()) {
        throw new InvalidItemDataException("Nome inválido!");
    }
    return ResponseEntity.ok("Nome válido");
}
}

