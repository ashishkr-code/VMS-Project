package com.example.vms.Configuration;

import com.example.vms.Enum.Role;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StringToRoleConverter implements Converter<String, Role> {
    @Override
    public Role convert(String source) {
        if (source == null) return null;
        // Automatically add prefix if missing
        if (!source.startsWith("ROLE_")) {
            source = "ROLE_" + source.toUpperCase();
        }
        return Role.valueOf(source);
    }
}
