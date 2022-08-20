package spring.service.impl;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import spring.dto.json.reponse.DashboardResponseDTO;
import spring.dto.row.DashboardRowData;
import spring.repository.UserRepository;
import spring.service.DashboardService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
@Log4j2
public class DashboardServiceImpl implements DashboardService {

    private final UserRepository userRepository;

    public DashboardServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public DashboardResponseDTO getDashboardDetails() {
        try {
            log.info("Execute method getDashboardDetails params ");

            DashboardRowData dashboardDetails = userRepository.getDashboardDetails();

            return new DashboardResponseDTO(dashboardDetails.getPending(),dashboardDetails.getProcessing(), dashboardDetails.getCompleted(),
                    dashboardDetails.getCustomers());

        } catch (Exception e) {
            log.error("Method getDashboardDetails : ", e);
            throw e;
        }
    }
}
