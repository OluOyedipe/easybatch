import com.oreilly.batch.controller.ExceptionThrowingController
import org.junit.Rule
import org.springframework.batch.admin.web.RestControllerAdvice
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

/**
 * Created by oo185005 on 1/8/17.
 */
class ExceptionThrowingControllerSpecification extends Specification {
    @Rule
    JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation('build/generated-snippets')

    def underTest = new ExceptionThrowingController()


    def mockMvc = MockMvcBuilders.standaloneSetup(underTest)
            .setControllerAdvice(new RestControllerAdvice())
            .apply(documentationConfiguration(restDocumentation))
            .build()

    def 'fake resource returns 404' () {
        when:
        def response = mockMvc.perform(post('/batch/fake/{jobName}','xmlJob').contentType(APPLICATION_JSON))

        then:
//        thrown(JobNotFoundException)
        response.andExpect(status().isNotFound()).andDo(document('{class-name}/{method-name}',pathParameters(parameterWithName('jobName').description('The name of the job to start'))))
    }
}
