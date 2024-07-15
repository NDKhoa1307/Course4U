package com.mgmtp.cfu.service.impl


import com.mgmtp.cfu.entity.User
import com.mgmtp.cfu.enums.Gender
import com.mgmtp.cfu.enums.Role
import com.mgmtp.cfu.repository.UserRepository
import com.mgmtp.cfu.service.UploadService
import org.springframework.web.multipart.MultipartFile
import spock.lang.Specification
import spock.lang.Subject
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder

import java.time.LocalDate

class UserServiceImplSpec extends Specification {

    def userRepository = Mock(UserRepository)
    def uploadService = Mock(UploadService)

    @Subject
    def userService = new UserServiceImpl(userRepository, uploadService)

    def setup() {
        def securityContext = Mock(SecurityContext)
        def authentication = Mock(Authentication)
        SecurityContextHolder.setContext(securityContext)
        securityContext.getAuthentication() >> authentication
        def user = User.builder().id(1)
                .fullName("Jane Doe")
                .username("janedoe")
                .email("janedoe@example.com")
                .role(Role.USER)
                .avatarUrl("/aksh/sdh")
                .gender(Gender.FEMALE)
                .telephone("008837623")
                .build()
        authentication.getCredentials() >> user
    }

    def cleanup() {
        SecurityContextHolder.clearContext()
    }

    def 'getMyProfile'() {
        given:
        when:
        def userDto = userService.getMyProfile()
        then:
        userDto.getId() == 1
        userDto.fullName == "Jane Doe"
        userDto.username == "janedoe"
        userDto.email == "janedoe@example.com"
        userDto.role == Role.USER
        userDto.avatarUrl == "/aksh/sdh"
        userDto.gender == Gender.FEMALE
        userDto.telephone == "008837623"

    }

    def "editUserProfile update user info and return UserDto"() {
        given:
        def imageFile = Mock(MultipartFile)
        def uploadPath = "uploads/img"
        def userDto = new UserDto(1, "janedoe", "NTN", "janedoe@example.com", "123",
                                  "/img/", LocalDate.now(), Role.USER, Gender.MALE, imageFile)

        when:
        uploadService.uploadThumbnail(imageFile, uploadPath)
        def result = userService.editUserProfile(userDto)

        then:
        result.getId() == userDto.getId()
    }

}
