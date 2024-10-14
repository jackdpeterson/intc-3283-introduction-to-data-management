package edu.northwestu.intc3283.datasourcestarter.controller;

import edu.northwestu.intc3283.datasourcestarter.entity.Donor;
import edu.northwestu.intc3283.datasourcestarter.repository.DonorsRepository;
import edu.northwestu.intc3283.datasourcestarter.util.DataGeneratorService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.List;

@Controller
@RequestMapping("/")
public class DonorsController {

    private final DonorsRepository donorRepository;
    private final DataGeneratorService dataGeneratorService;

    public DonorsController(DonorsRepository donorRepository, DataGeneratorService dataGeneratorService) {
        this.donorRepository = donorRepository;
        this.dataGeneratorService = dataGeneratorService;
    }

    @GetMapping("/search")
    @ResponseBody
    public ResponseEntity<List<Donor>> searchDonors(@RequestParam("searchTerm") String searchTerm) {
        List<Donor> donors = donorRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);

        if (donors.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(donors);
        }
    }


    @GetMapping("")
    public String getAll(@RequestParam(value = "searchTerm", required = false) String searchTerm, Model model) {
        List<Donor> donors;

        // If searchTerm is provided and non-empty, search by name
        if (searchTerm != null && !searchTerm.trim().isEmpty()) {
            donors = donorRepository.findByFirstNameContainingOrLastNameContaining(searchTerm, searchTerm);
        } else {
            // Otherwise, retrieve the 10 most recent donors
            donors = donorRepository.findTop10ByOrderByCreatedAtDesc();
        }

        model.addAttribute("donors", donors);
        model.addAttribute("searchTerm", searchTerm);
        return "donors/index";
    }

    @GetMapping("/report")
    public String topDonorsReport(Model model) {
        model.addAttribute("topDonors", donorRepository.findTopDonations());
        return "donors/report";
    }

    @GetMapping("/donors/random")
    public String generateRandomDonors(@RequestParam("numDonors") int numDonors, @RequestParam("maxDonationsPerDonor") int maxDonationsPerDonor) {
        this.dataGeneratorService.generateRandomDonorsAndDonations(numDonors, maxDonationsPerDonor);
        return "redirect:/";
    }
}
