import com.oreilly.batch.controller.JobLaunchingController
import org.junit.Rule
import org.springframework.batch.core.launch.JobOperator
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.restdocs.JUnitRestDocumentation
import org.springframework.restdocs.RestDocumentation
import org.springframework.test.context.web.WebAppConfiguration
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.hamcrest.Matchers.*
import static org.springframework.http.MediaType.APPLICATION_JSON
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration

/**
 * Created by oo185005 on 1/7/17.
 */
class JobLaunchingControllerSpecification extends Specification {

    @Rule
    JUnitRestDocumentation restDocumentation = new JUnitRestDocumentation('build/generated-snippets')

    def mockJobOperator = Mock(JobOperator)
    def underTest = new JobLaunchingController(jobOperator: mockJobOperator)


    def mockMvc = MockMvcBuilders.standaloneSetup(underTest)
            .apply(documentationConfiguration(restDocumentation))
            .build()

    def 'test that a job launch is successful' () {
        when:
        def response = mockMvc.perform(post('/batch/{jobName}','xmlJob').contentType(APPLICATION_JSON))

        then:
        1 * mockJobOperator.startNextInstance(_) >> 42

        response.andExpect(status().isAccepted()).andDo(document('{class-name}/{method-name}',pathParameters(parameterWithName('jobName').description('The name of the job to start'))))
    }
}
