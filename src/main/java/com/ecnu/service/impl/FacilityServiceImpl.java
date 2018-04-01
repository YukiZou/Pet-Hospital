package com.ecnu.service.impl;

import com.ecnu.dao.FacilityDao;
import com.ecnu.entity.Facility;
import com.ecnu.service.FacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacilityServiceImpl implements FacilityService{
    @Autowired
    private FacilityDao facilityDao;

    @Override
    public Boolean addFacility(Facility facility) {
        int res = facilityDao.insertFacility(facility);
        if (res > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<Facility> queryFacilities(Facility facility) {
        return facilityDao.queryFacilities(facility);
    }
}
