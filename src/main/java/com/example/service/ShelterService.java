package com.example.service;

import com.example.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ShelterService {
    ShelterListResponseDTO findByParams(SearchConfigDTO searchParams);
    SingleShelterResponseDTO createShelter(ShelterDTO shelterDTO);
    ShelterListResponseDTO findAllShelters();
    BaseResponseDTO deleteShelterById(String id);
}
