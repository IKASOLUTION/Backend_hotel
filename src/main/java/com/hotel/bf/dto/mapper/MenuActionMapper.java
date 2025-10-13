package com.hotel.bf.dto.mapper;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.hotel.bf.domain.MenuAction;
import com.hotel.bf.domain.ModuleParam;
import com.hotel.bf.dto.MenuActionDto;

/**
 * Mapper for the entity  MenuAction and its DTO.
 */
@Component
public class MenuActionMapper {
    public MenuActionDto toDto(MenuAction menuAction) {
    return MenuActionDto.builder()
            .id(menuAction.getId())
            .menuActionLibelle(menuAction.getMenuActionLibelle())
            .menuActionCode(menuAction.getMenuActionCode())
            .isDeleted(menuAction.getDeleted())
            .moduleParamId(menuAction.getModuleParam().getId())
            .moduleParamLibelle(menuAction.getModuleParam().getModuleParamLibelle())
            .build();
}

public MenuAction toEntity(MenuActionDto menuActionDto) {
    return MenuAction.builder()
            .id(menuActionDto.getId())
            .menuActionLibelle(menuActionDto.getMenuActionLibelle())
            .menuActionCode(menuActionDto.getMenuActionCode())
            .deleted(menuActionDto.getIsDeleted())
            .moduleParam(ModuleParam.builder()
                        .id(menuActionDto.getModuleParamId())
                        .build())
            .build();
}

public Set<MenuActionDto> setToDtos(Set<MenuAction> menuActions) {
    return  menuActions.stream().map(this::toDto).collect(Collectors.toSet());
}
public Set<MenuAction> setToEntities(Set<MenuActionDto> menuActionDtos) {
    return  menuActionDtos.stream().map(this::toEntity).collect(Collectors.toSet());
}
public List<MenuActionDto> toDtos(List<MenuAction> menuActions) {
    return menuActions.stream().map(this::toDto).toList();
}

public List<MenuAction> toEntities(List<MenuActionDto> menuActionDtos) {
    return menuActionDtos.stream().map(this::toEntity).toList();
}

}
