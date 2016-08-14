/**
 * 
 */
package com.zhou.test.push;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LUCKY
 *日志的格式类似于这样：
 *loggerName:=level;loggerName:=level
 *com.zhou.test.push:=WARN;root:=debug;
 */
public class logLevelPush {

    private static final Logger logger         = LoggerFactory.getLogger(logLevelPush.class);

    private String              logLevelConfig = "INFO";

    private Set<String>         acceptLevels   = new HashSet<>(Arrays.asList(new String[] {
            "TRACE", "DEBUG", "INFO", "WARN", "ERROR", "FATAL" }));

    private NodeZookeeperClient nodeZookeeperClient;

    public void init() {
        NodeInfo logLevelNode = new NodeInfo(this, "logLevelConfig", this.logLevelConfig);
        this.nodeZookeeperClient.confRegiste(logLevelNode, true);
    }

    /**
     * @return the logLevelConfig
     */
    public String getLogLevelConfig() {
        return logLevelConfig;
    }

    /**
     * @param logLevelConfig the logLevelConfig to set
     */
    public void setLogLevelConfig(String logLevelConfig) {
        if (logger.isDebugEnabled()) {
            logger.info("接收到推送的日志配置:" + logLevelConfig);
        }

        String[] pairs = StringUtils.split(logLevelConfig, ",;");
        if (pairs != null) {
            Arrays.sort(pairs);
            for (String nameAndLevel : pairs) {
                String[] nameLevelPair = StringUtils.split(nameAndLevel, ":=");
                if (nameLevelPair != null && nameAndLevel.length() >= 2) {
                    String loggerName = StringUtils.trim(nameLevelPair[0]);
                    String levelName = StringUtils.trim(nameLevelPair[1]);
                    setLogLevel(loggerName, StringUtils.upperCase(levelName));
                } else {
                    logger.info("unrecongnized format{" + nameAndLevel + "}");
                }
            }
        }

        this.logLevelConfig = logLevelConfig;
    }

    private void setLogLevel(String loggerName, String level) {
        org.apache.log4j.Logger log = null;
        //得到根节点log信息
        if ("root".equalsIgnoreCase(loggerName)) {
            log = LogManager.getRootLogger();
        } else if (StringUtils.isNotBlank(loggerName)) {
            log = org.apache.log4j.Logger.getLogger(loggerName);
        }

        //设置相应的appender信息
        if ((log != null) && (StringUtils.isNotBlank(level))) {
            if (this.acceptLevels.contains(level.toUpperCase())) {
                if (logger.isInfoEnabled()) {
                    logger.info("set logger{" + log.getName() + "}" + "to [" + level + "]");
                }
                log.setLevel(Level.toLevel(level.toUpperCase()));
            } else if (logger.isInfoEnabled()) {
                logger.info("Unaccept level {" + level + "}");
            }
        } else if (logger.isInfoEnabled()) {
            logger.info("unaccept level [" + level + "]");
        }
    }

    /**
     * @return the nodeZookeeperClient
     */
    public NodeZookeeperClient getNodeZookeeperClient() {
        return nodeZookeeperClient;
    }

    /**
     * @param nodeZookeeperClient the nodeZookeeperClient to set
     */
    public void setNodeZookeeperClient(NodeZookeeperClient nodeZookeeperClient) {
        this.nodeZookeeperClient = nodeZookeeperClient;
    }

    public static void main(String[] args) throws Exception {
        NodeZookeeperClient client = new NodeZookeeperClient();
        client.setProjectName("test");
        client.setZkAddress("10.200.0.161:2181");
        client.setTimeOut(10000);
        client.setCuratorClient(new CuratorClient());
        client.init();
        logLevelPush logLevelPush = new logLevelPush();
        logLevelPush.setNodeZookeeperClient(client);
        logLevelPush.init();
        Thread.sleep(Integer.MAX_VALUE);
    }
}
