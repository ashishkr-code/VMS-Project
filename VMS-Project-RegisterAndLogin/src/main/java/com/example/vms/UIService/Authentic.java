package com.example.vms.UIService;

import com.example.vms.Model.Auditor;
import com.example.vms.Repository.AuditorRepository;
import org.springframework.stereotype.Service;

@Service
public class Authentic {

    private final AuditorRepository auditorRepository;

    public Authentic(AuditorRepository auditorRepository) {
        this.auditorRepository = auditorRepository;
    }

    public Auditor authenticate(String email, String password) {
        Auditor user = auditorRepository.findByEmail(email).orElse(null);

        if (user == null || !user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }
}
