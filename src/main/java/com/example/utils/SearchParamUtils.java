package com.example.utils;

import com.example.constant.JOIN;
import com.example.constant.FILTER_OPERATION;
import com.example.dto.FilterParamDTO;
import com.example.dto.FilterParamGroupDTO;
import com.example.exception.SearchParamsParseException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;

public class SearchParamUtils {
    public static <T> BooleanBuilder generateSearchPredicate(FilterParamGroupDTO params, T entity, Class<T> entityClass) {
        BooleanBuilder predicate = new BooleanBuilder();
        if (params != null && params.isFilterParamsExists()) {
            PathBuilder<T> entityPath = new PathBuilder<>(entityClass, entity.toString());

            final JOIN join = JOIN.fromValue(params.getJoinBetween());

            if (params.getFilterParams() != null) {
                for (FilterParamDTO filterParam : params.getFilterParams()) {
                    BooleanExpression expression = generateExpression(filterParam, entityPath);

                    if (expression != null) {
                        joinExpression(join, predicate, expression);
                    } else {
                        throw new SearchParamsParseException(filterParam.getKey(), filterParam.getValue().toString());
                    }
                }
            }

            if (params.getFilterParamGroups() != null) {
                for (FilterParamGroupDTO filterGroup : params.getFilterParamGroups()) {
                    BooleanBuilder groupPredicate = generateSearchPredicate(filterGroup, entity, entityClass);
                    joinPredicate(join, predicate, groupPredicate);
                }
            }
        }

        return predicate;
    }

    private static BooleanExpression generateExpression(FilterParamDTO filterParam, PathBuilder<?> entityPath) {
        if (filterParam.getKey() == null) {
            throw new SearchParamsParseException("key", "null");
        }

        if (filterParam.getValue() == null) {
            throw new SearchParamsParseException("value", "null");
        }

        FILTER_OPERATION operation = FILTER_OPERATION.fromValue(filterParam.getOperation());
        if (operation == FILTER_OPERATION.EQUALS) {
            return entityPath.get(filterParam.getKey()).eq(filterParam.getValue());
        } else if (operation == FILTER_OPERATION.CONTAINS) {
            return entityPath.getString(filterParam.getKey()).containsIgnoreCase((String) filterParam.getValue());
        } else if (operation == FILTER_OPERATION.GREATER_THAN) {
            if (filterParam.getValue() instanceof Integer value) {
                return entityPath.getNumber(filterParam.getKey(), Integer.class).gt(value);
            } else if (filterParam.getValue() instanceof Double value) {
                return entityPath.getNumber(filterParam.getKey(), Double.class).gt(value);
            } else {
                return null;
            }
        } else if (operation == FILTER_OPERATION.LESS_THAN) {
            if (filterParam.getValue() instanceof Integer value) {
                return entityPath.getNumber(filterParam.getKey(), Integer.class).lt(value);
            } else if (filterParam.getValue() instanceof Double value) {
                return entityPath.getNumber(filterParam.getKey(), Double.class).lt(value);
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    private static void joinExpression(JOIN join, BooleanBuilder predicate, BooleanExpression expression) {
        if (join == JOIN.AND) {
            predicate.and(expression);
        } else if (join == JOIN.OR) {
            predicate.or(expression);
        } else if (join == JOIN.OR_NOT) {
            predicate.or(expression.not());
        } else if (join == JOIN.AND_NOT) {
            predicate.and(expression.not());
        }
    }

    private static void joinPredicate(JOIN join, BooleanBuilder predicate, BooleanBuilder joinedPredicate) {
        if (join == JOIN.AND) {
            predicate.and(joinedPredicate);
        } else if (join == JOIN.OR) {
            predicate.or(joinedPredicate);
        } else if (join == JOIN.OR_NOT) {
            predicate.or(joinedPredicate.not());
        } else if (join == JOIN.AND_NOT) {
            predicate.and(joinedPredicate.not());
        }
    }
}
