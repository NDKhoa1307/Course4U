package com.mgmtp.cfu.mapper.factory.impl

import com.mgmtp.cfu.mapper.DTOMapper
import com.mgmtp.cfu.mapper.EntityMapper
import com.mgmtp.cfu.mapper.factory.MapperFactory
import spock.lang.Specification
import spock.lang.Subject

class MapperFactoryImplSpec extends Specification {
    @Subject
    MapperFactory<Object> mapperFactory = new MapperFactoryImpl()

    def "Should return the correct DTOMapper"() {
        given:
        def dtoMapper = Mock(DTOMapper)
        mapperFactory._dtoMappers = [(Object.class): dtoMapper] as Map<Class<?>, DTOMapper>
        when:
        def result = mapperFactory.getDTOMapper(Object.class)

        then:
        result.isPresent()
        result.get() == dtoMapper
    }

    def "Should return empty Optional when the DTOMapper is not found"() {
        given:
        mapperFactory._dtoMappers = [:] as Map<Class<?>, DTOMapper>

        when:
        def result = mapperFactory.getDTOMapper(Object.class)

        then:
        result.isEmpty()
    }

    def "Should return the correct EntityMapper"() {
        given:
        def entityMapper = Mock(EntityMapper)
        mapperFactory._entityMappers = [(Object.class): entityMapper] as Map<Class<?>, EntityMapper>
        when:
        def result = mapperFactory.getEntityMapper(Object.class)

        then:
        result.isPresent()
        result.get() == entityMapper
    }

    def "Should return empty Optional when the EntityMapper is not found"() {
        given:
        mapperFactory._entityMappers = [:] as Map<Class<?>, EntityMapper>

        when:
        def result = mapperFactory.getEntityMapper(Object.class)

        then:
        result.isEmpty()
    }
}
