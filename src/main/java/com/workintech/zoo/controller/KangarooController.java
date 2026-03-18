package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Kangaroo;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/kangaroos") // Sadece /kangaroos yaptık, /workintech kısmını sildik!
public class KangarooController {

    private Map<Integer, Kangaroo> kangaroos;

    @PostConstruct
    public void init() {
        kangaroos = new HashMap<>();
    }

    @GetMapping
    public List<Kangaroo> findAll() {
        return kangaroos.values().stream().toList();
    }

    @GetMapping("/{id}")
    public Kangaroo find(@PathVariable int id) {
        // ID kontrolü ekleyelim (Validation)
        if (id <= 0) {
            throw new ZooException("Id must be greater than zero: " + id, HttpStatus.BAD_REQUEST);
        }
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        return kangaroos.get(id);
    }

    @PostMapping
    public Kangaroo save(@RequestBody Kangaroo kangaroo) {
        // Kaydetmeden önce validasyon (GenericException testi için önemli)
        if (kangaroo.getId() <= 0 || kangaroo.getName() == null || kangaroo.getName().isEmpty()) {
            throw new ZooException("Invalid kangaroo data!", HttpStatus.BAD_REQUEST);
        }
        kangaroos.put(kangaroo.getId(), kangaroo);
        return kangaroos.get(kangaroo.getId());
    }

    @PutMapping("/{id}")
    public Kangaroo update(@PathVariable int id, @RequestBody Kangaroo kangaroo) {
        if (id <= 0) {
            throw new ZooException("Id must be greater than zero: " + id, HttpStatus.BAD_REQUEST);
        }
        kangaroo.setId(id);
        kangaroos.put(id, kangaroo);
        return kangaroos.get(id);
    }

    @DeleteMapping("/{id}")
    public Kangaroo delete(@PathVariable int id) {
        if (!kangaroos.containsKey(id)) {
            throw new ZooException("Kangaroo not found to delete: " + id, HttpStatus.NOT_FOUND);
        }
        return kangaroos.remove(id);
    }
}