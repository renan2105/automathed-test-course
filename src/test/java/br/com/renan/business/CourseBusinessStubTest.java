package br.com.renan.business;

import br.com.renan.service.CourseService;
import br.com.renan.stubs.CourseServiceStub;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CourseBusinessStubTest {

    @Test
    void testCoursesRelatedToSpring_When_UsingAStub(){
//        Given/Arrange
        CourseService stubCourseService = new CourseServiceStub();
        CourseBusiness courseBusiness = new CourseBusiness(stubCourseService);

//        When/Act
        var filteredCourses = courseBusiness.retrieveCoursesRelatedToSpring("Leandro");
//        Then/Assert
        assertEquals(4, filteredCourses.size());
    }

    @Test
    void testCoursesRelatedToSpring_When_UsingAFooBarStudent(){
//        Given/Arrange
        CourseService stubCourseService = new CourseServiceStub();
        CourseBusiness courseBusiness = new CourseBusiness(stubCourseService);

//        When/Act
        var filteredCourses = courseBusiness.retrieveCoursesRelatedToSpring("FooBar");
//        Then/Assert
        assertEquals(0, filteredCourses.size());
    }

}
