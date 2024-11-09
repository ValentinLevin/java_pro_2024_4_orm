package com.example.utils;

import com.example.constant.SORT_DIRECTION;
import com.example.dto.SortParamDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class OrderParamUtils {
    public static PageRequest generateOrderSpecifiers(
            List<SortParamDTO> sortParams,
            int pageNumber,
            int pageSize
    ) {
        if (sortParams != null) {
            List<Sort.Order> orders = new ArrayList<>();

            for (SortParamDTO sortParam : sortParams) {
                SORT_DIRECTION sortDirection = SORT_DIRECTION.fromValue(sortParam.getSortDirection());
                Sort.Order order = new Sort.Order(sortDirection == SORT_DIRECTION.ASC ? Sort.Direction.ASC : Sort.Direction.DESC, sortParam.getKey());
                orders.add(order);
            }

            return PageRequest.of(pageNumber, pageSize, Sort.by(orders));
        } else {
            return PageRequest.of(pageNumber, pageSize);
        }
    }
}
