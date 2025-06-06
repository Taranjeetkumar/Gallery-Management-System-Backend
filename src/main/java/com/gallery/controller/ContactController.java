package com.gallery.controller;

import com.gallery.dto.ContactRequest;
import com.gallery.dto.ContactResponse;
import com.gallery.service.ContactService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contacts")
@CrossOrigin(origins = "*")
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public List<ContactResponse> getAllContacts() {
        return contactService.getAllContacts();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public ContactResponse getContact(@PathVariable Long id) {
        return contactService.getContactById(id);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public ContactResponse createContact(@Valid @RequestBody ContactRequest request) {
        return contactService.createContact(request);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public ContactResponse updateContact(@PathVariable Long id, @Valid @RequestBody ContactRequest request) {
        return contactService.updateContact(id, request);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','GALLERY_MANAGER')")
    public void deleteContact(@PathVariable Long id) {
        contactService.deleteContact(id);
    }
}
