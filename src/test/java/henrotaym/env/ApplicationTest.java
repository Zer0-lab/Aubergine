package henrotaym.env;

import henrotaym.env.enums.ProfileName;
import jakarta.transaction.Transactional;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({ProfileName.TEST, ProfileName.HTTP, ProfileName.QUEUE})
@Transactional
@AutoConfigureMockMvc
public abstract class ApplicationTest {}
