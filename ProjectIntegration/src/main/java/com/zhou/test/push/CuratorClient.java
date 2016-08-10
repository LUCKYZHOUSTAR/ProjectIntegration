/**
 * 
 */
package com.zhou.test.push;

import java.nio.charset.Charset;
import java.util.List;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.api.CuratorWatcher;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.data.Stat;


/**
 * @author LUCKY
 *
 */
public class CuratorClient {

    private String           zkAddress;
    private String           namespace;
    private CuratorFramework curator;

    public void init(String nameSpace, String address, int timeout,
                     ConnectionStateListener stateListener) throws Exception {

        this.namespace = nameSpace;
        this.zkAddress = address;
        this.curator = CuratorFrameworkFactory.builder().connectString(this.zkAddress)
            .namespace(this.namespace).retryPolicy(new RetryNTimes(5, 1000))
            .connectionTimeoutMs(timeout).build();
        this.curator.getConnectionStateListenable().addListener(stateListener);
        this.curator.start();
    }

    public void removeListener(ConnectionStateListener stateListener) {
        this.curator.getConnectionStateListenable().removeListener(stateListener);
    }

    public void close() {
        try {
            this.curator.close();
            this.curator = null;
        } catch (Exception e) {
            throw e;
        }
    }

    public Stat getStat(String path) throws Exception {
        return this.curator.checkExists().forPath(path);
    }

    public boolean isPathExist(String path) throws Exception {
        Stat serverStat = getStat(path);
        if (serverStat == null) {
            return false;
        }
        return true;
    }

    public void createPath(String path, CreateMode mode) throws Exception {
        createPath(path, mode, "");
    }

    public void createPath(String path, CreateMode mode, String value) throws Exception {
        this.curator.create().withMode(mode).withACL(ZooDefs.Ids.OPEN_ACL_UNSAFE)
            .forPath(path, value.getBytes(Charset.forName("utf-8")));
    }

    public void deletePath(String path) throws Exception {
        this.curator.delete().forPath(path);
    }

    public void writePath(String path, String value) throws Exception {
        this.curator.setData().forPath(path, value.getBytes(Charset.forName("utf-8")));
    }

    public String readPath(String path) throws Exception {
        byte[] buffer = this.curator.getData().forPath(path);
        return new String(buffer);
    }

    public String watcherPath(String path, CuratorWatcher watcher) throws Exception {
        byte[] buffer = this.curator.getData().usingWatcher(watcher).forPath(path);
        return new String(buffer);
    }

    public List<String> getChildren(String path) throws Exception {
        return this.curator.getChildren().forPath(path);
    }
}
