package by.serchenya.storage.controllers;

import by.serchenya.storage.entity.Pallet;
import by.serchenya.storage.entity.Storage;
import by.serchenya.storage.repo.PalletRepo;
import by.serchenya.storage.repo.StorageRepo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class PalletController {
    private final PalletRepo palletRepo;
    private final StorageRepo storageRepo;

    public PalletController(PalletRepo palletRepo, StorageRepo storageRepo) {
        this.palletRepo = palletRepo;
        this.storageRepo = storageRepo;
    }

    @PostMapping("/pallet/add")
    public String placeOrder(@RequestParam Long storageId,
                             @RequestParam String customer,
                             @RequestParam double weight,
                             @RequestParam String product,
                             Model model) {

        Storage storage = storageRepo.findById(storageId).get();
        double fill = (((storage.getPallets().stream().mapToDouble(Pallet::getWeight).sum()) / 10) / storage.getCapacity()) * 100;
        double cap = fill + weight / 1000;
        if (cap > 100) {
            return "redirect:/notSpace";
        }
        Pallet pallet = new Pallet(customer, weight, product, storage);
        palletRepo.save(pallet);
        storage.addPallet(pallet);
        return "redirect:/storage/" + storageId;
    }

    @PostMapping("/pallet/{id}/remove")
    public String info(@PathVariable(value = "id") long id,
                       @RequestParam Long storageId,
                       Model model) {
        Storage storage = storageRepo.findById(storageId).get();
        Pallet pallet = palletRepo.findById(id).get();
        palletRepo.delete(pallet);
        return "redirect:/storage/" + storageId;
    }
}
