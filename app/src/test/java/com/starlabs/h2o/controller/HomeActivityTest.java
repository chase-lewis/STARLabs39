package com.starlabs.h2o.controller;

import android.app.Fragment;

import com.starlabs.h2o.R;
import com.starlabs.h2o.controller.purity_report.CreatePurityReportFragment;
import com.starlabs.h2o.controller.purity_report.ViewPurityReportsFragment;
import com.starlabs.h2o.controller.report.SetupHistogramFragment;
import com.starlabs.h2o.controller.report.ViewMapFragment;
import com.starlabs.h2o.controller.user.ViewUserProfileFragment;
import com.starlabs.h2o.controller.water_report.CreateWaterReportFragment;
import com.starlabs.h2o.controller.water_report.ViewWaterReportsFragment;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * Created by kavin on 4/5/2017.
 * <p>
 * Tests Home Activity
 */

public class HomeActivityTest {

    @Test
    public void fragmentCheckTest() {
        HomeActivity homeActivityTester = new HomeActivity();
        Fragment current = new Fragment();
        boolean checkValue;

        int id = R.id.nav_map;
        checkValue = (homeActivityTester.fragmentInitialize(id, current) instanceof ViewMapFragment);
        assertTrue(checkValue);

        id = R.id.nav_create_water_report;
        checkValue = (homeActivityTester.fragmentInitialize(id, current) instanceof CreateWaterReportFragment);
        assertTrue(checkValue);

        id = R.id.nav_view_water_reports;
        checkValue = (homeActivityTester.fragmentInitialize(id, current) instanceof ViewWaterReportsFragment);
        assertTrue(checkValue);

        id = R.id.nav_create_purity_report;
        checkValue = (homeActivityTester.fragmentInitialize(id, current) instanceof CreatePurityReportFragment);
        assertTrue(checkValue);

        id = R.id.nav_view_purity_reports;
        checkValue = (homeActivityTester.fragmentInitialize(id, current) instanceof ViewPurityReportsFragment);
        assertTrue(checkValue);

        id = R.id.nav_view_histogram;
        checkValue = (homeActivityTester.fragmentInitialize(id, current) instanceof SetupHistogramFragment);
        assertTrue(checkValue);

        id = R.id.nav_profile;
        checkValue = (homeActivityTester.fragmentInitialize(id, current) instanceof ViewUserProfileFragment);
        assertTrue(checkValue);

        id = -1;
        checkValue = (homeActivityTester.fragmentInitialize(id, current) == current);
        assertTrue(checkValue);

    }
}

