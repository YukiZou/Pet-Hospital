package com.ecnu.service.impl;

import com.ecnu.dao.DepFacilityDao;
import com.ecnu.entity.DepFacility;
import com.ecnu.service.DepFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepFacilityServiceImpl implements DepFacilityService {
    @Autowired
    private DepFacilityDao depFacilityDao;

    @Override
    public List<DepFacility> findDepFacilitiesByDepId(int depId) {
        DepFacility depFacility = new DepFacility();
        depFacility.setDepartment_id(depId);
        return depFacilityDao.queryDepFacilities(depFacility);
    }

    @Override
    public void deleteDepFacilities(DepFacility depFacility) {
        depFacilityDao.deleteDepFacility(depFacility);
    }
}
