package com.ecnu.service;

import com.ecnu.entity.Facility;
import org.junit.Test;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class FacilityServiceTest extends BaseServiceTest {
    @Resource
    private FacilityService facilityService;

    @Test
    public void addFacility() throws Exception {
        String name = "testAdd" + System.currentTimeMillis();
        Facility facility = new Facility();
        facility.setName(name);
        assertTrue(facilityService.addFacility(facility));
        assertTrue(facilityService.deleteFacility(facility));
    }

    @Test
    public void deleteFacility() throws Exception {
        String name = "testDelete" + System.currentTimeMillis();
        Facility facility = new Facility();
        facility.setName(name);
        assertTrue(facilityService.addFacility(facility));
        assertTrue(facilityService.deleteFacility(facility));
        assertFalse(facilityService.deleteFacility(facility));
    }

    @Test
    public void queryFacilities() throws Exception {
        String name = "testQuery" + System.currentTimeMillis();
        Facility facility = new Facility();
        facility.setName(name);
        assertTrue(facilityService.addFacility(facility));
        Facility queryFacility = new Facility();
        queryFacility.setName("testQuery");
        assertTrue(facilityService.queryFacilities(queryFacility).size() > 0);
        assertTrue(facilityService.deleteFacility(facility));
        assertTrue(facilityService.queryFacilities(queryFacility).size() == 0);
    }

    @Test
    public void queryFacilitiesByIds() throws Exception {
        List<Integer> ids = new ArrayList<>();
        assertTrue(facilityService.queryFacilitiesByIds(ids).size() == 0);

        String name1 = "testQuery1" + System.currentTimeMillis();
        String name2 = "testQuery2" + System.currentTimeMillis();
        Facility facility1 = new Facility();
        facility1.setName(name1);
        assertTrue(facilityService.addFacility(facility1));
        Facility facility2 = new Facility();
        facility1.setName(name2);
        assertTrue(facilityService.addFacility(facility2));

        ids.add(facility1.getId());
        ids.add(facility2.getId());

        assertTrue(facilityService.queryFacilitiesByIds(ids).size() > 0);
        assertTrue(facilityService.deleteFacility(facility1));
        assertTrue(facilityService.deleteFacility(facility2));
        assertTrue(facilityService.queryFacilitiesByIds(ids).size() == 0);
    }

}