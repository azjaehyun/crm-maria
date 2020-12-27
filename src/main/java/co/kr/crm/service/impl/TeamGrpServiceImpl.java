package co.kr.crm.service.impl;

import co.kr.crm.service.TeamGrpService;
import co.kr.crm.domain.TeamGrp;
import co.kr.crm.repository.TeamGrpRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TeamGrp}.
 */
@Service
@Transactional
public class TeamGrpServiceImpl implements TeamGrpService {

    private final Logger log = LoggerFactory.getLogger(TeamGrpServiceImpl.class);

    private final TeamGrpRepository teamGrpRepository;

    public TeamGrpServiceImpl(TeamGrpRepository teamGrpRepository) {
        this.teamGrpRepository = teamGrpRepository;
    }

    @Override
    public TeamGrp save(TeamGrp teamGrp) {
        log.debug("Request to save TeamGrp : {}", teamGrp);
        return teamGrpRepository.save(teamGrp);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TeamGrp> findAll(Pageable pageable) {
        log.debug("Request to get all TeamGrps");
        return teamGrpRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<TeamGrp> findOne(Long id) {
        log.debug("Request to get TeamGrp : {}", id);
        return teamGrpRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeamGrp : {}", id);
        teamGrpRepository.deleteById(id);
    }
}
