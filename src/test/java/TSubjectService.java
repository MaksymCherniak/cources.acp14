import org.junit.Before;
import org.junit.Test;
import week4.home.jdbc.dao.services.ISubjectService;
import week4.home.jdbc.dao.services.ServiceFactory;
import week4.home.jdbc.entity.Subject;

import java.util.logging.Logger;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class TSubjectService {
    private static Logger log = Logger.getLogger(TSubjectService.class.getName());
    private ISubjectService iSubjectService = ServiceFactory.getSubjectInstance();
    private Subject subject;

    @Before
    public void initialize() {
        subject = new Subject();
        subject.setId(999);
        subject.setName("Test name");
        subject.setDescription("Test description");
    }

    @Test
    public void whenAddTheSubjectExists_subjectNotExistsAfterRemoved() {
        assertNull("Subject exists before added", iSubjectService.getSubjectById(subject.getId()));

        iSubjectService.addSubject(subject);

        assertNotNull("Subject does not exist after added", iSubjectService.getSubjectById(subject.getId()));

        iSubjectService.removeSubject(subject.getId());

        assertNull("Subject exists after removed", iSubjectService.getSubjectById(subject.getId()));
    }

}
