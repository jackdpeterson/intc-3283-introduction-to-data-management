package edu.northwestu.intc3283.datasourcestarter.controller;

import edu.northwestu.intc3283.datasourcestarter.dto.EntryForm;
import edu.northwestu.intc3283.datasourcestarter.entity.Entry;
import edu.northwestu.intc3283.datasourcestarter.repository.EntryRepository;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class IndexController {

    private final EntryRepository entryRepository;

    public IndexController(EntryRepository entryRepository) {
        this.entryRepository = entryRepository;
    }

    @GetMapping("/")
    public String indexAction(Model model) {
        model.addAttribute("entries", this.entryRepository.findAll());
        return "index";
    }


    @GetMapping("/entries/new")
    public String indexAction(final @ModelAttribute("entry") Entry input) {
        return "form";
    }

    @GetMapping("/entries/{id}")
    public String viewEntry(final @PathVariable("id") Long id, final Model model) {
        final Entry entry = this.entryRepository.findById(id).orElseThrow();
        model.addAttribute("entry", entry);
        return "entry";
    }

    @PostMapping("/entries")
    public String submitEntry(final @Valid @ModelAttribute("entry") EntryForm input,
                              final BindingResult bindingResult,
                              final HttpServletResponse response
    ) {
        if (bindingResult.hasErrors()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return "form";
        }
        Entry entity = new Entry(input);

        this.entryRepository.save(entity);
        return "redirect:/entries/" + entity.getId();
    }
}
