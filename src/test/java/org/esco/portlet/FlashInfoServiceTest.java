package org.esco.portlet;

import java.util.List;

import org.esco.portlet.model.FlashInfo;
import org.esco.portlet.service.IFlashInfoService;
import org.esco.portlet.service.impl.FlashInfoServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.portlet.MockPortletPreferences;
import org.springframework.mock.web.portlet.MockRenderRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

/**
 * Created by jgribonvald on 14/09/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext.xml")
public class FlashInfoServiceTest {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IFlashInfoService flashInfoService;

    @Test
    public void test1() throws Exception {
        MockRenderRequest request = new MockRenderRequest();
        request.setServerName("edu.internal.fr");
        request.setServerPort(8443);
        request.setContextPath("/portal");
        request.setScheme("http");
        request.addParameter("param1", "value");

        MockPortletPreferences prefs = new MockPortletPreferences();
        prefs.setValue(FlashInfoServiceImpl.getPrefFlashUrl(), "/test/{ESCOUAICourant}");
        request.setPreferences(prefs);

        List<FlashInfo> fil = flashInfoService.retrieveFlashInfos(request);
        Assert.notNull(fil);
        Assert.notEmpty(fil);
        Assert.isTrue(fil.size() == 5);
        //Assert.assertEquals(url,"http://edu.internal.fr:8443/portal/test/" + MockUserResourceImpl.getESCOUAICourant().get(0));
    }
}
