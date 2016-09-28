package org.esco.portlet.dao.util;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

import java.util.Set;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

/**
 * Created by jgribonvald on 27/09/16.
 */
public class EscoHostnameVerifier implements HostnameVerifier, InitializingBean {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private HostnameVerifier defaultHostnameVerifier;

    private Set<String> trustedDomains;

    public EscoHostnameVerifier() {
        super();
    }

    public boolean verify(String hostname, SSLSession session) {
        log.debug("EscoHostnameVerifier : checking on hostname [" + hostname + "]");
        if (hostname != null && trustedDomains.contains(hostname)) {
            return true;
        }
        return defaultHostnameVerifier.verify(hostname,session);
    }

    public void setTrustedDomains(final Set<String> trustedDomains) {
        this.trustedDomains = trustedDomains;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notEmpty(trustedDomains, "The list of trusted domains isn't initialized !");
        if (defaultHostnameVerifier == null) {
            defaultHostnameVerifier = SSLConnectionSocketFactory.getDefaultHostnameVerifier();
        }
        log.debug("Trusted Domain configured are {}", this.trustedDomains);
    }

}
