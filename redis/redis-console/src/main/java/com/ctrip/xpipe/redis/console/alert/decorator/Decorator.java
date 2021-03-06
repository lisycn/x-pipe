package com.ctrip.xpipe.redis.console.alert.decorator;

import com.ctrip.xpipe.redis.console.alert.AlertEntity;
import com.ctrip.xpipe.redis.console.config.ConsoleConfig;
import com.ctrip.xpipe.utils.DateTimeUtils;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.StringWriter;

/**
 * @author chen.zhu
 * <p>
 * Oct 19, 2017
 */

public abstract class Decorator {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected VelocityEngine velocityEngine;

    @Autowired
    protected ConsoleConfig consoleConfig;


    public String generateContent(AlertEntity alert) {
        VelocityContext context = generateCommonContext();
        context = fillInContext(alert, context);
        return getRenderedString(getTemplateName(), context);
    }


    protected VelocityContext generateCommonContext() {
        VelocityContext context = new VelocityContext();
        context.put("time", DateTimeUtils.currentTimeAsString());
        context.put("environment", consoleConfig.getXpipeRuntimeEnvironmentEnvironment());
        context.put("xpipeAdminEmails", consoleConfig.getXPipeAdminEmails());
        return context;
    }

    public String getRenderedString(String templateName, VelocityContext context) {
        StringWriter stringWriter = new StringWriter();
        String encoding = "UTF-8";
        try {
            velocityEngine.mergeTemplate(templateName, encoding, context, stringWriter);
            return stringWriter.toString();
        } catch (Exception e) {
            logger.error("[getRenderedString] Error with velocity:\n{}", e);
        } finally {
            try {
                stringWriter.close();
            } catch (IOException e) {
                logger.error("[getRenderedString] Closing string writer error:\n {}", e);
            }
        }
        return null;
    }

    protected abstract String getTemplateName();

    public abstract String generateTitle(AlertEntity alert);

    protected abstract VelocityContext fillInContext(AlertEntity alert, VelocityContext context);
}
