package br.com.renan.business;

import br.com.renan.service.CourseService;

import java.util.ArrayList;
import java.util.List;

//SUT - System(Method) Under Test
public class CourseBusiness {

//    CourseService is a Dependency
    private CourseService courseService;

    public CourseBusiness(CourseService courseService){
     this.courseService = courseService;
    }

    public List<String> retrieveCoursesRelatedToSpring(String student){

        var filteredCourses = new ArrayList<String>();

        if("FooBar".equals(student)) return filteredCourses;

        var allCourses = courseService.retrieveCourses(student);

        for (String course: allCourses) {

            if(course.contains("Spring")){
                filteredCourses.add(course);
            }

        }

        return filteredCourses;
    }

    public void deleteCoursesNotRelatedToSpring(String student){

        var allCourses = courseService.retrieveCourses(student);

        for (String course: allCourses) {
            if(!course.contains("Spring")){
                courseService.deleteCourse(course);
            }
        }
    }

}
