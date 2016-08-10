/**
 * 
 */
package com.zhou.test.push;

import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LUCKY
 *
 */
public class PushIpWatcher implements CuratorWatcher {
    private static Logger       logger = LoggerFactory.getLogger(PushIpWatcher.class);

    private NodeZookeeperClient client;
    private Object              nodeObj;
    private String              param;
    private boolean             isroot;

    /** 
    * <p>Title: </p> 
    * <p>Description: </p> 
    * @param client
    * @param nodeObj
    * @param param
    * @param isroot 
    */
    public PushIpWatcher(NodeZookeeperClient client, Object nodeObj, String param, boolean isroot) {
        this.client = client;
        this.nodeObj = nodeObj;
        this.param = param;
        this.isroot = isroot;
    }

    @Override
    public void process(WatchedEvent event) throws Exception {
        logger.info(event.toString());
        if ((event.getState() == Watcher.Event.KeeperState.Disconnected)) {
            return;
        }
        if (this.client == null) {
            return;
        }

        if (event.getType() == EventType.NodeDataChanged) {
            try {
                String path = event.getPath();
                String value = this.client.getCuratorClient().readPath(path);
                if (this.isroot) {
                    String ip = IpUtil.getRealIp();
                    String ippath = path + "/" + ip;
                    this.client.getCuratorClient().writePath(ippath, value);
                } else {
                    ReflectionUtils.writeField(this.param, this.nodeObj, value);
                }
                logger.info(this.client.getCuratorClient().readPath(path));

            } finally {
                this.client.getCuratorClient().watcherPath(event.getPath(), this);
            }
        }
    }

}
