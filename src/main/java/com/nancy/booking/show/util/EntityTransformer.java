package com.nancy.booking.show.util;

import org.modelmapper.ModelMapper;

/**
 *  Provides Type inference support to detect types dynamically and map between
 *  entity and DTO.
 */
public class EntityTransformer {
    private static final ModelMapper modelMapper = new ModelMapper();

    @SuppressWarnings({ "unchecked", "null" })
    public static <D, E> E toEntity(D dto, E entity) {
        E result = null;
        try {
            if (entity == null) {
                result = (E) entity.getClass().newInstance();
            }
            if (dto != null) {
                result = (E) modelMapper.map(dto, entity.getClass());
                if (result == null) {
                    return (E) entity.getClass().newInstance();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @SuppressWarnings({ "unchecked", "null" })
    public static <D, E> D toDto(E entity, D dto) {
        D result = null;
        try {
            if (dto == null) {
                result = (D) dto.getClass().newInstance();
            }
            if (entity != null) {
                result = (D) modelMapper.map(entity, dto.getClass());
                if (result == null) {
                    return (D) dto.getClass().newInstance();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
