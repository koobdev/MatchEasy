package koo.project.matcheasy.mapper;

public interface EntityDtoMapper<D, E> {

    E toEntity(final D dto);
    D toDto(final E entity);
}
