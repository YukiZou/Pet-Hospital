package com.ecnu.service.impl;

import com.ecnu.dao.DepFacilityDao;
import com.ecnu.entity.DepFacility;
import com.ecnu.service.DepFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author asus
 */
@Service
public class DepFacilityServiceImpl implements DepFacilityService {
    @Autowired
    private DepFacilityDao depFacilityDao;

    @Override
    public List<DepFacility> findDepFacilitiesByDepId(int depId) {
        DepFacility depFacility = new DepFacility();
        depFacility.setDepartmentId(depId);
        return depFacilityDao.queryDepFacilities(depFacility);
    }

    @Override
    public List<DepFacility> findDepFacility(DepFacility depFacility) {
        return depFacilityDao.queryDepFacilities(depFacility);
    }

    @Override
    public int addDepFacilities(List<DepFacility> depFacilities) {
        if (depFacilities == null || depFacilities.size() == 0) {
            return 0;
        }
        return depFacilityDao.insertDepFacilities(depFacilities);
    }

    @Override
    public int deleteDepFacilities(List<DepFacility> depFacilities) {
        if (depFacilities == null || depFacilities.size() == 0) {
            return 0;
        }
        return depFacilityDao.deleteDepFacilities(depFacilities);
    }

    @Override
    public int deleteDepFacilities(DepFacility depFacility) {
        return depFacilityDao.deleteDepFacility(depFacility);
    }
}
