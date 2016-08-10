/**
 * 
 */
package com.zhou.test.push;

/**
 * @author LUCKY
 *节点的测试类相关的信息
 */
public class NodeTest {

    private String httpServiceReload = "reload";

    public static void main(String[] args) throws Exception {
        NodeTest test = new NodeTest();
        NodeZookeeperClient client = new NodeZookeeperClient();
        client.setProjectName("test");
        client.setZkAddress("10.200.0.161:2181");
        client.setTimeOut(10000);
        client.setCuratorClient(new CuratorClient());
        client.init();
        NodeInfo httpNode = new NodeInfo(test, "httpServiceReload", "httpServiceReload");
        client.confRegiste(httpNode, true);
        Thread.sleep(Integer.MAX_VALUE);
    }

    public String getHttpServiceReload() {
        return httpServiceReload;
    }

    public void setHttpServiceReload(String httpServiceReload) {
        System.out.println("重新推送了一遍哦");
        this.httpServiceReload = httpServiceReload;
    }

}
