package com.ecnu.service;

import com.ecnu.entity.DepFacility;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class DepFacilityServiceTest extends BaseServiceTest {
    @Resource
    private DepFacilityService depFacilityService;
    @Test
    public void findDepFacilitiesByDepId() throws Exception {
        //1.要找的关系不存在
        int depId = 7;
        assertTrue(depFacilityService.findDepFacilitiesByDepId(depId).size() == 0);

        //2.要找的关系存在
        depId = 2;
        assertTrue(depFacilityService.findDepFacilitiesByDepId(depId).size() > 0);
    }

    @Test
    public void findDepFacility() throws Exception {
        List<DepFacility> depFacilityList = new ArrayList<>();
        DepFacility depFacility = new DepFacility();
        depFacility.setDepartmentId(2);
        depFacility.setFacilityId(2);
        depFacilityList.add(depFacility);
        assertTrue(depFacilityService.addDepFacilities(depFacilityList) > 0);
        assertTrue(depFacilityService.findDepFacility(depFacility).size() > 0);
        assertTrue(depFacilityService.deleteDepFacilities(depFacility) > 0);
        assertTrue(depFacilityService.findDepFacility(depFacility).size() == 0);
    }

    @Test
    public void deleteDepFacilities() throws Exception {
        List<DepFacility> depFacilityList = new ArrayList<>();
        assertTrue(depFacilityService.addDepFacilities(depFacilityList) == 0);
        assertTrue(depFacilityService.deleteDepFacilities(depFacilityList) == 0);

        depFacilityList.add(new DepFacility(2,2));
        depFacilityList.add(new DepFacility(3, 1));

        assertTrue(depFacilityService.addDepFacilities(depFacilityList) > 0);
        assertTrue(depFacilityService.deleteDepFacilities(depFacilityList) > 0);
        assertTrue(depFacilityService.deleteDepFacilities(depFacilityList) == 0);
    }

}