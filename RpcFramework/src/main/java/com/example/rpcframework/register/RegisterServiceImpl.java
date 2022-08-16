package com.example.rpcframework.register;

import com.example.rpcframework.dto.Invocation;
import com.example.rpcframework.loadbalance.LoadBalance;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author Shan Cheng
 * @since 2022/8/16
 */

@Component
public class RegisterServiceImpl implements RegisterService {

    @Autowired
    private LoadBalance loadBalance;

    @Override
    public InetSocketAddress findService(Invocation invocation) {
        String serviceName = invocation.getInterfaceName();

        CuratorFramework zkClient = CuratorUtils.getZkClient();
        List<String> serviceUrlList = CuratorUtils.getChildrenNodes(zkClient, serviceName);

        if (CollectionUtils.isEmpty(serviceUrlList)) {
            throw new RuntimeException("service not found, serviceName=" + serviceName);
        }

        // load balancing
        String targetServiceUrl = loadBalance.selectService(serviceUrlList, invocation);

        String[] socketAddressArray = targetServiceUrl.split(":");
        String host = socketAddressArray[0];
        int port = Integer.parseInt(socketAddressArray[1]);

        return new InetSocketAddress(host, port);
    }

    @Override
    public void register(String serviceName, InetSocketAddress inetSocketAddress) {

        String servicePath = CuratorUtils.ZK_REGISTER_ROOT_PATH + "/" + serviceName + inetSocketAddress.toString();
        CuratorFramework zkClient = CuratorUtils.getZkClient();
        CuratorUtils.createPersistentNode(zkClient, servicePath);
    }
}
