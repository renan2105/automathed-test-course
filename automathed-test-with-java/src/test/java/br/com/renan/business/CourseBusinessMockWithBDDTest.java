package br.com.renan.business;

import br.com.renan.service.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class CourseBusinessMockWithBDDTest {
    CourseService mockCourseService;
    CourseBusiness courseBusiness;
    List<String> courses;

    @BeforeEach
    void setup(){
//        Given/Arrange
        mockCourseService = mock(CourseService.class);
        courseBusiness = new CourseBusiness(mockCourseService);
        courses = Arrays.asList(
                "REST API's RESTFul do 0 à Azure com ASP.NET Core 5 e Docker",
                "Agile Desmistificado com Scrum, XP, Kanban e Trello",
                "Spotify Engineering Culture Desmistificado",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Java e Docker",
                "Docker do Zero à Maestria - Contêinerização Desmistificada",
                "Docker para Amazon AWS Implante Apps Java e .NET com Travis CI",
                "Microsserviços do 0 com Spring Cloud, Spring Boot e Docker",
                "Arquitetura de Microsserviços do 0 com ASP.NET, .NET 6 e C#",
                "REST API's RESTFul do 0 à AWS com Spring Boot 3 Kotlin e Docker",
                "Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android",
                "Microsserviços do 0 com Spring Cloud, Kotlin e Docker");
    }

    @Test
    void testCoursesRelatedToSpring_When_UsingAMock(){
//        Given/Arrange
        given(mockCourseService.retrieveCourses("Leandro")).willReturn(courses);
//        When/Act
        var filteredCourses = courseBusiness.retrieveCoursesRelatedToSpring("Leandro");
//        Then/Assert
        assertThat(filteredCourses.size(), is(4));
    }

    @Test
    @DisplayName("Delete courses not related to spring Using mockito should call method")
    void testDeleteCoursesNotRelatedToSpring_UsingMockitoVerify_Should_CallMethod_deleteCourse(){
//        Given/Arrange
        given(mockCourseService.retrieveCourses("Leandro")).willReturn(courses);
//        When/Act
        courseBusiness.deleteCoursesNotRelatedToSpring("Leandro");
//        Then/Assert
//        verify(mockCourseService).deleteCourse("Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android");
//        verify(mockCourseService, times(1)).deleteCourse("Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android");
//        verify(mockCourseService, atLeast(1)).deleteCourse("Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android");
        verify(mockCourseService, atLeastOnce()).deleteCourse("Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android");
        verify(mockCourseService).deleteCourse("Spotify Engineering Culture Desmistificado");
        verify(mockCourseService, never()).deleteCourse("Microsserviços do 0 com Spring Cloud, Kotlin e Docker");
    }

    @Test
    @DisplayName("Delete courses not related to spring capturing arguments should call method V2")
    void testDeleteCoursesNotRelatedToSpring_CapturingArguments_Should_CallMethod_deleteCourseV2(){
//        Given/Arrange

//        courses = Arrays.asList(
//                "Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android",
//                "Microsserviços do 0 com Spring Cloud, Kotlin e Docker");

        given(mockCourseService.retrieveCourses("Leandro")).willReturn(courses);

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

//        String kotlinCourse = "Kotlin para DEV's Java: Aprenda a Linguagem Padrão do Android";

//        When/Act
        courseBusiness.deleteCoursesNotRelatedToSpring("Leandro");
//        Then/Assert
        then(mockCourseService).should(times(7)).deleteCourse(argumentCaptor.capture());
        assertThat(argumentCaptor.getAllValues().size(), is(7));
    }


}
