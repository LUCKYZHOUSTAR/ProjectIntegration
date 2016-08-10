/**
 * 
 */
package com.zhou.test.push;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.CountDownLatch;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author LUCKY
 *node监听的客户端的操作
 */
public class NodeZookeeperClient {

    private static Logger           logger    = LoggerFactory.getLogger(NodeZookeeperClient.class);
    private CuratorClient           curatorClient;
    private static final String     nameSpace = "PUSH";
    private String                  ip;
    private String                  zkAddress;
    private int                     timeOut;
    private String                  projectName;
    private ConnectionStateListener listener;
    private Set<NodeInfo>           confSet;
    private CountDownLatch          latch;

    public NodeZookeeperClient() {
        this.timeOut = 10000;
        this.confSet = new CopyOnWriteArraySet<>();
        this.latch = null;
    }

    public void init() throws Exception {
        this.ip = IpUtil.getRealIp();
        this.latch = new CountDownLatch(1);
        this.listener = new StateEventListener();
        this.curatorClient = new CuratorClient();
        this.curatorClient.init(nameSpace, zkAddress, timeOut, listener);
        this.latch.await();
        //没有节点信息的话，就开始创建节点信息
        try {
            //在PUSH节点下，下载projectName
            if (!this.curatorClient.isPathExist(this.projectName)) {
                this.curatorClient.createPath(this.projectName, CreateMode.PERSISTENT);
                this.curatorClient.writePath(this.projectName, "");

            }
        } catch (Exception e) {
        }
    }

    public boolean confRegiste(NodeInfo node, boolean addSet) {
        String path = this.projectName + "/" + node.getClassName() + "." + node.getParamName();
        //以后项目部署到多个服务器的话，需要加ip来进行区分的操作
        //PUSH/projectName/className.methodName/ip
        String ippath = path + "/" + this.ip;
        if ((addSet) && (this.confSet.contains(node))) {
            logger.error("重复注册节点", node);
            return false;
        }

        try {
            //算是根节点吧，项目的节点信息
            if (!this.curatorClient.isPathExist(path)) {
                this.curatorClient.createPath(path, CreateMode.PERSISTENT);
                this.curatorClient.writePath(path, node.getValue());
            } else {
                String value = this.curatorClient.readPath(path);
                if (!value.equals(node.getValue())) {
                    ReflectionUtils.writeField(node.getParamName(), node, value);
                    node.setValue(value);
                }
            }

            if (this.curatorClient.isPathExist(ippath)) {
                this.curatorClient.deletePath(ippath);
            }
            this.curatorClient.createPath(ippath, CreateMode.PERSISTENT);
            this.curatorClient.writePath(ippath, node.getValue());
            this.curatorClient.watcherPath(ippath,
                new PushIpWatcher(this, node.getObject(), node.getParamName(), false));
            //添加到集合中，下次进行重复添加判断的操作
            if (addSet) {
                this.confSet.add(node);
            }
        } catch (Exception e) {
            logger.error("注册drm异常", e);
            return false;
        }

        return true;
    }

    private void reinit() {
        try {
            //先取消注册，在重新初始化操作
            unregister();
            init();
            //注册节点信息
            for (NodeInfo node : this.confSet) {
                confRegiste(node, false);
            }
        } catch (Exception e) {
            logger.error("重新初始化异常", e);
        }
    }

    private void unregister() {
        try {
            this.curatorClient.removeListener(this.listener);
            this.listener = null;
            this.curatorClient.close();
            this.curatorClient = null;
        } catch (Exception e) {
            logger.warn("unregister failed");
            throw e;
        }
    }

    class StateEventListener implements ConnectionStateListener {

        @Override
        public void stateChanged(CuratorFramework client, ConnectionState newState) {

            if (newState == ConnectionState.CONNECTED) {
                NodeZookeeperClient.logger.info("connection establish");
                NodeZookeeperClient.this.latch.countDown();
            } else if (newState == ConnectionState.LOST) {
                NodeZookeeperClient.this.logger.info("connection lost ,waiting for reconnect");
                try {
                    NodeZookeeperClient.logger.info("re-initing");
                    NodeZookeeperClient.this.reinit();
                    NodeZookeeperClient.logger.info("re-inited");

                } catch (Exception e) {
                    NodeZookeeperClient.logger.error("re-inited");
                }
            }
        }

    }

    /**
     * @return the curatorClient
     */
    public CuratorClient getCuratorClient() {
        return curatorClient;
    }

    /**
     * @param curatorClient the curatorClient to set
     */
    public void setCuratorClient(CuratorClient curatorClient) {
        this.curatorClient = curatorClient;
    }

    /**
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * @param ip the ip to set
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * @return the zkAddress
     */
    public String getZkAddress() {
        return zkAddress;
    }

    /**
     * @param zkAddress the zkAddress to set
     */
    public void setZkAddress(String zkAddress) {
        this.zkAddress = zkAddress;
    }

    /**
     * @return the timeOut
     */
    public int getTimeOut() {
        return timeOut;
    }

    /**
     * @param timeOut the timeOut to set
     */
    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    /**
     * @return the projectName
     */
    public String getProjectName() {
        return projectName;
    }

    /**
     * @param projectName the projectName to set
     */
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    /**
     * @return the listener
     */
    public ConnectionStateListener getListener() {
        return listener;
    }

    /**
     * @param listener the listener to set
     */
    public void setListener(ConnectionStateListener listener) {
        this.listener = listener;
    }

    /**
     * @return the confSet
     */
    public Set<NodeInfo> getConfSet() {
        return confSet;
    }

    /**
     * @param confSet the confSet to set
     */
    public void setConfSet(Set<NodeInfo> confSet) {
        this.confSet = confSet;
    }

    /**
     * @return the latch
     */
    public CountDownLatch getLatch() {
        return latch;
    }

    /**
     * @param latch the latch to set
     */
    public void setLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    /**
     * @return the namespace
     */
    public static String getNamespace() {
        return nameSpace;
    }

}
