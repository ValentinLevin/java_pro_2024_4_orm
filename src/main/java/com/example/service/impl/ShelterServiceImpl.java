package com.example.service.impl;

import com.example.dto.*;
import com.example.exception.ShelterNotFoundException;
import com.example.mapper.ShelterMapper;
import com.example.model.QShelterModel;
import com.example.model.ShelterModel;
import com.example.repository.ShelterRepository;
import com.example.service.ShelterService;
import com.example.utils.OrderParamUtils;
import com.example.utils.SearchParamUtils;
import com.querydsl.core.BooleanBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class ShelterServiceImpl implements ShelterService {
    private final ShelterRepository shelterRepository;

    public ShelterServiceImpl(ShelterRepository shelterRepository) {
        this.shelterRepository = shelterRepository;
    }

    @Override
    public ShelterListResponseDTO findByParams(SearchConfigDTO searchParams) {
        QShelterModel qShelterModel = QShelterModel.shelterModel;

        BooleanBuilder filterPredicate =
                SearchParamUtils.generateSearchPredicate(
                        searchParams.getFilterParams(),
                        qShelterModel,
                        QShelterModel.class
                );

        Pageable pageable = OrderParamUtils.generateOrderSpecifiers(
                searchParams.getSortParams(),
                searchParams.getPageNumber() - 1,
                searchParams.getPageSize()
        );

        List<ShelterDTO> items = new ArrayList<>();

        shelterRepository.findAll(filterPredicate, pageable)
                .forEach(shelterModel -> items.add(ShelterMapper.INSTANCE.toDTO(shelterModel)));

        return new ShelterListResponseDTO(items);
    }

    @Override
    @Transactional
    public SingleShelterResponseDTO createShelter(ShelterDTO shelterDTO) {
        ShelterModel shelterModel = ShelterMapper.INSTANCE.toEntity(shelterDTO);
        shelterModel = shelterRepository.save(shelterModel);
        return new SingleShelterResponseDTO(ShelterMapper.INSTANCE.toDTO(shelterModel));
    }

    @Override
    public ShelterListResponseDTO findAllShelters() {
        List<ShelterDTO> items = shelterRepository.findAll().stream()
                .map(ShelterMapper.INSTANCE::toDTO)
                .toList();
        return new ShelterListResponseDTO(items);
    }

    @Override
    @Transactional
    public BaseResponseDTO deleteShelterById(String id) {
        if (!shelterRepository.existsById(id)) {
            throw new ShelterNotFoundException(id);
        }
        shelterRepository.deleteById(id);
        return new BaseResponseDTO();
    }
}
