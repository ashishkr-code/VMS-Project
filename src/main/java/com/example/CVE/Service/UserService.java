package com.example.CVE.Service;

import com.example.CVE.Dto.CveResponse;
import java.util.List;

public interface UserService {
    List<CveResponse> getAllCves();
}