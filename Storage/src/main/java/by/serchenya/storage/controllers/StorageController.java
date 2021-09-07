package by.serchenya.storage.controllers;

import by.serchenya.storage.entity.Pallet;
import by.serchenya.storage.entity.Storage;
import by.serchenya.storage.repo.StorageRepo;
import lombok.val;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class StorageController {
    private final StorageRepo storageRepo;

    public StorageController(StorageRepo storageRepo) {
        this.storageRepo = storageRepo;
    }

    @GetMapping("/")
    public String index(Model model) {
        Iterable<Storage> storages = storageRepo.findAll();
        model.addAttribute("title", "MainPage");
        model.addAttribute("storages", storages);
        return "index";
    }

    @GetMapping("/storage/add")
    public String addStorage(Model model) {
        model.addAttribute("title", "Add new Storage!");
        return "addStorage";
    }

    @GetMapping("/storage/{id}")
    public String info(@PathVariable(value = "id") long id, Model model) {
        Storage storage = storageRepo.findById(id).get();
        List<Pallet> pallets = storage.getPallets();
        model.addAttribute("title", "Storage - " + storage.getName());
        double weight = (((storage.getPallets().stream().mapToDouble(Pallet::getWeight).sum()) / 10) / storage.getCapacity()) * 100;
        model.addAttribute("weight", "width: " + weight + "%");
        model.addAttribute("storage", storage);
        model.addAttribute("pallets", pallets);
        return "aboutStorage";
    }

    @GetMapping("/notSpace")
    public String notFreeSpace(Model model) {
        model.addAttribute("title", "Have not free space on storage");
        return "notSpace";
    }

    @GetMapping("/storage/{id}/add")
    public String addPallet(@PathVariable(value = "id") long id, Model model) {
        Storage storage = storageRepo.findById(id).get();
        model.addAttribute("title", "Add new product to " + storage.getName());
        model.addAttribute("storageId", id);
        return "addPallet";
    }

    @PostMapping("/storage/add")
    public String placeOrder(@RequestParam String name,
                             @RequestParam int capacity,
                             @RequestParam String description,
                             @RequestParam String img,
                             Model model) {
        Storage storage = new Storage(name, capacity, description, img);
        storageRepo.save(storage);
        return "redirect:/";
    }
}
