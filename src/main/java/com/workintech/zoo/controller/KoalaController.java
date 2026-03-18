package com.workintech.zoo.controller;

import com.workintech.zoo.entity.Koala;
import com.workintech.zoo.exceptions.ZooException;
import jakarta.annotation.PostConstruct;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/koalas") // Sadece /koalas yaptık
public class KoalaController {
    private Map<Integer, Koala> koalas;

    @PostConstruct
    public void init() { koalas = new HashMap<>(); }

    @GetMapping
    public List<Koala> findAll() { return koalas.values().stream().toList(); }

    @GetMapping("/{id}")
    public Koala find(@PathVariable int id) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala with id " + id + " not found!", HttpStatus.NOT_FOUND);
        }
        return koalas.get(id);
    }

    @PostMapping
    public Koala save(@RequestBody Koala koala) {
        koalas.put(koala.getId(), koala);
        return koalas.get(koala.getId());
    }

    @PutMapping("/{id}")
    public Koala update(@PathVariable int id, @RequestBody Koala koala) {
        koala.setId(id);
        koalas.put(id, koala);
        return koalas.get(id);
    }

    @DeleteMapping("/{id}")
    public Koala delete(@PathVariable int id) {
        if (!koalas.containsKey(id)) {
            throw new ZooException("Koala not found!", HttpStatus.NOT_FOUND);
        }
        return koalas.remove(id);
    }
}
