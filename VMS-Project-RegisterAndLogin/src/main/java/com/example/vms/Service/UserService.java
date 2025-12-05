package com.example.vms.Service;

import com.example.vms.Dto.CveResponse;

import java.util.List;

public interface UserService {
    List<CveResponse> getAllCves();
    CveResponse getCveById(Integer id);
    // other methods...
}
