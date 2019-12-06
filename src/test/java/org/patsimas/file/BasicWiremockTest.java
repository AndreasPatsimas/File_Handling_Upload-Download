package org.patsimas.file;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Before;
import org.junit.Rule;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.patsimas.file.controllers.FileController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.restdocs.JUnitRestDocumentation;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.InputStream;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@WebAppConfiguration
@ContextConfiguration(classes= {FileApplication.class})
@TestPropertySource(locations="classpath:application-test.properties")
public class BasicWiremockTest {
    @Rule
    public JUnitRestDocumentation jUnitRestDocumentation =
            new JUnitRestDocumentation("target/generated-snippets");

    @Autowired
    protected WebApplicationContext context;

    protected MockMvc mockMvc;

    @Value("${wiremock.server.host}")
    protected String WIREMOCK_SERVER_HOST;

    @Value("${wiremock.server.port}")
    protected int WIREMOCK_SERVER_PORT;

    @Value("${wiremock.server.scheme}")
    protected String WIREMOCK_SERVER_SCHEME;

    @Value("${server.servlet.context-path}")
    protected String CONTEXT_PATH;

    protected InputStream is;

    @Spy
    @InjectMocks
    private FileController controller = new FileController();

    @Before
    public void setUp() {
        this.mockMvc =
                MockMvcBuilders.webAppContextSetup(this.context)
                        .apply(documentationConfiguration(this.jUnitRestDocumentation)
                                .uris()
                                .withScheme(WIREMOCK_SERVER_SCHEME)
                                .withHost(WIREMOCK_SERVER_HOST)
                                .withPort(WIREMOCK_SERVER_PORT))
                        .alwaysDo(document("{class-name}/{method-name}",
                                preprocessRequest(prettyPrint()), preprocessResponse(prettyPrint())))
                        .build();

        is = controller.getClass().getClassLoader().getResourceAsStream("");
    }

    protected static String asJsonString(final Object obj) {
        try{
            return new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
