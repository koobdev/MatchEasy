package koo.project.matcheasy.mapper;

import koo.project.matcheasy.domain.team.Dayly;
import koo.project.matcheasy.domain.team.Task;
import koo.project.matcheasy.dto.DaylyDto;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DaylyMapper {

    DaylyMapper DAYLY_MAPPER = Mappers.getMapper(DaylyMapper.class);

    Dayly toEntity(DaylyDto daylyDto);
    DaylyDto toDto(Dayly dayly);

//    @AfterMapping
//    default void afterMapping(@MappingTarget Dayly dayly, DaylyDto daylyDto){
//        Task task = dayly.getTask();
//        task.setGoal(daylyDto.getGoal());
//        task.setStatus(daylyDto.getStatus());
//    }
}
